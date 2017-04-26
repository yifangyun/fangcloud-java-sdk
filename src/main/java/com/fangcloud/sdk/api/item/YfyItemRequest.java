package com.fangcloud.sdk.api.item;

import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.ItemTypeEnum;
import com.fangcloud.sdk.exception.YfyException;

import java.util.HashMap;
import java.util.Map;

public class YfyItemRequest {
    private final static String ITEM_API_PATH = YfySdkConstant.API_VERSION + "item/";
    private final static String SEARCH_ITEM_PATH = ITEM_API_PATH + "search";

    private final YfyClient<?>.YfyInternalClient client;

    public YfyItemRequest(YfyClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Retrieve relevant files and/or folders with query words in user's account.
     * The requests need paging param assigned by developer. Every page return 20 items.
     *
     * @param queryWords key words want to search for
     * @param itemType Type of item. see {@link ItemTypeEnum}
     * @param pageNumber Page number begin with 0
     * @param searchInFolder Assign the folder id to search in specific folder, usually 0
     * @return Object contains two lists named "folders" and "files", and other page information
     * @throws YfyException
     */
    public SearchItemResult searchItem(final String queryWords,
                                       final ItemTypeEnum itemType,
                                       final int pageNumber,
                                       final long searchInFolder)
            throws YfyException {
        Map<String, String> params = new HashMap<String, String>() {{
            put(YfySdkConstant.QUERY_WORDS, queryWords);
            put(YfySdkConstant.TYPE, itemType.getType());
            put(YfySdkConstant.PAGE_NUMBER, String.valueOf(pageNumber));
            put(YfySdkConstant.SEARCH_IN_FOLDER, String.valueOf(searchInFolder));
        }};
        return searchInFolder(params);
    }

    private SearchItemResult searchInFolder(Map<String, String> params) throws YfyException {
        return client.doGet(SEARCH_ITEM_PATH,
                null,
                params,
                SearchItemResult.class);
    }

}
