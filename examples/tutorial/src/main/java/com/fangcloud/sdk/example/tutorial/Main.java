package com.fangcloud.sdk.example.tutorial;

import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.file.YfyFile;
import com.fangcloud.sdk.api.file.YfyFileRequest;

public class Main {
    private final static String accessToken = "your access token";

    public static void main(String args[]) throws Exception {
        YfyClient client = new YfyClient(new YfyRequestConfig(), accessToken);
        testFileApi(client);
    }

    private static void testFileApi(YfyClient client) throws Exception {
        YfyFileRequest fileRequest = client.files();
        String fileName = "test.txt";
        YfyFile file = fileRequest.directUploadFile(0L, fileName, Main.class.getResourceAsStream("/" + fileName));
        System.out.println("File id:" + file.getId() + ".File name:" +file.getName());
    }
}
