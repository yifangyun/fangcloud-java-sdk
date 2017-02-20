package com.fangcloud.core.sdk.api.file;

import com.fangcloud.core.sdk.YfyClient;
import com.fangcloud.core.sdk.exception.YfyException;

public class YfyFileRequest {
    private static String POST_METHOD = "POST";
    private static String PUT_METHOD = "PUT";
    private static String DELETE_METHOD = "DELETE";

    private static String GET_FILE_PATH = "api/file/%s/info";
    private static String COPY_FILE_PATH = "api/file/copy";

    private final YfyClient client;

    public YfyFileRequest(YfyClient client) {
        this.client = client;
    }

    /**
     * Get {@link YfyFile} model by file id
     *
     * @param fileId File id in fangcloud
     * @return {@link YfyFile} model with file info
     * @throws YfyException
     */
    public YfyFile getFile(long fileId) throws YfyException {
        String[] params = { String.valueOf(fileId) };
        return getFile(params);
    }

    private YfyFile getFile(String[] params) throws YfyException {
        return client.doGet(client.getHost().getApi(),
                GET_FILE_PATH,
                params,
                null,
                YfyFile.class);
    }

    /**
     * Copy the specific file to a folder
     *
     * @param fileId File id in fangcloud
     * @param targetFolderId Target folder id that you want to copy to
     * @return {@link YfyFile} model with the generated file info
     * @throws YfyException
     */
    public YfyFile copyFile(long fileId, long targetFolderId) throws YfyException {
        return copyFile(new CopyFileArg(fileId, targetFolderId));
    }

    private YfyFile copyFile(CopyFileArg copyFileArg) throws YfyException {
        return client.doPost(client.getHost().getApi(),
                COPY_FILE_PATH,
                null,
                copyFileArg,
                POST_METHOD,
                YfyFile.class);
    }
}
