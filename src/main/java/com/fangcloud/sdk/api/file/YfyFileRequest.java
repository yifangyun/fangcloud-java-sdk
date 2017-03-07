package com.fangcloud.sdk.api.file;

import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.api.SuccessResult;
import com.fangcloud.sdk.exception.ClientValidationException;
import com.fangcloud.sdk.exception.NetworkIOException;
import com.fangcloud.sdk.exception.YfyException;
import com.fangcloud.sdk.util.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class YfyFileRequest {
    private final static String FILE_API_PATH = "api/file/";
    private final static String GET_FILE_PATH = FILE_API_PATH + "%s/info";
    private final static String COPY_FILE_PATH = FILE_API_PATH + "copy";
    private final static String PRE_SIGNATURE_DOWNLOAD_PATH = FILE_API_PATH + "%s/download";
    private final static String PRE_SIGNATURE_UPLOAD_PATH = FILE_API_PATH + "upload";
    private final static String NEW_VERSION_PRE_SIGNATURE_UPLOAD_PATH = FILE_API_PATH + "%s/new_version";
    private final static String UPDATE_FILE_PATH = FILE_API_PATH + "%s/update";
    private final static String DELETE_FILE_BATCH_PATH = FILE_API_PATH + "delete_batch";
    private final static String DELETE_FILE_PATH = FILE_API_PATH + "%s/delete";
    private final static String DELETE_FILE_FROM_TRASH_PATH = FILE_API_PATH + "%s/delete_from_trash";
    private final static String DELETE_FILE_BATCH_FROM_TRASH_PATH = FILE_API_PATH + "delete_batch_from_trash";
    private final static String RESTORE_FILE_BATCH_FROM_TRASH_PATH = FILE_API_PATH + "restore_batch_from_trash";
    private final static String RESTORE_FILE_FROM_TRASH_PATH = FILE_API_PATH + "%s/restore_from_trash";
    private final static String MOVE_FILE_PATH = FILE_API_PATH + "%s/move";
    private final static String MOVE_FILE_BATCH_PATH = FILE_API_PATH + "move_batch";
    private final static String PREVIEW_PATH = FILE_API_PATH + "%s/preview";
    private final static String DOWNLOAD_PREVIEW_PATH = FILE_API_PATH + "%s/preview_download";

    private final YfyClient<?>.YfyInternalClient client;

    public YfyFileRequest(YfyClient.YfyInternalClient client) {
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

    /**
     * Discard files to the trash
     *
     * @param fileIds File ids list in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteFile(List<Long> fileIds) throws YfyException {
        return deleteFile(DELETE_FILE_BATCH_PATH, null, new DeleteFileArg(fileIds));
    }

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
    public SuccessResult deleteFileFromTrash(List<Long> fileIds) throws YfyException {
        if (fileIds == null || fileIds.isEmpty()) {
            throw new ClientValidationException("file ids can not be null or be empty");
        }
        return deleteFileFromTrash(DELETE_FILE_BATCH_FROM_TRASH_PATH, null, new DeleteFileFromTrashArg(fileIds, false));
    }

    /**
     * Permanently delete all files that are in the trash (not including folders).
     * The files will no longer exist in fangcloud. This action cannot be undone.
     *
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteAllFileInTrash() throws YfyException {
        return deleteFileFromTrash(DELETE_FILE_BATCH_FROM_TRASH_PATH, null, new DeleteFileFromTrashArg(null, true));
    }

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
    public SuccessResult restoreFileFromTrash(List<Long> fileIds) throws YfyException {
        if (fileIds == null || fileIds.isEmpty()) {
            throw new ClientValidationException("file ids can not be null or be empty");
        }
        return restoreFileFromTrash(RESTORE_FILE_BATCH_FROM_TRASH_PATH, null, new RestoreFileFromTrashArg(fileIds, false));
    }

    /**
     * Restore all files that have been moved to the trash. Default behavior is to restore the file to the folder
     * it was in before it was moved to the trash
     *
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult restoreAllFileInTrash() throws YfyException {
        return restoreFileFromTrash(RESTORE_FILE_BATCH_FROM_TRASH_PATH, null, new RestoreFileFromTrashArg(null, true));
    }

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

    /**
     * Move files to another folder
     *
     * @param fileIds File ids list in fangcloud
     * @param targetFolderId Folder id of the destination folder in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult moveFile(List<Long> fileIds, long targetFolderId) throws YfyException {
        return moveFile(MOVE_FILE_BATCH_PATH, null, new MoveFileArg(fileIds, targetFolderId));
    }

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
     * @return An object contains a map with file id as key and download url as value
     * @throws YfyException
     */
    public PreSignatureDownloadResult preSignatureDownload(long fileId) throws YfyException {
        String[] params = { String.valueOf(fileId) };
        return preSignatureDownload(params);
    }

    private PreSignatureDownloadResult preSignatureDownload(String[] params) throws YfyException {
        return client.doGet(PRE_SIGNATURE_DOWNLOAD_PATH,
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
    public PreSignatureUploadResult newVersionPreSignatureUpload(long fileId, String name, String remark)
            throws YfyException {
        String[] param = { String.valueOf(fileId) };
        return newVersionPreSignatureUpload(param, new NewVersionPreSignatureUploadArg(name, "api", remark));
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
     * When get a file upload url, can use this method to upload the file to the server
     *
     * @param uploadUrl Upload url returned by the {@link this#preSignatureUpload(long,String)}
     * @param fileStream The file stream which you'd like upload to server
     * @param fileName Name of the upload file
     * @return Detailed new file info
     * @throws YfyException
     */
    public YfyFile uploadFile(String uploadUrl, InputStream fileStream, String fileName) throws YfyException {
        return client.doUpload(uploadUrl, fileStream, fileName);
    }

    /**
     * Combine the {@link this#preSignatureUpload(long, String)}
     * and {@link this#uploadFile(String,InputStream,String)} method, direct upload the file
     *
     * @param parentId Parent folder id you want to store the file in, the root folder is 0
     * @param name File name
     * @param fileStream The file stream which you'd like upload to server
     * @return Detailed new file's information
     * @throws YfyException
     */
    public YfyFile directUploadFile(long parentId, String name, InputStream fileStream) throws YfyException {
        return uploadFile(preSignatureUpload(parentId, name).getUploadUrl(), fileStream, name);
    }

    /**
     * Combine the {@link this#newVersionPreSignatureUpload(long,String,String)}
     * and {@link this#uploadFile(String,InputStream,String)} method, direct upload the new version file
     *
     * @param fileId File id that you want to upload the new version to
     * @param name New version file name
     * @param remark Remark of the new version
     * @param fileStream The file stream which you'd like upload to server
     * @return Detailed new file's information
     * @throws YfyException
     */
    public YfyFile directUploadNewVersionFile(long fileId, String name, String remark, InputStream fileStream) throws YfyException {
        return uploadFile(newVersionPreSignatureUpload(fileId, name, remark).getUploadUrl(), fileStream, name);
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
        return client.doPost(PREVIEW_PATH,
                param,
                previewArg,
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
        return client.doPost(DOWNLOAD_PREVIEW_PATH,
                param,
                downloadPreviewArg,
                DownloadPreviewResult.class);
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
        return copyFile(new CopyFileArg(fileId, targetFolderId));
    }

    private YfyFile copyFile(CopyFileArg copyFileArg) throws YfyException {
        return client.doPost(COPY_FILE_PATH,
                null,
                copyFileArg,
                YfyFile.class);
    }
}
