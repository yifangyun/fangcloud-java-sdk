package com.fangcloud.sdk.api.folder;

import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.exception.YfyException;

import java.util.HashMap;
import java.util.Map;

public class YfyFolderRequest {
    private static String GET_CHILDREN_PATH = "api/folder/children";

    private final YfyClient client;

    public YfyFolderRequest(YfyClient client) {
        this.client = client;
    }

    /**
     * Retrieves the files and/or folders contained within this folder without any other info about the folder.
     * The requests need paging param assigned by developer.
     *
     * @param folderId folder id in fangcloud
     * @param pageId page id begin with 0
     * @param pageCapacity files and/or folders' list size once return
     * @param type type of item. "file", "folder", or "item"
     * @return contain two lists with folders and files
     * @throws YfyException
     */
    public GetChildrenResult getChildren(final long folderId,
                                         final int pageId,
                                         final int pageCapacity,
                                         final String type)
            throws YfyException {
        Map<String, String> params = new HashMap<String, String>() {{
            put(YfySdkConstant.FOLDER_ID, String.valueOf(folderId));
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
            put(YfySdkConstant.PAGE_CAPACITY, String.valueOf(pageCapacity));
            put(YfySdkConstant.TYPE, type);
        }};
        return getChildren(params);
    }

    private GetChildrenResult getChildren(Map<String, String> params) throws YfyException {
        return client.doGet(client.getHost().getApi(),
                GET_CHILDREN_PATH,
                null,
                params,
                GetChildrenResult.class);
    }
}
