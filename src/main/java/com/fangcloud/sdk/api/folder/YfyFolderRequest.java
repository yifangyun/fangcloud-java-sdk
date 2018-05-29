package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.YfyBaseClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.ItemTypeEnum;
import com.fangcloud.sdk.api.SuccessResult;
import com.fangcloud.sdk.api.collab.ListCollabResult;
import com.fangcloud.sdk.api.share_link.ListShareLinkResult;
import com.fangcloud.sdk.exception.YfyException;

import java.util.HashMap;
import java.util.Map;

public class YfyFolderRequest {
    private final static String FOLDER_API_PATH = YfySdkConstant.API_VERSION + "folder/";
    private final static String GET_PERSONAL_ITEMS_PATH = FOLDER_API_PATH + "personal_items";
    private final static String GET_DEPARTMENT_FOLDERS_PATH = FOLDER_API_PATH + "department_folders";
    private final static String GET_CHILDREN_PATH = FOLDER_API_PATH + "%s/children";
    private final static String GET_COLLAB_FOLDER_PATH = FOLDER_API_PATH + "collab_folders";
    private final static String CREATE_FOLDER_PATH = FOLDER_API_PATH + "create";
    private final static String GET_FOLDER_PATH = FOLDER_API_PATH + "%s/info";
    private final static String UPDATE_FOLDER_PATH = FOLDER_API_PATH + "%s/update";
    private final static String DELETE_FOLDER_PATH = FOLDER_API_PATH + "%s/delete";
    private final static String DELETE_FOLDER_BATCH_PATH = FOLDER_API_PATH + "delete_batch";
    private final static String DELETE_FOLDER_FROM_TRASH_PATH = FOLDER_API_PATH + "%s/delete_from_trash";
    private final static String DELETE_FOLDER_BATCH_FROM_TRASH_PATH = FOLDER_API_PATH + "delete_batch_from_trash";
    private final static String RESTORE_FOLDER_FROM_TRASH_PATH = FOLDER_API_PATH + "%s/restore_from_trash";
    private final static String RESTORE_FOLDER_BATCH_FROM_TRASH_PATH = FOLDER_API_PATH + "restore_batch_from_trash";
    private final static String MOVE_FOLDER_PATH = FOLDER_API_PATH + "%s/move";
    private final static String MOVE_FOLDER_BATCH_PATH = FOLDER_API_PATH + "move_batch";
    private final static String LIST_FOLDER_SHARE_LINK_PATH = FOLDER_API_PATH + "%s/share_links";
    private final static String LIST_COLLAB_PATH = FOLDER_API_PATH + "%s/collabs";

    private final YfyBaseClient<?>.YfyInternalClient client;

    public YfyFolderRequest(YfyBaseClient.YfyInternalClient client) {
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
     * Create a new empty folder inside the specified parent folder in the personal space
     *
     * @param name New folder name
     * @param parentId New folder's parent id in fangcloud
     * @return Detailed new folder's information
     * @throws YfyException
     */
    public YfyFolder createFolder(String name, long parentId) throws YfyException {
        return createFolder(name, parentId, null);
    }

    /**
     * Create a new empty folder inside the specified parent folder in the department space
     *
     * @param name New folder name
     * @param parentId New folder's parent id in fangcloud
     * @param departmentId New folder's department id in fangcloud(only effect when parent id is 0)
     * @return Detailed new folder's information
     * @throws YfyException
     */
    public YfyFolder createFolder(String name, long parentId, Long departmentId) throws YfyException {
        return createFolder(new CreateFolderArg(name, parentId, departmentId));
    }

    private YfyFolder createFolder(CreateFolderArg createFolderArg) throws YfyException {
        return client.doPost(CREATE_FOLDER_PATH,
                null,
                createFolderArg,
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
                YfyFolder.class);
    }

    /**
     * Delete a folder to trash
     *
     * @param folderId Folder id in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteFolder(long folderId) throws YfyException {
        String[] param = { String.valueOf(folderId) };
        return deleteFolder(DELETE_FOLDER_PATH, param, null);
    }

    // /**
    //  * Delete folders to trash
    //  *
    //  * @param folderIds Folder ids list in fangcloud
    //  * @return An object only has one attribute named success
    //  * @throws YfyException
    //  */
    // public SuccessResult deleteFolder(List<Long> folderIds) throws YfyException {
    //     if (folderIds == null || folderIds.isEmpty()) {
    //         throw new ClientValidationException("folder ids can not be null or be empty");
    //     }
    //     return deleteFolder(DELETE_FOLDER_BATCH_PATH, null, new DeleteFolderArg(folderIds));
    // }

    private SuccessResult deleteFolder(String path, String[] param, DeleteFolderArg deleteFolderArg) throws YfyException {
        return client.doPost(path,
                param,
                deleteFolderArg,
                SuccessResult.class);
    }

    /**
     * Permanently delete a specific folder that is in the trash. The folder will no longer exist in fangcloud.
     * This action cannot be undone.
     *
     * @param folderId Folder id in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteFolderFromTrash(long folderId) throws YfyException {
        String[] param = { String.valueOf(folderId) };
        return deleteFolderFromTrash(DELETE_FOLDER_FROM_TRASH_PATH, param, null);
    }

    /**
     * Permanently delete specific folders that are in the trash. The folders will no longer exist in fangcloud.
     * This action cannot be undone.
     *
     * @param folderIds Folder ids list in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    // public SuccessResult deleteFolderFromTrash(List<Long> folderIds) throws YfyException {
    //     if (folderIds == null || folderIds.isEmpty()) {
    //         throw new ClientValidationException("folder ids can not be null or be empty");
    //     }
    //     return deleteFolderFromTrash(DELETE_FOLDER_BATCH_FROM_TRASH_PATH, null, new DeleteFolderFromTrashArg(folderIds, false));
    // }

    /**
     * Permanently delete all folders that are in the trash (not including files).
     * The folders will no longer exist in fangcloud. This action cannot be undone.
     *
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    // public SuccessResult deleteAllFolderInTrash() throws YfyException {
    //     return deleteFolderFromTrash(DELETE_FOLDER_BATCH_FROM_TRASH_PATH, null, new DeleteFolderFromTrashArg(null, true));
    // }

    private SuccessResult deleteFolderFromTrash(String path, String[] param,
                                                DeleteFolderFromTrashArg deleteFolderFromTrashArg) throws YfyException {
        return client.doPost(path,
                param,
                deleteFolderFromTrashArg,
                SuccessResult.class);
    }


    /**
     * Restore a specific folder that have been moved to the trash. Default behavior is to restore the folder to the
     * parent folder it was in before it was moved to the trash
     *
     * @param folderId Folder id in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult restoreFolderFromTrash(long folderId) throws YfyException {
        String[] param = { String.valueOf(folderId) };
        return restoreFolderFromTrash(RESTORE_FOLDER_FROM_TRASH_PATH, param, null);
    }

    /**
     * Restore specific folders that have been moved to the trash. Default behavior is to restore the folder to the
     * parent folder it was in before it was moved to the trash
     *
     * @param folderIds Folder ids list in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    // public SuccessResult restoreFolderFromTrash(List<Long> folderIds) throws YfyException {
    //     if (folderIds == null || folderIds.isEmpty()) {
    //         throw new ClientValidationException("folder ids can not be null or be empty");
    //     }
    //     return restoreFolderFromTrash(RESTORE_FOLDER_BATCH_FROM_TRASH_PATH, null,
    //             new RestoreFolderFromTrashArg(folderIds, false));
    // }

    /**
     * Restore all folders that have been moved to the trash. Default behavior is to restore the folder to the
     * parent folder it was in before it was moved to the trash
     *
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    // public SuccessResult restoreAllFolderInTrash() throws YfyException {
    //     return restoreFolderFromTrash(RESTORE_FOLDER_BATCH_FROM_TRASH_PATH, null, new RestoreFolderFromTrashArg(null, true));
    // }

    private SuccessResult restoreFolderFromTrash(String path, String[] param,
                                                 RestoreFolderFromTrashArg restoreFolderFromTrashArg) throws YfyException {
        return client.doPost(path,
                param,
                restoreFolderFromTrashArg,
                SuccessResult.class);
    }

    /**
     * Move a folder to another folder
     *
     * @param folderId Folder id in fangcloud
     * @param targetFolderId Folder id of the destination folder in fangcloud
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult moveFolder(long folderId, long targetFolderId) throws YfyException {
        String[] param = { String.valueOf(folderId) };
        return moveFolder(MOVE_FOLDER_PATH, param, new MoveFolderArg(null, targetFolderId));
    }

    // /**
    //  * Move folders to another folder
    //  *
    //  * @param folderIds Folder ids list in fangcloud
    //  * @param targetFolderId Folder id of the destination folder in fangcloud
    //  * @return An object only has one attribute named success
    //  * @throws YfyException
    //  */
    // public SuccessResult moveFolder(List<Long> folderIds, long targetFolderId) throws YfyException {
    //     return moveFolder(MOVE_FOLDER_BATCH_PATH, null, new MoveFolderArg(folderIds, targetFolderId));
    // }

    private SuccessResult moveFolder(String path, String[] param, MoveFolderArg moveFolderArg) throws YfyException {
        return client.doPost(path,
                param,
                moveFolderArg,
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
        String[] params = { String.valueOf(folderId) };
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
            put(YfySdkConstant.PAGE_CAPACITY, String.valueOf(pageCapacity));
            put(YfySdkConstant.TYPE, itemType.getType());
        }};
        return getChildren(params, mapParams);
    }

    /**
     * Retrieve the files and/or folders contained in personal space.
     * The requests need paging param assigned by developer.
     *
     * @param pageId Page id begin with 0
     * @param pageCapacity Files and/or folders' list size once return
     * @param itemType Type of item. see {@link ItemTypeEnum}
     * @return Object contains two lists named "folders" and "files", and other page information
     * @throws YfyException
     */
    public GetChildrenResult getPersonalItems(
            final int pageId,
            final int pageCapacity,
            final ItemTypeEnum itemType)
            throws YfyException {
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
            put(YfySdkConstant.PAGE_CAPACITY, String.valueOf(pageCapacity));
            put(YfySdkConstant.TYPE, itemType.getType());
        }};
        return client.doGet(GET_PERSONAL_ITEMS_PATH, null, mapParams, GetChildrenResult.class);
    }

    /**
     * Retrieve the files and/or folders contained in department space.
     * The requests need paging param assigned by developer.
     *
     * @param departmentId department id in Fangcloud
     * @param pageId Page id begin with 0
     * @param pageCapacity Files and/or folders' list size once return
     * @param itemType Type of item. see {@link ItemTypeEnum}
     * @return Object contains two lists named "folders" and "files", and other page information
     * @throws YfyException
     */
    public GetChildrenResult getDepartmentFolders(
            final long departmentId,
            final int pageId,
            final int pageCapacity,
            final ItemTypeEnum itemType)
            throws YfyException {
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.DEPARTMENT_ID, String.valueOf(departmentId));
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
            put(YfySdkConstant.PAGE_CAPACITY, String.valueOf(pageCapacity));
            put(YfySdkConstant.TYPE, itemType.getType());
        }};
        return client.doGet(GET_DEPARTMENT_FOLDERS_PATH, null, mapParams, GetChildrenResult.class);
    }

    private GetChildrenResult getChildren(String[] params, Map<String, String> mapParams) throws YfyException {
        return client.doGet(GET_CHILDREN_PATH,
                params,
                mapParams,
                GetChildrenResult.class);
    }

    /**
     * Retrieve the folders that have collaboration with you. The requests need paging param assigned by developer.
     *
     * @param pageId Page id begin with 0
     * @param pageCapacity Files and/or folders' list size once return
     * @return Object contains a list named "folders", and other page information
     * @throws YfyException
     */
    public GetCollabFoldersResult getCollabFolders(final int pageId, final int pageCapacity) throws YfyException {
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
            put(YfySdkConstant.PAGE_CAPACITY, String.valueOf(pageCapacity));
        }};
        return getCollabFolders(mapParams);
    }

    private GetCollabFoldersResult getCollabFolders(Map<String, String> mapParams) throws YfyException {
        return client.doGet(GET_COLLAB_FOLDER_PATH,
                null,
                mapParams,
                GetCollabFoldersResult.class);
    }

    /**
     * List share links of a folder
     *
     * @param folderId Folder id in fangcloud
     * @param pageId Page id begin with 0
     * @return All related share links' information
     * @throws YfyException
     */
    public ListShareLinkResult listShareLink(long folderId, final int pageId) throws YfyException {
        String[] param = { String.valueOf(folderId) };
        Map<String, String> mapParams = new HashMap<String, String>(){{
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
        }};
        return listShareLink(param, mapParams);
    }

    /**
     * List folder's share links created by specific owner
     *
     * @param folderId Folder id in fangcloud
     * @param pageId Page id begin with 0
     * @param ownerId Owner id of share link you want to see
     * @return All related share links' information
     * @throws YfyException
     */
    public ListShareLinkResult listShareLink(long folderId, final int pageId, final long ownerId) throws YfyException {
        String[] param = { String.valueOf(folderId) };
        Map<String, String> mapParams = new HashMap<String, String>(){{
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
            put(YfySdkConstant.OWNER_ID, String.valueOf(ownerId));
        }};
        return listShareLink(param, mapParams);
    }

    private ListShareLinkResult listShareLink(String[] param, Map<String, String> mapParams) throws YfyException {
        return client.doGet(LIST_FOLDER_SHARE_LINK_PATH,
                param,
                mapParams,
                ListShareLinkResult.class);
    }

    /**
     * List specific folder's all collab relationships
     *
     * @param folderId Folder id in fangcloud
     * @return All collab infomations
     * @throws YfyException
     */
    public ListCollabResult listCollab(long folderId) throws YfyException {
        String[] param = { String.valueOf(folderId) };
        return listCollab(param);
    }

    private ListCollabResult listCollab(String[] param) throws YfyException {
        return client.doGet(LIST_COLLAB_PATH,
                param,
                null,
                ListCollabResult.class);
    }


}
