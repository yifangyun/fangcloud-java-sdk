package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.ItemTypeEnum;
import com.fangcloud.sdk.api.SuccessResult;
import com.fangcloud.sdk.exception.ClientValidationException;
import com.fangcloud.sdk.exception.YfyException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YfyFolderRequest {
    private final static String FOLDER_API_PATH = "api/folder/";
    private final static String GET_CHILDREN_PATH = FOLDER_API_PATH + "children";
    private final static String CREATE_FOLDER_PATH = FOLDER_API_PATH + "create";
    private final static String GET_FOLDER_PATH = FOLDER_API_PATH + "%s/info";
    private final static String UPDATE_FOLDER_PATH = FOLDER_API_PATH + "%s/update";
    private final static String DELETE_FOLDER_PATH = FOLDER_API_PATH + "delete";
    private final static String DELETE_FOLDER_FROM_TRASH_PATH = FOLDER_API_PATH + "delete_from_trash";
    private final static String RESTORE_FOLDER_FROM_TRASH_PATH = FOLDER_API_PATH + "restore_from_trash";
    private final static String MOVE_FOLDER_PATH = FOLDER_API_PATH + "move";

    private final YfyClient<?>.YfyInternalClient client;

    public YfyFolderRequest(YfyClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Retrieve detailed information about a folder
     *
     * @param folderId Folder id in fangcloud
     * @return Detailed folder's information
     * @throws YfyException
     */
    public YfyFolder getFolder(long folderId) throws YfyException {
        String[] param = { String.valueOf(folderId) };
        return getFolder(param);
    }

    private YfyFolder getFolder(String[] param) throws YfyException {
        return client.doGet(GET_FOLDER_PATH,
                param,
                null,
                YfyFolder.class);
    }

    /**
     * Create a new empty folder inside the specified parent folder
     *
     * @param name New folder name
     * @param parentId New folder's parent id in fangcloud
     * @return Detailed new folder's information
     * @throws YfyException
     */
    public YfyFolder createFolder(String name, long parentId) throws YfyException {
        return createFolder(new CreateFolderArg(name, parentId));
    }

    private YfyFolder createFolder(CreateFolderArg createFolderArg) throws YfyException {
        return client.doPost(CREATE_FOLDER_PATH,
                null,
                createFolderArg,
                YfySdkConstant.POST_METHOD,
                YfyFolder.class);
    }

    /**
     * Update the name of the folder
     *
     * @param folderId Folder id in fangcloud
     * @param newName New name of the folder
     * @return Detailed folder's information
     * @throws YfyException
     */
    public YfyFolder updateFolder(long folderId, String newName) throws YfyException {
        String[] param = { String.valueOf(folderId) };
        return updateFolder(param, new UpdateFolderArg(newName));
    }

    private YfyFolder updateFolder(String[] param, UpdateFolderArg updateFolderArg) throws YfyException {
        return client.doPost(UPDATE_FOLDER_PATH,
                param,
                updateFolderArg,
                YfySdkConstant.PUT_METHOD,
                YfyFolder.class);
    }

    /**
     * Delete folders to trash
     *
     * @param folderIds folder ids list in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteFolder(List<Long> folderIds) throws YfyException {
        if (folderIds == null || folderIds.isEmpty()) {
            throw new ClientValidationException("folder ids can not be null or be empty");
        }
        return deleteFolder(new DeleteFolderArg(folderIds));
    }

    private SuccessResult deleteFolder(DeleteFolderArg deleteFolderArg) throws YfyException {
        return client.doPost(DELETE_FOLDER_PATH,
                null,
                deleteFolderArg,
                YfySdkConstant.DELETE_METHOD,
                SuccessResult.class);
    }

    /**
     * Permanently delete specific folders that are in the trash. The file will no longer exist in fangcloud.
     * This action cannot be undone.
     *
     * @param folderIds Folder ids list in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteFolderFromTrash(List<Long> folderIds) throws YfyException {
        if (folderIds == null || folderIds.isEmpty()) {
            throw new ClientValidationException("folder ids can not be null or be empty");
        }
        return deleteFolderFromTrash(new DeleteFolderFromTrashArg(folderIds, false));
    }

    /**
     * Permanently delete all folders that are in the trash (not including files).
     * The folders will no longer exist in fangcloud. This action cannot be undone.
     *
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteAllFolderInTrash() throws YfyException {
        return deleteFolderFromTrash(new DeleteFolderFromTrashArg(null, true));
    }

    private SuccessResult deleteFolderFromTrash(DeleteFolderFromTrashArg deleteFolderFromTrashArg) throws YfyException {
        return client.doPost(DELETE_FOLDER_FROM_TRASH_PATH,
                null,
                deleteFolderFromTrashArg,
                YfySdkConstant.DELETE_METHOD,
                SuccessResult.class);
    }

    /**
     * Restore specific folders that have been moved to the trash. Default behavior is to restore the folder to the
     * parent folder it was in before it was moved to the trash
     *
     * @param folderIds folder ids in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult restoreFolderFromTrash(List<Long> folderIds) throws YfyException {
        if (folderIds == null || folderIds.isEmpty()) {
            throw new ClientValidationException("folder ids can not be null or be empty");
        }
        return restoreFolderFromTrash(new RestoreFolderFromTrashArg(folderIds, false));
    }

    /**
     * Restore all folders that have been moved to the trash. Default behavior is to restore the folder to the
     * parent folder it was in before it was moved to the trash
     *
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult restoreAllFolderInTrash() throws YfyException {
        return restoreFolderFromTrash(new RestoreFolderFromTrashArg(null, true));
    }

    private SuccessResult restoreFolderFromTrash(RestoreFolderFromTrashArg restoreFolderFromTrashArg) throws YfyException {
        return client.doPost(RESTORE_FOLDER_FROM_TRASH_PATH,
                null,
                restoreFolderFromTrashArg,
                YfySdkConstant.POST_METHOD,
                SuccessResult.class);
    }

    /**
     * Move folders to another folder
     *
     * @param folderIds Folder ids in fangcloud
     * @param targetFolderId Folder id of the destination folder in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    private SuccessResult moveFolder(List<Long> folderIds, long targetFolderId) throws YfyException {
        return moveFolder(new MoveFolderArg(folderIds, targetFolderId));
    }

    private SuccessResult moveFolder(MoveFolderArg moveFolderArg) throws YfyException {
        return client.doPost(MOVE_FOLDER_PATH,
                null,
                moveFolderArg,
                YfySdkConstant.POST_METHOD,
                SuccessResult.class);
    }

    /**
     * Retrieve the files and/or folders contained within this folder without any other info about the folder.
     * The requests need paging param assigned by developer.
     *
     * @param folderId Folder id in fangcloud
     * @param pageId Page id begin with 0
     * @param pageCapacity Files and/or folders' list size once return
     * @param itemType Type of item. see {@link ItemTypeEnum}
     * @return Object contains two lists named "folders" and "files", and other page information
     * @throws YfyException
     */
    public GetChildrenResult getChildren(final long folderId,
                                         final int pageId,
                                         final int pageCapacity,
                                         final ItemTypeEnum itemType)
            throws YfyException {
        Map<String, String> params = new HashMap<String, String>() {{
            put(YfySdkConstant.FOLDER_ID, String.valueOf(folderId));
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
            put(YfySdkConstant.PAGE_CAPACITY, String.valueOf(pageCapacity));
            put(YfySdkConstant.TYPE, itemType.getType());
        }};
        return getChildren(params);
    }

    private GetChildrenResult getChildren(Map<String, String> params) throws YfyException {
        return client.doGet(GET_CHILDREN_PATH,
                null,
                params,
                GetChildrenResult.class);
    }
}
