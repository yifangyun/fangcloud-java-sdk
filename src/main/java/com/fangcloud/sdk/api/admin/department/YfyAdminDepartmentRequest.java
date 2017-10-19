package com.fangcloud.sdk.api.admin.department;

import com.fangcloud.sdk.YfyBaseClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.exception.YfyException;

import java.util.HashMap;
import java.util.Map;

public class YfyAdminDepartmentRequest {
    private final static String ADMIN_DEPARTMENT_PATH = YfySdkConstant.API_VERSION + "admin/department/";
    private final static String SEARCH_DEPARTMENT_USERS_PATH = ADMIN_DEPARTMENT_PATH + "%s/users";

    private final YfyBaseClient<?>.YfyInternalClient client;

    public YfyAdminDepartmentRequest(YfyBaseClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Retrieve detailed members info of the department
     *
     * @param departmentId Department id search in
     * @param queryWords Query words about user info
     * @param pageId Page id begin with 0
     * @return Detailed members info
     * @throws YfyException
     */
    public AdminDepartmentUserResult getDepartmentUsers(long departmentId, final String queryWords, final int pageId) throws YfyException {
        String[] listParams = { String.valueOf(departmentId) };
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.QUERY_WORDS, queryWords);
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
        }};
        return getDepartmentUsers(listParams, mapParams);
    }

    private AdminDepartmentUserResult getDepartmentUsers(String[] listParams, Map<String, String> mapParams) throws YfyException {
        return client.doGet(SEARCH_DEPARTMENT_USERS_PATH,
                listParams,
                mapParams,
                AdminDepartmentUserResult.class);
    }

}
