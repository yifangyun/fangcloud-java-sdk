package com.fangcloud.sdk.example.web_demo;

import com.fangcloud.sdk.YfySessionStore;
import com.fangcloud.sdk.YfyStandardSessionStore;
import com.fangcloud.sdk.auth.YfyAuthFinish;
import com.fangcloud.sdk.auth.YfyWebAuth;
import com.fangcloud.sdk.exception.YfyException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FangcloudAuth {
    private Common common;

    public FangcloudAuth(Common common) {
        this.common = common;
    }

    // -------------------------------------------------------------------------------------------
    // POST /fangcloud-auth-start
    // -------------------------------------------------------------------------------------------
    // Start the process of getting a Fangcloud API access token for the user's Fangcloud account.

    public void doStart(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (!common.checkPost(request, response)) return;
        User user = common.requireLoggedInUser(request, response);
        if (user == null) return;

        // Start the authorization process with Fangcloud.
        YfyWebAuth.Request authRequest = YfyWebAuth.newRequestBuilder()
                // After we redirect the user to the Fangcloud website for authorization,
                // Fangcloud will redirect them back here.
                .withRedirectUri(getRedirectUri(request), getSessionStore(request))
                .withState("test")
                .build();
        String authorizeUrl = getWebAuth().authorize(authRequest);

        // Redirect the user to the Fangcloud website so they can approve our application.
        // The Fangcloud website will send them back to /fangcloud-auth-finish when they're done.
        response.sendRedirect(authorizeUrl);
    }

    // -------------------------------------------------------------------------------------------
    // GET /fangcloud-auth-finish
    // -------------------------------------------------------------------------------------------
    // The Fangcloud API authorization page will redirect the user's browser to this page.
    //
    // This is a GET (even though it modifies state) because we get here via a browser
    // redirect (Fangcloud redirects the user here).  You can't do a browser redirect to
    // an HTTP POST.

    public void doFinish(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (!common.checkGet(request, response)) return;

        User user = common.requireLoggedInUser(request, response);
        if (user == null) {
            common.pageSoftError(response, "Can't do /fangcloud-auth-finish.  Nobody is logged in.");
            return;
        }

        YfyAuthFinish authFinish;
        try {
            authFinish = getWebAuth().finishFromRedirect(
                    getRedirectUri(request),
                    getSessionStore(request),
                    request.getParameterMap()
            );
        } catch (YfyWebAuth.BadRequestException e) {
            System.out.println("On /fangcloud-auth-finish: Bad request: " + e.getMessage());
            response.sendError(400);
            return;
        } catch (YfyWebAuth.BadStateException e) {
            // Send them back to the start of the auth flow.
            response.sendRedirect(common.getUrl(request, "/fangcloud-auth-start"));
            return;
        } catch (YfyWebAuth.CsrfException e) {
            System.out.println("On /fangcloud-auth-finish: CSRF mismatch: " + e.getMessage());
            response.sendError(403);
            return;
        } catch (YfyException e) {
            System.out.println("On /fangcloud-auth-finish: Error getting token: " + e);
            response.sendError(503, "Error communicating with Fangcloud.");
            return;
        }

        // We have an Fangcloud API access token now.  This is what will let us make Fangcloud API
        // calls.  Save it in the database entry for the current user.
        user.setAccessToken(authFinish.getAccessToken());
        user.setRefreshToken(authFinish.getRefreshToken());
        common.saveUserDb();

        response.sendRedirect("/");
    }

    private YfySessionStore getSessionStore(final HttpServletRequest request) {
        // Select a spot in the session for YfyWebAuth to store the CSRF token.
        HttpSession session = request.getSession(true);
        String sessionKey = "fangcloud-auth-csrf-token";
        return new YfyStandardSessionStore(session, sessionKey);
    }

    private YfyWebAuth getWebAuth() {
        return new YfyWebAuth(common.getProxyRequestConfig());
    }

    private String getRedirectUri(final HttpServletRequest request) {
        return common.getUrl(request, "/fangcloud-auth-finish");
    }
}
