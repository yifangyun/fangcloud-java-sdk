package com.fangcloud.sdk.api.trash;

import com.fangcloud.sdk.YfyBaseClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.ItemTypeEnum;
import com.fangcloud.sdk.api.SuccessResult;
import com.fangcloud.sdk.exception.YfyException;

public class YfyTrashRequest {
    private final static String TRASH_API_PATH = YfySdkConstant.API_VERSION + "trash/";
    private final static String CLEAR_TRASH_PATH = TRASH_API_PATH + "clear";
    private final static String RESTORE_TRASH_PATH = TRASH_API_PATH + "restore_all";

    private final YfyBaseClient<?>.YfyInternalClient client;

    public YfyTrashRequest(YfyBaseClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Permanently delete all items(file or folder or all) in trash
     *
     * @param itemType Type of item.
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult clearTrash(final ItemTypeEnum itemType) throws YfyException {
        return clearTrash(new TrashArg(itemType.getType()));
    }

    private SuccessResult clearTrash(TrashArg trashArg) throws YfyException {
        return client.doPost(CLEAR_TRASH_PATH,
                null,
                trashArg,
                SuccessResult.class);
    }

    /**
     * Restore all items(file or folder or all) in trash
     *
     * @param itemType Type of item.
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult restoreTrash(final ItemTypeEnum itemType) throws YfyException {
        return restoreTrash(new TrashArg(itemType.getType()));
    }

    private SuccessResult restoreTrash(TrashArg trashArg) throws YfyException {
        return client.doPost(RESTORE_TRASH_PATH,
                null,
                trashArg,
                SuccessResult.class);
    }

}
