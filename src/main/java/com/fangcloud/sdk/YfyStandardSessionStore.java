package com.fangcloud.sdk;

import javax.servlet.http.HttpSession;

/**
 * A {@link YfySessionStore} implementation that stores the value using the standard
 * {@link HttpSession} interface from the Java Servlet API.
 *
 * Example:
 * <pre>
 * YfyWebAuth getYfyWebAuth(HttpServletRequest request)
 * {
 *     HttpSession session = request.getSession(true);
 *     String key = "fangcloud-auth-csrf-token";
 *     YfySessionStore csrfStore = new YfyStandardSessionStore(session, key);
 *     return new YfyWebAuth(..., csrfStore);
 * }
 * </pre>
 */
public class YfyStandardSessionStore implements YfySessionStore {
    private final HttpSession session;
    private final String key;

    public YfyStandardSessionStore(HttpSession session, String key) {
        this.session = session;
        this.key = key;
    }

    public HttpSession getSession() {
        return session;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String get() {
        Object v = session.getAttribute(key);
        if (v instanceof String) return (String) v;
        return null;
    }

    @Override
    public void set(String value) {
        session.setAttribute(key, value);
    }

    @Override
    public void clear() {
        session.removeAttribute(key);
    }
}
