package com.fangcloud.sdk;

/**
 * An interface that lets you save, retrieve, and clear a single value in the user's web
 * session.
 *
 * <pre>
 * If your web app uses the standard Java Servlet API, just use
 * {@link YfyStandardSessionStore}.
 * </pre>
 */
public interface YfySessionStore {
    String get();
    void set(String value);
    void clear();
}
