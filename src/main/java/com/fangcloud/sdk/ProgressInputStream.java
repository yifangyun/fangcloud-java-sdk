package com.fangcloud.sdk;

import com.fangcloud.sdk.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

public class ProgressInputStream extends InputStream {
    private final InputStream inputStream;
    private final YfyProgressListener progressListener;

    private long total;
    private long totalRead;
    private long lastRead;
    private long speed;
    private long timestamp;

    public ProgressInputStream(InputStream inputStream, YfyProgressListener progressListener, long total) {
        this.inputStream = inputStream;
        this.progressListener = progressListener;
        this.total = total;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public int read() throws IOException {
        int read = inputStream.read();
        totalRead++;
        calculateProgress();
        return read;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int read = inputStream.read(b, off, len);
        totalRead += read;
        calculateProgress();
        return read;
    }

    @Override
    public int read(byte[] b) throws IOException {
        int read = inputStream.read(b);
        totalRead += read;
        calculateProgress();
        return read;
    }

    private void calculateProgress() {
        long now = System.currentTimeMillis();
        if (now - timestamp > 1000L) {
            speed = (totalRead - lastRead) * 1000L / (now - timestamp);
            lastRead = totalRead;
            timestamp = now;
            progressListener.onProgressChanged(totalRead, total, StringUtil.formatBytes(speed) + "/s");
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }
}
