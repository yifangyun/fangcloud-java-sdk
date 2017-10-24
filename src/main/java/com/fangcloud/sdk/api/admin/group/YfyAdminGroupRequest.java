package com.fangcloud.sdk.api.admin.group;

import com.fangcloud.sdk.YfyBaseClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.admin.department.AdminDepartmentUserResult;
import com.fangcloud.sdk.exception.YfyException;

import java.util.HashMap;
import java.util.Map;

public class YfyAdminGroupRequest {
    private final static String ADMIN_GROUP_PATH = YfySdkConstant.API_VERSION + "admin/group/";
    private final static String LIST_GROUPS_PATH = ADMIN_GROUP_PATH + "list";

    private final YfyBaseClient<?>.YfyInternalClient client;

    public YfyAdminGroupRequest(YfyBaseClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Retrieve detailed groups info
     *
     * @param queryWords Query words about user info
     * @param pageId Page id begin with 0
     * @return Detailed groups info
     * @throws YfyException
     */
    public AdminGroupListResult getGrouplist(final String queryWords, final int pageId) throws YfyException {
        Map<String, String> params = new HashMap<String, String>() {{
            put(YfySdkConstant.QUERY_WORDS, queryWords);
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
        }};
        return getGrouplist(params);
    }

    private AdminGroupListResult getGrouplist(Map<String, String> params) throws YfyException {
        return client.doGet(LIST_GROUPS_PATH,
                null,
                params,
                AdminGroupListResult.class);
    }

}
