package com.fangcloud.core.sdk.api.file;

import com.fangcloud.core.sdk.YfyClient;
import com.fangcloud.core.sdk.YfySdkConstant;
import com.fangcloud.core.sdk.exception.ClientValidationException;
import com.fangcloud.core.sdk.exception.NetworkIOException;
import com.fangcloud.core.sdk.exception.YfyException;
import com.fangcloud.core.sdk.util.IOUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class YfyFileRequest {
    private static String GET_FILE_PATH = "api/file/%s/info";
    private static String COPY_FILE_PATH = "api/file/copy";
    private static String PRE_SIGNATURE_DOWNLOAD_PATH = "api/file/%s/download";
    private static String PRE_SIGNATURE_UPLOAD_PATH = "api/file/upload";

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
                YfySdkConstant.POST_METHOD,
                YfyFile.class);
    }

    /**
     * Get file download url, then use the url to download file. The url is valid in an hour
     *
     * @param fileId File id in fangcloud
     * @return An object contains a map with file id as key and download url as value
     * @throws YfyException
     */
    public PreSignatureDownloadResult preSignatureDownload(long fileId) throws YfyException {
        String[] params = { String.valueOf(fileId) };
        return preSignatureDownload(params);
    }

    private PreSignatureDownloadResult preSignatureDownload(String[] params) throws YfyException {
        return client.doGet(client.getHost().getApi(),
                PRE_SIGNATURE_DOWNLOAD_PATH,
                params,
                null,
                PreSignatureDownloadResult.class);
    }

    /**
     * When get a file download url, can use this method to save the file to the certain path
     *
     * @param downloadUrl Download url returned by the {@link YfyFileRequest#preSignatureDownload(long)}
     * @param savePath Where you'd like to save the file
     * @throws YfyException
     */
    public void downloadFile(String downloadUrl, String savePath) throws YfyException {
        InputStream body = client.doDownload(downloadUrl);
        File file = new File(savePath);
        try {
            FileOutputStream out = new FileOutputStream(file);
            IOUtil.copyStreamToStream(body, out);
        } catch (FileNotFoundException e) {
            throw new ClientValidationException("The file " + savePath + " not found");
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        } finally {
            IOUtil.closeQuietly(body);
        }
    }

    /**
     * Combine the {@link YfyFileRequest#preSignatureDownload(long)}
     * and {@link YfyFileRequest#downloadFile(String,String)} method, direct download the file
     *
     * @param fileId File id in fangcloud
     * @param savePath Where you'd like to save the file
     * @throws YfyException
     */
    public void directDownloadFile(long fileId, String savePath) throws YfyException {
        downloadFile(preSignatureDownload(fileId).getDownloadUrls().get(fileId), savePath);
    }

    /**
     * Get the file upload url, then use the url to upload file.
     * The url is valid in an hour and can only be used for one time
     *
     * @param parentId Parent folder id you want to store the file in, the root folder is 0
     * @param name File name
     * @return An object contains a attribute named upload url
     * @throws YfyException
     */
    public PreSignatureUploadResult preSignatureUpload(long parentId, String name) throws YfyException {
        return preSignatureUpload(new PreSignatureUploadArg(parentId, name, "api"));
    }

    private PreSignatureUploadResult preSignatureUpload(PreSignatureUploadArg preSignatureUploadArg)
            throws YfyException {
        return client.doPost(client.getHost().getApi(),
                PRE_SIGNATURE_UPLOAD_PATH,
                null,
                preSignatureUploadArg,
                YfySdkConstant.POST_METHOD,
                PreSignatureUploadResult.class);
    }

    /**
     * When get a file upload url, can use this method to upload the file to the server
     *
     * @param uploadUrl Upload url returned by the {@link YfyFileRequest#preSignatureUpload(long,String)}
     * @param filePath The file path which you'd like upload to server
     * @throws YfyException
     */
    public UploadFileResult uploadFile(String uploadUrl, String filePath) throws YfyException {
        return client.doUpload(uploadUrl, filePath);
    }

    /**
     * Combine the {@link YfyFileRequest#preSignatureUpload(long, String)}
     * and {@link YfyFileRequest#uploadFile(String,String)} method, direct upload the file
     *
     * @param parentId Parent folder id you want to store the file in, the root folder is 0
     * @param name File name
     * @param filePath The file path which you'd like to upload to server
     * @throws YfyException
     */
    public UploadFileResult directUploadFile(long parentId, String name, String filePath) throws YfyException {
        return uploadFile(preSignatureUpload(parentId, name).getUploadUrl(), filePath);
    }


}
