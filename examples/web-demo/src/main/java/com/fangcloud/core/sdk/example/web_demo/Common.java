package com.fangcloud.core.sdk.example.web_demo;

import com.fangcloud.core.sdk.YfyRequestConfig;
import com.fangcloud.core.sdk.exception.InvalidTokenException;
import com.fangcloud.core.sdk.exception.NeedAuthorizationException;
import com.fangcloud.core.sdk.exception.YfyException;
import com.fangcloud.core.sdk.util.LangUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fangcloud.core.sdk.util.StringUtil.jq;

public class Common {
    private static final TypeReference<List<User>> USER_LIST_TYPE = new TypeReference<List<User>>() {};

    private final File userDbFile;
    private final Map<String, User> userDb;

    public Common(File userDbFile) throws IOException {
        this.userDbFile = userDbFile;
        this.userDb = loadUserDb(userDbFile);
    }

    public Map<String, User> getUserDb() {
        return userDb;
    }
// -------------------------------------------------------------------------------------------
    // User Database
    // -------------------------------------------------------------------------------------------

    private static final ObjectMapper jsonMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    private Map<String,User> loadUserDb(File dbFile) throws IOException {
        HashMap<String,User> userMap = new HashMap<String,User>();

        if (dbFile.isFile()) {
            System.out.println("Loading User DB from " + jq(dbFile.getPath()) + ".");
            List<User> userList = jsonMapper.readValue(dbFile, USER_LIST_TYPE);
            for (User user : userList) {
                userMap.put(user.getUsername(), user);
            }
            System.out.println("Loaded " + userMap.size() + " records.");
        }

        return userMap;
    }

    public void saveUserDb() throws IOException {
        synchronized (this.userDb) {
            jsonMapper.writeValue(this.userDbFile, this.userDb.values());
        }
    }

    // -------------------------------------------------------------------------------------------
    // Utility Functions
    // -------------------------------------------------------------------------------------------

    public boolean checkGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (!request.getMethod().equals("GET")) {
            response.sendError(405);  // 405 - Incorrect method.
            return false;
        }

        return true;
    }

    public boolean checkPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            page(response, 405, "Incorrect method", "Expecting POST, got " + request.getMethod());
            response.sendError(405);  // 405 - Incorrect method.
            return false;
        }
        return true;
    }

    public User getLoggedInUser(HttpServletRequest request) throws IOException {
        String username = (String) request.getSession().getAttribute("logged-in-username");
        if (username == null) {
            return null;
        }
        return userDb.get(username);
    }

    public User requireLoggedInUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User user = getLoggedInUser(request);
        if (user == null) {
            pageSoftError(response, "This page requires a logged-in user.  Nobody is logged in.");
            return null;
        }
        return user;
    }

    public void handleYfyException(HttpServletResponse response, User user, YfyException ex, String action)
            throws IOException {
        // NeedAuthorizationException happens with auto refresh opened and the refresh token is expired
        if (ex instanceof NeedAuthorizationException) {
            page(response, 400, "Cannot access Fangcloud account", "You need to do authentication again to the account");
            user.setAccessToken(null);
            user.setRefreshToken(null);
            saveUserDb();
            return;
        }
        // InvalidTokenException happens with auto refresh closed and the access token is expired
        if (ex instanceof InvalidTokenException) {
            page(response, 400, "access token is invalid", "You need to refresh access token or open auto refresh with client");
            saveUserDb();
            return;
        }

        handleException(response, ex, action);
    }

    public void handleException(HttpServletResponse response, Exception ex, String action) throws IOException {
        // The generic "try again later" is a decent response.
        // - If it's a transient error, then a retry will work.
        // - If it's Fangcloud's fault, their service will eventually get fixed and the retry will work (sooner or later).
        // - If it's our fault, we might fix our service and the retry will work eventually.
        page(response, 503, "Error communicating with Fangcloud", "Try again later?");

        // But obviously we should still log all these errors because they probably indicate a real problem.
        // - If it's Fangcloud's fault, we should record error information so we can notify Fangcloud
        //   via their API support e-mail address or the API support forums.
        // - If it's our fault, we obviously want to fix it.
        System.out.println("Error making Fangcloud API call: " + action + ": " + ex.getMessage());
    }

    public void pageSoftError(HttpServletResponse response, String message) throws IOException {
        page(response, 200, "Error", message);
    }

    public void page(HttpServletResponse response, int statusCode, String title, String message) throws IOException {
        title = htmlEncode(title);
        message = htmlEncode(message);
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
        out.println("<html>");
        out.println("<head><title>" + title + "</title></head>");
        out.println("<body>");
        out.println("<h2>" + title + "</h2>");
        out.println("<p>" + message + "</p>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        response.setStatus(statusCode);
    }

    public String getUrl(HttpServletRequest request, String path) {
        URL requestUrl;
        try {
            requestUrl = new URL(request.getRequestURL().toString());
            return new URL(requestUrl, path).toExternalForm();
        } catch (MalformedURLException ex) {
            throw LangUtil.mkAssert("Bad URL", ex);
        }
    }

    public static String htmlEncode(String source) {
        if (source == null) {
            return "";
        }
        String html = "";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                case '"':
                    buffer.append("&quot;");
                    break;
                case 10:
                case 13:
                    break;
                default:
                    buffer.append(c);
            }
        }
        html = buffer.toString();
        return html;
    }

    public YfyRequestConfig getProxyRequestConfig() {
        // Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
        // HttpRequestor proxyHttpRequestor = new StandardHttpRequestor(
        //         StandardHttpRequestor.Config.builder().withProxy(proxy).build());
        return new YfyRequestConfig();
    }
}
