package com.fangcloud.sdk.api.admin.platform;

import com.fangcloud.sdk.YfyBaseClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.admin.user.YfyDetailedUser;
import com.fangcloud.sdk.exception.YfyException;

import java.util.HashMap;
import java.util.Map;

public class YfyAdminPlatformRequest {
    private final static String ADMIN_PLATFORM_PATH = YfySdkConstant.API_VERSION + "admin/platform/";
    private final static String MAPPING_USER_PATH = ADMIN_PLATFORM_PATH + "%s/mapping_user";
    private final static String MAPPING_DEPARTMENT_PATH = ADMIN_PLATFORM_PATH + "%s/mapping_department";
    private final static String MAPPING_GROUP_PATH = ADMIN_PLATFORM_PATH + "%s/mapping_group";

    private final YfyBaseClient<?>.YfyInternalClient client;

    public YfyAdminPlatformRequest(YfyBaseClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * get a mapping user's information
     *
     * @param platformId Platform id in fangcloud（available only when enterprise open sync account）
     * @param userId Platform user id in custom system（can be any string）
     * @return Detailed user information only seen by admin
     * @throws YfyException
     */
    public YfyDetailedUser getMappingUser(long platformId, final String userId) throws YfyException {
        String[] listParams = { String.valueOf(platformId) };
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.USER_ID, userId);
        }};
        return getMappingUser(listParams, mapParams);
    }

    /**
     * get a mapping user's information
     *
     * @param platformId Platform id in fangcloud（available only when enterprise open sync account）
     * @param yfyUserId Fangcloud User id
     * @return Detailed user information only seen by admin
     * @throws YfyException
     */
    public YfyDetailedUser getMappingUser(long platformId, final long yfyUserId) throws YfyException {
        String[] listParams = { String.valueOf(platformId) };
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.YFY_USER_ID, String.valueOf(yfyUserId));
        }};
        return getMappingUser(listParams, mapParams);
    }

    /**
     * get a mapping department's information
     *
     * @param platformId Platform id in fangcloud（available only when enterprise open sync account）
     * @param departmentId Platform department id in custom system（can be any string）
     * @return Detailed department information only seen by admin
     * @throws YfyException
     */
    public YfyMappingDepartment getMappingDepartment(long platformId, final String departmentId) throws YfyException {
        String[] listParams = { String.valueOf(platformId) };
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.DEPARTMENT_ID, departmentId);
        }};
        return getMappingDepartment(listParams, mapParams);
    }

    /**
     * get a mapping department's information
     *
     * @param platformId Platform id in fangcloud（available only when enterprise open sync account）
     * @param yfyDepartmentId Fangcloud Department id
     * @return Detailed department information only seen by admin
     * @throws YfyException
     */
    public YfyMappingDepartment getMappingDepartment(long platformId, final long yfyDepartmentId) throws YfyException {
        String[] listParams = { String.valueOf(platformId) };
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.YFY_DEPARTMENT_ID, String.valueOf(yfyDepartmentId));
        }};
        return getMappingDepartment(listParams, mapParams);
    }

    /**
     * get a mapping group's information
     *
     * @param platformId Platform id in fangcloud（available only when enterprise open sync account）
     * @param groupId Platform group id in custom system（can be any string）
     * @return Detailed group information only seen by admin
     * @throws YfyException
     */
    public YfyMappingGroup getMappingGroup(long platformId, final String groupId) throws YfyException {
        String[] listParams = { String.valueOf(platformId) };
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.GROUP_ID, groupId);
        }};
        return getMappingGroup(listParams, mapParams);
    }

    /**
     * get a mapping group's information
     *
     * @param platformId Platform id in fangcloud（available only when enterprise open sync account）
     * @param yfyGroupId Fangcloud Group id
     * @return Detailed group information only seen by admin
     * @throws YfyException
     */
    public YfyMappingGroup getMappingGroup(long platformId, final long yfyGroupId) throws YfyException {
        String[] listParams = { String.valueOf(platformId) };
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.YFY_GROUP_ID, String.valueOf(yfyGroupId));
        }};
        return getMappingGroup(listParams, mapParams);
    }

    private YfyMappingUser getMappingUser(String[] listParams, Map<String, String> mapParams) throws YfyException {
        return client.doGet(MAPPING_USER_PATH,
                listParams,
                mapParams,
                YfyMappingUser.class);
    }

    private YfyMappingDepartment getMappingDepartment(String[] listParams, Map<String, String> mapParams) throws YfyException {
        return client.doGet(MAPPING_DEPARTMENT_PATH,
                listParams,
                mapParams,
                YfyMappingDepartment.class);
    }

    private YfyMappingGroup getMappingGroup(String[] listParams, Map<String, String> mapParams) throws YfyException {
        return client.doGet(MAPPING_GROUP_PATH,
                listParams,
                mapParams,
                YfyMappingGroup.class);
    }
}
