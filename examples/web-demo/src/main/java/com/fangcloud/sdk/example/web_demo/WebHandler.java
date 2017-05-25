package com.fangcloud.sdk.example.web_demo;

import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyClientFactory;
import com.fangcloud.sdk.YfyRefreshListener;
import com.fangcloud.sdk.api.user.YfyUser;
import com.fangcloud.sdk.exception.YfyException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import static com.fangcloud.sdk.example.web_demo.Common.htmlEncode;

public class WebHandler extends AbstractHandler {
    private final Common common;
    private final FangcloudAuth fangcloudAuth;
    private final YfyClientFactory<String> clientFactory;

    public WebHandler(File userDbFile) throws IOException {
        this.common = new Common(userDbFile);
        this.fangcloudAuth = new FangcloudAuth(common);
        this.clientFactory = new YfyClientFactory<String>(10, common.getProxyRequestConfig(),
                new MyRefreshListener(common));
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        // Don't pollute the logging with the favicon.ico requests that browsers issue.
        if (target.equals("/favicon.ico")) {
            response.sendError(404);
            return;
        }

        // Log the request path.
        String requestPath = target;
        if (request.getQueryString() != null) {
            requestPath += "?" + request.getQueryString();
        }
        System.out.println("-- Request: " + request.getMethod() + " " + requestPath);

        if (target.equals("/")) {
            doIndex(request, response);
        } else if (target.equals("/login")) {
            doLogin(request, response);
        } else if (target.equals("/home")) {
            doHome(request, response);
        } else if (target.equals("/logout")) {
            doLogout(request, response);
            // Fangcloud authorize routes
        } else if (target.equals("/fangcloud-auth-start")) {
            fangcloudAuth.doStart(request, response);
        } else if (target.equals("/fangcloud-auth-finish")) {
            fangcloudAuth.doFinish(request, response);
            // Fangcloud file browsing routes.
        } else {
            response.sendError(404);
        }
    }

    // -------------------------------------------------------------------------------------------
    // GET /
    // -------------------------------------------------------------------------------------------
    // The front page with a login form.  If there's already a user logged in, they get
    // redirected to "/home".

    public void doIndex(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!common.checkGet(request, response)) return;

        // If there's a user logged in, send them to "/home".
        User user = common.getLoggedInUser(request);
        if (user != null) {
            response.sendRedirect("/home");
            return;
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));

        out.println("<html>");
        out.println("<head><title>Home - Web Demo</title></head>");
        out.println("<body>");

        // Login form.
        out.println("<h2>Log in</h2>");
        out.println("<form action='/login' method='POST'>");
        out.println("<p>Username: <input name='username' type='text' /> (pick whatever you want)</p>");
        out.println("<p>No password needed for this tiny example.</p>");
        out.println("<input type='submit' value='Login' />");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    // -------------------------------------------------------------------------------------------
    // POST /login
    // -------------------------------------------------------------------------------------------
    // Login form handler.

    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!common.checkPost(request, response)) return;

        String username = request.getParameter("username");
        if (username == null) {
            response.sendError(400, "Missing field \"username\".");
            return;
        }

        // Lookup user.  If the user doesn't exist, create it.
        User user;
        synchronized (common.getUserDb()) {
            Map<String, User> userDb = common.getUserDb();
            user = userDb.get(username);
            if (user == null) {
                user = new User();
                user.setUsername(username);

                userDb.put(user.getUsername(), user);
                common.saveUserDb();
            }
        }

        request.getSession().setAttribute("logged-in-username", user.getUsername());
        response.sendRedirect("/");
    }

    // -------------------------------------------------------------------------------------------
    // GET /home
    // -------------------------------------------------------------------------------------------
    // If a user is logged in, show them information about their account and let them connect their
    // account to their Fangcloud account.  If nobody is logged in, redirect to "/".

    public void doHome(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!common.checkGet(request, response)) return;

        // If nobody's logged in, send the browser to "/".
        User user = common.getLoggedInUser(request);
        if (user == null) {
            response.sendRedirect("/");
            return;
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));

        out.println("<html>");
        out.println("<head><title>Home - Web demo</title></head>");
        out.println("<body>");

        // Show user info.
        out.println("<h2>User: " + htmlEncode(user.getUsername()) + "</h2>");

        if (user.getAccessToken() != null) {
            // Show information about linked Fangcloud account.
            YfyClient client = clientFactory.getClient(user.getUsername(), user.getAccessToken(), user.getRefreshToken());
            try {
                YfyUser yfyUser = client.users().getSelf();
                out.println("<p>Linked to your Fangcloud account (" + htmlEncode(user.getAccessToken()) + ")</p>");
                out.println("<p>your Fangcloud account name: " + htmlEncode(yfyUser.getName()) + "</p>");
                out.println("<p>your Fangcloud account id: " + yfyUser.getId() + "</p>");
                out.println("<p>your Fangcloud account email: " + htmlEncode(yfyUser.getEmail()) + "</p>");

                // upload test
                // YfyFile result = client.files().directUploadFile(0L, "chrome.crx", "/Users/Wien/Downloads/chrome.crx");
                // out.println("<p>");
                // out.println("upload file success!file name: " + result.getName());
                // out.println("</p>");

                // out.println("<p>");
                // out.println("<form method=\"post\"action=\"" + client.files().preSignatureUpload(0L, "chrome.crx").getUploadUrl()
                //         + "\" enctype=\"multipart/form-data\">");
                // out.println("<input type=\"file\" name=\"test\">");
                // out.println("<input type=\"submit\" name=\"submit\" value=\"提交\">");
                // out.println("</form>");
                // out.println("</p>");

                // download test
                // GetChildrenResult result = client.folders().getChildren(0L, 0, 1, "file");
                // for (YfyFile file : result.getFiles()) {
                //     client.files().directDownloadFile(file.getId(), file.getName());
                //     out.println("<p><a href=\""
                //             + client.files().preSignatureDownload(file.getId()).getDownloadUrls().get(file.getId())
                //             + "\">" + fileInfo.getName() + "</a></p>");
                // }


            } catch (YfyException ex) {
                ex.printStackTrace();
                common.handleYfyException(response, user, ex, "users getSelf");
            }
        } else {
            // They haven't linked their Fangcloud account.  Display the "Link" form.
            out.println("<p><form action='/fangcloud-auth-start' method='POST'>");
            out.println("<input type='submit' value='Link to your Fangcloud account' />");
            out.println("</form></p>");
        }

        // Log out form.
        out.println("<p><form action='/logout' method='POST'>");
        out.println("<input type='submit' value='Logout' />");
        out.println("</form></p>");

        out.println("</body>");
        out.println("</html>");
        out.flush();
    }

    // -------------------------------------------------------------------------------------------
    // POST /logout
    // -------------------------------------------------------------------------------------------
    // Logout form handler.

    public void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!common.checkPost(request, response)) return;
        request.getSession().removeAttribute("logged-in-username");
        response.sendRedirect("/");
    }

    private class MyRefreshListener implements YfyRefreshListener<String> {
        private final Common common;

        // import your own user storage into refresh listener
        public MyRefreshListener(Common common) {
            this.common = common;
        }

        @Override
        public void onTokenRefreshed(String key, String accessToken, String refreshToken, long expireIn) {
            System.out.println("user " + key + " has refresh token successfully");
            User user = common.getUserDb().get(key);
            if (user != null) {
                user.setAccessToken(accessToken);
                user.setRefreshToken(refreshToken);
                try {
                    common.saveUserDb();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
