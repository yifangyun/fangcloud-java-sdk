package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyBaseClient;
import com.fangcloud.sdk.YfyProgressListener;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.SuccessResult;
import com.fangcloud.sdk.api.YfyFileVersion;
import com.fangcloud.sdk.api.comment.ListCommentResult;
import com.fangcloud.sdk.api.share_link.ListShareLinkResult;
import com.fangcloud.sdk.exception.NetworkIOException;
import com.fangcloud.sdk.exception.YfyException;
import com.fangcloud.sdk.util.IOUtil;
import com.fangcloud.sdk.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class YfyFileRequest {
    private final static String FILE_API_PATH = YfySdkConstant.API_VERSION + "file/";
    private final static String GET_FILE_PATH = FILE_API_PATH + "%s/info";
    private final static String COPY_FILE_PATH = FILE_API_PATH + "%s/copy";
    private final static String PRE_SIGNATURE_DOWNLOAD_PATH = FILE_API_PATH + "%s/download";
    private final static String PRE_SIGNATURE_UPLOAD_PATH = FILE_API_PATH + "upload";
    private final static String NEW_VERSION_PRE_SIGNATURE_UPLOAD_PATH = FILE_API_PATH + "%s/new_version";
    private final static String UPDATE_FILE_PATH = FILE_API_PATH + "%s/update";
    private final static String DELETE_FILE_PATH = FILE_API_PATH + "%s/delete";
    private final static String DELETE_FILE_BATCH_PATH = FILE_API_PATH + "delete_batch";
    private final static String DELETE_FILE_FROM_TRASH_PATH = FILE_API_PATH + "%s/delete_from_trash";
    private final static String DELETE_FILE_BATCH_FROM_TRASH_PATH = FILE_API_PATH + "delete_batch_from_trash";
    private final static String RESTORE_FILE_FROM_TRASH_PATH = FILE_API_PATH + "%s/restore_from_trash";
    private final static String RESTORE_FILE_BATCH_FROM_TRASH_PATH = FILE_API_PATH + "restore_batch_from_trash";
    private final static String MOVE_FILE_PATH = FILE_API_PATH + "%s/move";
    private final static String MOVE_FILE_BATCH_PATH = FILE_API_PATH + "move_batch";
    private final static String PREVIEW_PATH = FILE_API_PATH + "%s/preview";
    private final static String DOWNLOAD_PREVIEW_PATH = FILE_API_PATH + "%s/preview_download";
    private final static String LIST_FILE_SHARE_LINK_PATH = FILE_API_PATH + "%s/share_links";
    private final static String LIST_COMMENT_PATH = FILE_API_PATH + "%s/comments";
    private final static String LIST_VERSION_PATH = FILE_API_PATH + "%s/versions";
    private final static String GET_VERSION_PATH = FILE_API_PATH + "%s/version/%s/info";
    private final static String GET_PREVIEW_TOKEN_PATH = FILE_API_PATH + "/preview_token";

    private final YfyBaseClient<?>.YfyInternalClient client;

    public YfyFileRequest(YfyBaseClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Retrieve file info
     *
     * @param fileId File id in fangcloud
     * @return Detailed file's information
     * @throws YfyException
     */
    public YfyFile getFile(long fileId) throws YfyException {
        String[] params = { String.valueOf(fileId) };
        return getFile(params);
    }

    private YfyFile getFile(String[] params) throws YfyException {
        return client.doGet(GET_FILE_PATH,
                params,
                null,
                YfyFile.class);
    }

    /**
     * Update fields in file, including file name and file description
     *
     * @param fileId File id in fangcloud
     * @param newName File new name, can not be null
     * @param newDescription File new description, can be null
     * @return Detailed file's information
     * @throws YfyException
     */
    public YfyFile updateFile(long fileId, String newName, String newDescription) throws YfyException {
        String[] params = { String.valueOf(fileId) };
        return updateFile(params, new UpdateFileArg(newName, newDescription));
    }

    private YfyFile updateFile(String[] params, UpdateFileArg updateFileArg) throws YfyException {
        return client.doPost(UPDATE_FILE_PATH,
                params,
                updateFileArg,
                YfyFile.class);
    }

    /**
     * Discard a file to the trash
     *
     * @param fileId File id in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteFile(long fileId) throws YfyException {
        String[] params = { String.valueOf(fileId) };
        return deleteFile(DELETE_FILE_PATH, params, null);
    }

    // /**
    //  * Discard files to the trash
    //  *
    //  * @param fileIds File ids list in fangcloud
    //  * @return An object only has one attribute named success
    //  * @throws YfyException
    //  */
    // public SuccessResult deleteFile(List<Long> fileIds) throws YfyException {
    //     return deleteFile(DELETE_FILE_BATCH_PATH, null, new DeleteFileArg(fileIds));
    // }

    private SuccessResult deleteFile(String path, String[] params, DeleteFileArg deleteFileArg) throws YfyException {
        return client.doPost(path,
                params,
                deleteFileArg,
                SuccessResult.class);
    }

    /**
     * Permanently delete a specific file that is in the trash. The file will no longer exist in fangcloud.
     * This action cannot be undone.
     *
     * @param fileId File id in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteFileFromTrash(long fileId) throws YfyException {
        String[] params = { String.valueOf(fileId) };
        return deleteFileFromTrash(DELETE_FILE_FROM_TRASH_PATH, params, null);
    }

    /**
     * Permanently delete specific files that are in the trash. The files will no longer exist in fangcloud.
     * This action cannot be undone.
     *
     * @param fileIds File ids list in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    // public SuccessResult deleteFileFromTrash(List<Long> fileIds) throws YfyException {
    //     if (fileIds == null || fileIds.isEmpty()) {
    //         throw new ClientValidationException("file ids can not be null or be empty");
    //     }
    //     return deleteFileFromTrash(DELETE_FILE_BATCH_FROM_TRASH_PATH, null, new DeleteFileFromTrashArg(fileIds, false));
    // }

    /**
     * Permanently delete all files that are in the trash (not including folders).
     * The files will no longer exist in fangcloud. This action cannot be undone.
     *
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    // public SuccessResult deleteAllFileInTrash() throws YfyException {
    //     return deleteFileFromTrash(DELETE_FILE_BATCH_FROM_TRASH_PATH, null, new DeleteFileFromTrashArg(null, true));
    // }

    private SuccessResult deleteFileFromTrash(String path, String[] params, DeleteFileFromTrashArg deleteFileFromTrashArg) throws YfyException {
        return client.doPost(path,
                params,
                deleteFileFromTrashArg,
                SuccessResult.class);
    }

    /**
     * Restore a file that has been moved to the trash. Default behavior is to restore the file to the folder
     * it was in before it was moved to the trash
     *
     * @param fileId File id in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult restoreFileFromTrash(long fileId) throws YfyException {
        String[] params = { String.valueOf(fileId) };
        return restoreFileFromTrash(RESTORE_FILE_FROM_TRASH_PATH, params, null);
    }

    /**
     * Restore files that have been moved to the trash. Default behavior is to restore the file to the folder
     * it was in before it was moved to the trash
     *
     * @param fileIds File ids list in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    // public SuccessResult restoreFileFromTrash(List<Long> fileIds) throws YfyException {
    //     if (fileIds == null || fileIds.isEmpty()) {
    //         throw new ClientValidationException("file ids can not be null or be empty");
    //     }
    //     return restoreFileFromTrash(RESTORE_FILE_BATCH_FROM_TRASH_PATH, null, new RestoreFileFromTrashArg(fileIds, false));
    // }

    /**
     * Restore all files that have been moved to the trash. Default behavior is to restore the file to the folder
     * it was in before it was moved to the trash
     *
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    // public SuccessResult restoreAllFileInTrash() throws YfyException {
    //     return restoreFileFromTrash(RESTORE_FILE_BATCH_FROM_TRASH_PATH, null, new RestoreFileFromTrashArg(null, true));
    // }

    private SuccessResult restoreFileFromTrash(String path, String[] params,
                                               RestoreFileFromTrashArg restoreFileFromTrashArg) throws YfyException {
        return client.doPost(path,
                params,
                restoreFileFromTrashArg,
                SuccessResult.class);
    }

    /**
     * Move a file to another folder
     *
     * @param fileId File id in fangcloud
     * @param targetFolderId Folder id of the destination folder in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult moveFile(long fileId, long targetFolderId) throws YfyException {
        String[] params = { String.valueOf(fileId) };
        return moveFile(MOVE_FILE_PATH, params, new MoveFileArg(null, targetFolderId));
    }

    // /**
    //  * Move files to another folder
    //  *
    //  * @param fileIds File ids list in fangcloud
    //  * @param targetFolderId Folder id of the destination folder in fangcloud
    //  * @return An object only has one attribute named success
    //  * @throws YfyException
    //  */
    // public SuccessResult moveFile(List<Long> fileIds, long targetFolderId) throws YfyException {
    //     return moveFile(MOVE_FILE_BATCH_PATH, null, new MoveFileArg(fileIds, targetFolderId));
    // }

    private SuccessResult moveFile(String path, String[] params, MoveFileArg moveFileArg) throws YfyException {
        return client.doPost(path,
                params,
                moveFileArg,
                SuccessResult.class);
    }

    /**
     * Get file download url, then use the url to download file. The url is valid in an hour
     *
     * @param fileId File id in fangcloud
     * @return Download url of the file
     * @throws YfyException
     */
    public String preSignatureDownload(long fileId) throws YfyException {
        String[] params = { String.valueOf(fileId) };
        return preSignatureDownload(params).getDownloadUrl();
    }

    private PreSignatureDownloadResult preSignatureDownload(String[] params) throws YfyException {
        return client.doGet(PRE_SIGNATURE_DOWNLOAD_PATH,
                params,
                null,
                PreSignatureDownloadResult.class);
    }

    /**
     * When get a file download url, use this method to save the file to the certain path
     *
     * @param downloadUrl Download url returned by the {@link YfyFileRequest#preSignatureDownload(long)}
     * @param savePath Where you'd like to save the file
     * @throws YfyException
     */
    public void downloadFile(String downloadUrl, String savePath) throws YfyException {
        InputStream body = client.doDownload(downloadUrl, false, null);
        File file = new File(savePath);
        try {
            IOUtil.copyStreamToFile(body, file);
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        } finally {
            IOUtil.closeQuietly(body);
        }
    }

    /**
     * When get a file download url, use this method to save the file to the certain path
     *
     * @param downloadUrl Download url returned by the {@link YfyFileRequest#preSignatureDownload(long)}
     * @param savePath Where you'd like to save the file
     * @param progressListener Where get file upload progress
     * @throws YfyException
     */
    public void downloadFile(String downloadUrl, String savePath, YfyProgressListener progressListener) throws YfyException {
        InputStream body = client.doDownload(downloadUrl, false, progressListener);
        File file = new File(savePath);
        try {
            IOUtil.copyStreamToFile(body, file);
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        } finally {
            IOUtil.closeQuietly(body);
        }
    }

    /**
     * When get a file download url, use this method to get a InputStream of the file
     *
     * @param downloadUrl Download url returned by the {@link YfyFileRequest#preSignatureDownload(long)}
     * @return InputStream of the file to be downloaded
     * @throws YfyException
     */
    public InputStream downloadFileStream(String downloadUrl) throws YfyException {
        return client.doDownload(downloadUrl, false, null);
    }

    /**
     * When get a file download url, use this method to get a InputStream of the file
     *
     * @param downloadUrl Download url returned by the {@link YfyFileRequest#preSignatureDownload(long)}
     * @param progressListener Where get file upload progress
     * @return InputStream of the file to be downloaded
     * @throws YfyException
     */
    public InputStream downloadFileStream(String downloadUrl, YfyProgressListener progressListener) throws YfyException {
        return client.doDownload(downloadUrl, false, progressListener);
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
        downloadFile(preSignatureDownload(fileId), savePath);
    }

    /**
     * Combine the {@link YfyFileRequest#preSignatureDownload(long)}
     * and {@link YfyFileRequest#downloadFile(String,String)} method, direct get a InputStream of the file
     *
     * @param fileId File id in fangcloud
     * @return InputStream of the file to be downloaded
     * @throws YfyException
     */
    public InputStream directDownloadFileStream(long fileId) throws YfyException {
        return downloadFileStream(preSignatureDownload(fileId));
    }

    /**
     * Get the file upload url, then use the url to upload file.
     * The url is valid in an hour and can only be used for one time
     *
     * @param parentId Parent folder id you want to store the file in, the root folder is 0
     * @param name File name
     * @return Upload url of the file
     * @throws YfyException
     */
    public String preSignatureUpload(long parentId, String name) throws YfyException {
        StringUtil.checkStringNotEmpty(name);
        return preSignatureUpload(new PreSignatureUploadArg(parentId, name, "api")).getUploadUrl();
    }

    private PreSignatureUploadResult preSignatureUpload(PreSignatureUploadArg preSignatureUploadArg)
            throws YfyException {
        return client.doPost(PRE_SIGNATURE_UPLOAD_PATH,
                null,
                preSignatureUploadArg,
                PreSignatureUploadResult.class);
    }

    /**
     * Get the new version file upload url, then use the url to upload new version file.
     * The url is valid in an hour and can only be used for one time
     *
     * @param fileId Origin file id in fangcloud
     * @param name File name of the new version
     * @param remark remark of the new version
     * @return An object contains a attribute named upload url
     * @throws YfyException
     */
    public String newVersionPreSignatureUpload(long fileId, String name, String remark)
            throws YfyException {
        StringUtil.checkStringNotEmpty(name);
        String[] param = { String.valueOf(fileId) };
        return newVersionPreSignatureUpload(param, new NewVersionPreSignatureUploadArg(name, "api", remark)).getUploadUrl();
    }

    private PreSignatureUploadResult newVersionPreSignatureUpload(
            String[] param, NewVersionPreSignatureUploadArg newVersionPreSignatureUploadArg)
            throws YfyException {
        return client.doPost(NEW_VERSION_PRE_SIGNATURE_UPLOAD_PATH,
                param,
                newVersionPreSignatureUploadArg,
                PreSignatureUploadResult.class);
    }

    /**
     * When get a file upload url, use this method to upload the file to the server
     * (note that real file name used name in pre signature arg)
     *
     * @param uploadUrl Upload url returned by the {@link this#preSignatureUpload(long,String)}
     * @param filePath Path of the file which you'd like to upload to server
     * @return Detailed new file information
     * @throws YfyException
     */
    public YfyFile uploadFile(String uploadUrl, String filePath) throws YfyException, FileNotFoundException {
        StringUtil.checkStringNotEmpty(uploadUrl);
        StringUtil.checkStringNotEmpty(filePath);
        return client.doUpload(uploadUrl, new FileInputStream(filePath), 0L, null);
    }

    /**
     * When get a file upload url, use this method to upload the file to the server
     * (note that real file name used name in pre signature arg)
     *
     * @param uploadUrl Upload url returned by the {@link this#preSignatureUpload(long,String)}
     * @param filePath Path of the file which you'd like to upload to server
     * @param fileSize The size of file
     * @param progressListener Where get file upload progress
     * @return Detailed new file information
     * @throws YfyException
     */
    public YfyFile uploadFile(String uploadUrl, String filePath, long fileSize, YfyProgressListener progressListener)
            throws YfyException, FileNotFoundException {
        StringUtil.checkStringNotEmpty(uploadUrl);
        StringUtil.checkStringNotEmpty(filePath);
        return client.doUpload(uploadUrl, new FileInputStream(filePath), fileSize, progressListener);
    }

    /**
     * When get a file upload url, use this method to upload the file to the server
     * (note that real file name used name in pre signature arg)
     *
     * @param uploadUrl Upload url returned by the {@link this#preSignatureUpload(long,String)}
     * @param fileStream The file stream which you'd like to upload to server
     * @return Detailed new file information
     * @throws YfyException
     */
    public YfyFile uploadFile(String uploadUrl, InputStream fileStream) throws YfyException {
        StringUtil.checkStringNotEmpty(uploadUrl);
        StringUtil.checkObjectNotNull(fileStream);
        return client.doUpload(uploadUrl, fileStream, 0L, null);
    }

    /**
     * When get a file upload url, use this method to upload the file to the server
     * (note that real file name used name in pre signature arg)
     *
     * @param uploadUrl Upload url returned by the {@link this#preSignatureUpload(long,String)}
     * @param fileStream The file stream which you'd like to upload to server
     * @param fileSize The size of file
     * @param progressListener Where get file upload progress
     * @return Detailed new file information
     * @throws YfyException
     */
    public YfyFile uploadFile(String uploadUrl, InputStream fileStream, long fileSize
            , YfyProgressListener progressListener) throws YfyException {
        StringUtil.checkStringNotEmpty(uploadUrl);
        StringUtil.checkObjectNotNull(fileStream);
        return client.doUpload(uploadUrl, fileStream, fileSize, progressListener);
    }

    /**
     * Combine the {@link this#preSignatureUpload(long, String)}
     * and {@link this#uploadFile(String,InputStream)} method, direct upload the file
     *
     * @param parentId Parent folder id you want to store the file in, the root folder is 0
     * @param name File name
     * @param filePath Path of the file which you'd like to upload to server
     * @return Detailed new file information
     * @throws YfyException
     */
    public YfyFile directUploadFile(long parentId, String name, String filePath) throws YfyException, FileNotFoundException {
        return uploadFile(preSignatureUpload(parentId, name), filePath);
    }

    /**
     * Combine the {@link this#preSignatureUpload(long, String)}
     * and {@link this#uploadFile(String,InputStream)} method, direct upload the file
     *
     * @param parentId Parent folder id you want to store the file in, the root folder is 0
     * @param name File name
     * @param fileStream The file stream which you'd like to upload to server
     * @return Detailed new file information
     * @throws YfyException
     */
    public YfyFile directUploadFile(long parentId, String name, InputStream fileStream) throws YfyException {
        return uploadFile(preSignatureUpload(parentId, name), fileStream);
    }

    /**
     * Combine the {@link this#newVersionPreSignatureUpload(long,String,String)}
     * and {@link this#uploadFile(String,InputStream)} method, direct upload the new version file
     *
     * @param fileId File id that you want to upload the new version to
     * @param name New version file name
     * @param remark Remark of the new version
     * @param filePath Path of the file which you'd like to upload to server
     * @return Detailed new file information
     * @throws YfyException
     */
    public YfyFile directUploadNewVersionFile(long fileId, String name, String remark, String filePath)
            throws YfyException, FileNotFoundException {
        return uploadFile(newVersionPreSignatureUpload(fileId, name, remark), filePath);
    }

    /**
     * Combine the {@link this#newVersionPreSignatureUpload(long,String,String)}
     * and {@link this#uploadFile(String,InputStream)} method, direct upload the new version file
     *
     * @param fileId File id that you want to upload the new version to
     * @param name New version file name
     * @param remark Remark of the new version
     * @param fileStream The file stream which you'd like to upload to server
     * @return Detailed new file information
     * @throws YfyException
     */
    public YfyFile directUploadNewVersionFile(long fileId, String name, String remark, InputStream fileStream) throws YfyException {
        return uploadFile(newVersionPreSignatureUpload(fileId, name, remark), fileStream);
    }

    /**
     * Copy the specific file to a folder
     *
     * @param fileId File id in fangcloud
     * @param targetFolderId Id of the destination folder in fangcloud
     * @return Detailed new file's information
     * @throws YfyException
     */
    public YfyFile copyFile(long fileId, long targetFolderId) throws YfyException {
        String[] param = { String.valueOf(fileId) };
        return copyFile(param, new CopyFileArg(targetFolderId));
    }

    private YfyFile copyFile(String[] param, CopyFileArg copyFileArg) throws YfyException {
        return client.doPost(COPY_FILE_PATH,
                param,
                copyFileArg,
                YfyFile.class);
    }

    /**
     * List share links of a file
     *
     * @param fileId File id in fangcloud
     * @param pageId Page id begin with 0
     * @return All related share links' information
     * @throws YfyException
     */
    public ListShareLinkResult listShareLink(long fileId, final int pageId) throws YfyException {
        String[] param = { String.valueOf(fileId) };
        Map<String, String> mapParams = new HashMap<String, String>(){{
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
        }};
        return listShareLink(param, mapParams);
    }

    /**
     * List file's share links created by specific owner
     *
     * @param fileId File id in fangcloud
     * @param pageId Page id begin with 0
     * @param ownerId Owner id of share link you want to see
     * @return All related share links' information
     * @throws YfyException
     */
    public ListShareLinkResult listShareLink(long fileId, final int pageId, final long ownerId) throws YfyException {
        String[] param = { String.valueOf(fileId) };
        Map<String, String> mapParams = new HashMap<String, String>(){{
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
            put(YfySdkConstant.OWNER_ID, String.valueOf(ownerId));
        }};
        return listShareLink(param, mapParams);
    }

    private ListShareLinkResult listShareLink(String[] param, Map<String, String> mapParams) throws YfyException {
        return client.doGet(LIST_FILE_SHARE_LINK_PATH,
                param,
                mapParams,
                ListShareLinkResult.class);
    }

    /**
     * List file's all comments' information
     *
     * @param fileId File id in fangcloud
     * @return All file's comments' information
     * @throws YfyException
     */
    public ListCommentResult listComment(long fileId) throws YfyException {
        String[] param = { String.valueOf(fileId) };
        return listComment(param);
    }

    private ListCommentResult listComment(String[] param) throws YfyException {
        return client.doGet(LIST_COMMENT_PATH,
                param,
                null,
                ListCommentResult.class);
    }

    /**
     * List file's all versions' information
     * @param fileId File id in fangcloud
     * @return All file's versions' information
     * @throws YfyException
     */
    public ListFileVersionsResult listVersions(long fileId) throws YfyException {
        String[] param = { String.valueOf(fileId) };
        return client.doGet(LIST_VERSION_PATH, param, null, ListFileVersionsResult.class);
    }

    /**
     * List file's version's information
     * @param fileId File id in fangcloud
     * @param fileVersionId FileVersion id in fangcloud
     * @return file's version's information
     * @throws YfyException
     */
    public YfyFileVersion getFileVersion(long fileId, long fileVersionId) throws YfyException {
        String[] param = { String.valueOf(fileId), String.valueOf(fileVersionId) };
        return client.doGet(GET_VERSION_PATH, param, null, YfyFileVersion.class);
    }


    /**
     * get file's preview url
     *
     * @param fileId File id in fangcloud
     * @param period expire in seconds, should be between 300 to 7200
     * @return file's preview url
     * @throws YfyException
     */
    public String getPreviewUrl(long fileId, int period) throws YfyException {
        PreviewTokenResult result = client.doPost(GET_PREVIEW_TOKEN_PATH, null, new GetPreviewTokenArg(fileId, period), PreviewTokenResult.class);
        String protocol ;
        if (YfyAppInfo.getHost().getApi().contains("-svc")) {
            protocol = YfySdkConstant.SCHEME_HTTP;
        } else {
            protocol = YfySdkConstant.SCHEME_HTTPS;
        }
        if(YfyAppInfo.getProtocol() != null){
            protocol =  YfyAppInfo.getProtocol();
        }
        return protocol + "://" + YfyAppInfo.getHost().getApi() + "/preview/preview.html?preview_token=" + result.getPreviewToken();
    }

    /**
     * get fileVersion's preview url
     *
     * @param fileId File id in fangcloud
     * @param fileVersionId FileVersion id in fangcloud
     * @param period expire in seconds, should be between 300 to 7200
     * @return fileVersion's preview url
     * @throws YfyException
     */
    public String getPreviewUrl(long fileId, long fileVersionId, int period) throws YfyException {
        PreviewTokenResult result = client.doPost(GET_PREVIEW_TOKEN_PATH, null, new GetPreviewTokenArg(fileId, fileVersionId, period), PreviewTokenResult
                .class);
        String protocol ;
        if (YfyAppInfo.getHost().getApi().contains("-svc")) {
            protocol = YfySdkConstant.SCHEME_HTTP;
        } else {
            protocol = YfySdkConstant.SCHEME_HTTPS;
        }
        if(YfyAppInfo.getProtocol() != null){
            protocol =  YfyAppInfo.getProtocol();
        }
        return protocol + "https://" + YfyAppInfo.getHost().getApi() + "/preview/preview.html?preview_token=" + result.getPreviewToken();
    }

}
