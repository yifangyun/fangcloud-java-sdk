package com.fangcloud.sdk.api.admin.user;

import com.fangcloud.sdk.YfyBaseClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.IdentifierTypeEnum;
import com.fangcloud.sdk.api.SuccessResult;
import com.fangcloud.sdk.api.user.DeleteUserArg;
import com.fangcloud.sdk.exception.YfyException;

import java.util.HashMap;
import java.util.Map;

public class YfyAdminUserRequest {
    private final static String ADMIN_USER_PATH = YfySdkConstant.API_VERSION + "admin/user/";
    private final static String DELETE_USER_PATH = ADMIN_USER_PATH + "%s/delete";
    private final static String GET_LOGIN_URL_PATH = ADMIN_USER_PATH + "get_login_url";
    private final static String GET_LOGIN_PARAMS_PATH = ADMIN_USER_PATH + "get_login_params";

    private final YfyBaseClient<?>.YfyInternalClient client;

    public YfyAdminUserRequest(YfyBaseClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Delete a user with handing over files. (Attention! This operation can not revoke.)
     *
     * @param userId        User id in fangcloud
     * @param receiveUserId User id who files of delete user will package to, can be null if the delete user has no files
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteUser(long userId, Long receiveUserId) throws YfyException {
        String[] param = {String.valueOf(userId)};
        return deleteUser(DELETE_USER_PATH, param, new DeleteUserArg(receiveUserId));
    }

    private SuccessResult deleteUser(String path, String[] param, DeleteUserArg deleteUserArg) throws YfyException {
        return client.doPost(path,
                param,
                deleteUserArg,
                SuccessResult.class);
    }


    public GetUserLoginUrlResult getUserLoginUrl(final String identifier, final IdentifierTypeEnum identifierType) throws YfyException {
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.IDENTIFIER, identifier);
            put(YfySdkConstant.TYPE, identifierType.getType());
        }};
        return client.doGet(GET_LOGIN_URL_PATH, null, mapParams, GetUserLoginUrlResult.class);
    }

    public GetUserLoginParamsResult getUserLoginParams(final String identifier, final IdentifierTypeEnum identifierType) throws YfyException {
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.IDENTIFIER, identifier);
            put(YfySdkConstant.TYPE, identifierType.getType());
        }};
        return client.doGet(GET_LOGIN_PARAMS_PATH, null, mapParams, GetUserLoginParamsResult.class);
    }
}
