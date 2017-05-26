package com.fangcloud.sdk;

import com.fangcloud.sdk.util.StringUtil;

import java.io.IOException;
import java.io.OutputStream;

public class ProgressOutputStream extends OutputStream {
    private final OutputStream outputStream;
    private final YfyProgressListener progressListener;

    private long total;
    private long totalWritten;
    private long lastWritten;
    private long speed;
    private long timestamp;

    public ProgressOutputStream(OutputStream outputStream, YfyProgressListener progressListener, long total) {
        this.outputStream = outputStream;
        this.progressListener = progressListener;
        this.total = total;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        totalWritten++;
        calculateProgress();
    }

    @Override
    public void write(byte[] b) throws IOException {
        outputStream.write(b);
        totalWritten += b.length;
        calculateProgress();
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        outputStream.write(b, off, len);
        if (len < b.length) {
            totalWritten += len;
        } else {
            totalWritten += b.length;
        }
        calculateProgress();
    }

    private void calculateProgress() {
        long now = System.currentTimeMillis();
        if (now - timestamp > 1000L) {
            speed = (totalWritten - lastWritten) * 1000L / (now - timestamp);
            lastWritten = totalWritten;
            timestamp = now;
            progressListener.onProgressChanged(totalWritten, total, StringUtil.formatBytes(speed) + "/s");
        }
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }
}
