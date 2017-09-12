package com.fangcloud.sdk.api.department;

import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.CollabRoleEnum;
import com.fangcloud.sdk.api.SuccessResult;
import com.fangcloud.sdk.api.collab.InviteCollabArg;
import com.fangcloud.sdk.api.collab.UpdateCollabArg;
import com.fangcloud.sdk.api.collab.YfyCollab;
import com.fangcloud.sdk.exception.YfyException;

import java.util.HashMap;
import java.util.Map;

public class YfyDepartmentRequest {
    private final static String DEPARTMENT_PATH = YfySdkConstant.API_VERSION + "department/";
    private final static String DEPARTMENT_INFO_PATH = DEPARTMENT_PATH + "%s/info";
    private final static String DEPARTMENT_CHILDREN_PATH = DEPARTMENT_PATH + "%s/children";

    private final YfyClient<?>.YfyInternalClient client;

    public YfyDepartmentRequest(YfyClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Retrieve detailed department information
     *
     * @return Detailed department information
     * @throws YfyException
     */
    public YfyDepartment getDepartment(long departmentId) throws YfyException {
        String[] params = { String.valueOf(departmentId) };
        return getDepartment(params);
    }

    private YfyDepartment getDepartment(String[] params) throws YfyException {
        return client.doGet(DEPARTMENT_INFO_PATH,
                params,
                null,
                YfyDepartment.class);
    }

    /**
     * Retrieve department children
     *
     * @return Department children
     * @throws YfyException
     */
    public DepartmentChildrenResult getDepartmentChildren(long departmentId, final boolean permissionFilter) throws YfyException {
        String[] params = {String.valueOf(departmentId) };
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.PERMISSION_FILTER, String.valueOf(permissionFilter));
        }};
        return getDepartmentChildren(params, mapParams);
    }

    private DepartmentChildrenResult getDepartmentChildren(String[] params, Map<String, String> mapParams) throws YfyException {
        return client.doGet(DEPARTMENT_CHILDREN_PATH,
                params,
                mapParams,
                DepartmentChildrenResult.class);
    }


}
