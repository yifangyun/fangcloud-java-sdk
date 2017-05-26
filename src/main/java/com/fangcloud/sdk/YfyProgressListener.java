package com.fangcloud.sdk;

/**
 * The listener interface for monitoring the progress of a long-running API(usually upload and download) call.
 */
public interface YfyProgressListener {
    /**
     * Invoked when the progress of the API call(usually upload and download) changes.
     *
     * @param numBytes Number of bytes completed
     * @param totalBytes Total number of bytes
     * @param speed Transport speed during a interval
     */
    void onProgressChanged(long numBytes, long totalBytes, String speed);
}
