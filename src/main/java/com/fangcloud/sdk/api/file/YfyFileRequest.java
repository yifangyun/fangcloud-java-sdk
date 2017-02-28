package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.SuccessResult;
import com.fangcloud.sdk.exception.NetworkIOException;
import com.fangcloud.sdk.exception.YfyException;
import com.fangcloud.sdk.util.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class YfyFileRequest {
    private final static String GET_FILE_PATH = "api/file/%s/info";
    private final static String COPY_FILE_PATH = "api/file/copy";
    private final static String PRE_SIGNATURE_DOWNLOAD_PATH = "api/file/%s/download";
    private final static String PRE_SIGNATURE_UPLOAD_PATH = "api/file/upload";
    private final static String NEW_VERSION_PRE_SIGNATURE_UPLOAD_PATH = "api/file/%s/new_version";
    private final static String UPDATE_FILE_PATH = "api/file/%s/update";
    private final static String DELETE_FILE_PATH = "api/file/delete";
    private final static String DELETE_FILE_FROM_TRASH_PATH = "api/file/delete_from_trash";
    private final static String RESTORE_FILE_FROM_TRASH_PATH = "api/file/restore_from_trash";
    private final static String MOVE_FILE_PATH = "api/file/move";
    private final static String PREVIEW_PATH = "api/file/%s/preview";
    private final static String DOWNLOAD_PREVIEW_PATH = "api/file/%s/preview_download";

    private final YfyClient<?>.YfyInternalClient client;

    public YfyFileRequest(YfyClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Retrieve file info
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
     * Update fields in file, including file name and file description
     *
     * @param fileId File id in fangcloud
     * @param newName File new name, can not be null
     * @param newDescription File new description, can be null
     * @return {@link YfyFile} model with file info
     * @throws YfyException
     */
    public YfyFile updateFile(long fileId, String newName, String newDescription) throws YfyException {
        String[] params = { String.valueOf(fileId) };
        return updateFile(params, new UpdateFileArg(newName, newDescription));
    }

    private YfyFile updateFile(String[] params, UpdateFileArg updateFileArg) throws YfyException {
        return client.doPost(client.getHost().getApi(),
                UPDATE_FILE_PATH,
                params,
                updateFileArg,
                YfySdkConstant.PUT_METHOD,
                YfyFile.class);
    }

    /**
     * Discard files to the trash
     *
     * @param fileIds File ids list in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteFile(List<Long> fileIds) throws YfyException {
        return deleteFile(new DeleteFileArg(fileIds));
    }

    private SuccessResult deleteFile(DeleteFileArg deleteFileArg) throws YfyException {
        return client.doPost(client.getHost().getApi(),
                DELETE_FILE_PATH,
                null,
                deleteFileArg,
                YfySdkConstant.DELETE_METHOD,
                SuccessResult.class);
    }

    /**
     * Permanently delete files that are in the trash. The file will no longer exist in fangcloud.
     * This action cannot be undone.
     *
     * @param fileIds File ids list in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteFileFromTrash(List<Long> fileIds) throws YfyException {
        if (fileIds == null || fileIds.isEmpty()) {
            throw new IllegalArgumentException("file ids can not be null or be empty");
        }
        return deleteFileFromTrash(new DeleteFileFromTrashArg(fileIds, false));
    }

    /**
     * Permanently delete all files that are in the trash (not including folders).
     * The files will no longer exist in fangcloud. This action cannot be undone.
     *
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteAllFileInTrash() throws YfyException {
        return deleteFileFromTrash(new DeleteFileFromTrashArg(null, true));
    }

    private SuccessResult deleteFileFromTrash(DeleteFileFromTrashArg deleteFileFromTrashArg) throws YfyException {
        return client.doPost(client.getHost().getApi(),
                DELETE_FILE_FROM_TRASH_PATH,
                null,
                deleteFileFromTrashArg,
                YfySdkConstant.DELETE_METHOD,
                SuccessResult.class);
    }

    /**
     * Restore files that have been moved to the trash. Default behavior is to restore the file to the folder
     * it was in before it was moved to the trash
     *
     * @param fileIds File ids list in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult restoreFileFromTrash(List<Long> fileIds) throws YfyException {
        if (fileIds == null || fileIds.isEmpty()) {
            throw new IllegalArgumentException("file ids can not be null or be empty");
        }
        return restoreFileFromTrash(new RestoreFileFromTrashArg(fileIds, false));
    }

    /**
     * Restore all files that have been moved to the trash. Default behavior is to restore the file to the folder
     * it was in before it was moved to the trash
     *
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult restoreAllFileInTrash() throws YfyException {
        return restoreFileFromTrash(new RestoreFileFromTrashArg(null, true));
    }

    private SuccessResult restoreFileFromTrash(RestoreFileFromTrashArg restoreFileFromTrashArg) throws YfyException {
        return client.doPost(client.getHost().getApi(),
                RESTORE_FILE_FROM_TRASH_PATH,
                null,
                restoreFileFromTrashArg,
                YfySdkConstant.POST_METHOD,
                SuccessResult.class);
    }

    /**
     * Move files to another folder
     *
     * @param fileIds File ids list in fangcloud
     * @param targetFolderId Folder id of the destination folder in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult moveFile(List<Long> fileIds, long targetFolderId) throws YfyException {
        return moveFile(new MoveFileArg(fileIds, targetFolderId));
    }

    private SuccessResult moveFile(MoveFileArg moveFileArg) throws YfyException {
        return client.doPost(client.getHost().getApi(),
                MOVE_FILE_PATH,
                null,
                moveFileArg,
                YfySdkConstant.POST_METHOD,
                SuccessResult.class);
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
            IOUtil.copyStreamToFile(body, file);
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
     * Get the new version file upload url, then use the url to upload new version file.
     * The url is valid in an hour and can only be used for one time
     *
     * @param fileId Origin file id in fangcloud
     * @param name File name of the new version
     * @param remark remark of the new version
     * @return An object contains a attribute named upload url
     * @throws YfyException
     */
    public PreSignatureUploadResult newVersionPreSignatureUpload(long fileId, String name, String remark)
            throws YfyException {
        String[] param = { String.valueOf(fileId) };
        return newVersionPreSignatureUpload(param, new NewVersionPreSignatureUploadArg(name, "api", remark));
    }

    private PreSignatureUploadResult newVersionPreSignatureUpload(
            String[] param, NewVersionPreSignatureUploadArg newVersionPreSignatureUploadArg)
            throws YfyException {
        return client.doPost(client.getHost().getApi(),
                NEW_VERSION_PRE_SIGNATURE_UPLOAD_PATH,
                param,
                newVersionPreSignatureUploadArg,
                YfySdkConstant.POST_METHOD,
                PreSignatureUploadResult.class);
    }

    /**
     * When get a file upload url, can use this method to upload the file to the server
     *
     * @param uploadUrl Upload url returned by the {@link this#preSignatureUpload(long,String)}
     * @param filePath The file path which you'd like upload to server
     * @throws YfyException
     */
    public YfyFile uploadFile(String uploadUrl, String filePath) throws YfyException {
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
    public YfyFile directUploadFile(long parentId, String name, String filePath) throws YfyException {
        return uploadFile(preSignatureUpload(parentId, name).getUploadUrl(), filePath);
    }

    /**
     * Retrieve preview image information and download url()
     *
     * @param fileId File id in fangcloud
     * @param previewKind The kinds of preview, see {@link PreviewKindEnum}
     * @param forceRegenerate if true, the preview image will regenerate force
     * @return Information about the preview result
     * @throws YfyException
     */
    public PreviewResult preview(long fileId, PreviewKindEnum previewKind, boolean forceRegenerate) throws YfyException {
        String[] param = { String.valueOf(fileId) };
        return preview(param, new PreviewArg(previewKind.getKind(), forceRegenerate));
    }

    private PreviewResult preview(String[] param, PreviewArg previewArg) throws YfyException {
        return client.doPost(client.getHost().getApi(),
                PREVIEW_PATH,
                param,
                previewArg,
                YfySdkConstant.POST_METHOD,
                PreviewResult.class);
    }

    /**
     * Retrieve download url if the page count of result to {@link this#preview(long,PreviewKindEnum,boolean)} more
     * than 1. Notice this interface will not trigger preview transform.
     *
     * @param fileId File id in fangcloud
     * @param pageIndex Less than the page count return by {@link this#preview(long,PreviewKindEnum,boolean)}
     * @param previewKind The kinds of preview, see {@link PreviewKindEnum}
     * @return Download url means success. Status attribute value "failed" means generating preview fail,
     *         "ungenerated" means not invoke {@link this#preview(long,PreviewKindEnum,boolean)} before
     * @throws YfyException
     */
    public DownloadPreviewResult downloadPreview(long fileId, int pageIndex, PreviewKindEnum previewKind)
            throws YfyException {
        String[] param = { String.valueOf(fileId) };
        return downloadPreview(param, new DownloadPreviewArg(pageIndex, previewKind.getKind()));
    }

    private DownloadPreviewResult downloadPreview(String[] param, DownloadPreviewArg downloadPreviewArg)
            throws YfyException {
        return client.doPost(client.getHost().getApi(),
                DOWNLOAD_PREVIEW_PATH,
                param,
                downloadPreviewArg,
                YfySdkConstant.POST_METHOD,
                DownloadPreviewResult.class);
    }

    /**
     * Copy the specific file to a folder
     *
     * @param fileId File id in fangcloud
     * @param targetFolderId Id of the destination folder in fangcloud
     * @return {@link YfyFile} model with the new file info
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
}
