package com.fangcloud.sdk.api.collab;

import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.AccessibleByTypeEnum;
import com.fangcloud.sdk.api.CollabRoleEnum;
import com.fangcloud.sdk.api.SuccessResult;
import com.fangcloud.sdk.exception.YfyException;

public class YfyCollabRequest {
    private final static String COLLAB_PATH = YfySdkConstant.API_VERSION + "collab/";
    private final static String COLLAB_INFO_PATH = COLLAB_PATH + "%s/info";
    private final static String INVITE_COLLAB_PATH = COLLAB_PATH + "invite";
    private final static String UPDATE_COLLAB_PATH = COLLAB_PATH + "%s/update";
    private final static String DELETE_COLLAB_PATH = COLLAB_PATH + "%s/delete";

    private final YfyClient<?>.YfyInternalClient client;

    public YfyCollabRequest(YfyClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Retrieve detailed collab information
     *
     * @return Detailed collab information
     * @throws YfyException
     */
    public YfyCollab getCollab(long collabId) throws YfyException {
        String[] params = { String.valueOf(collabId) };
        return getCollab(params);
    }

    private YfyCollab getCollab(String[] params) throws YfyException {
        return client.doGet(COLLAB_INFO_PATH,
                params,
                null,
                YfyCollab.class);
    }

    /**
     * Invite a user to a specific folder
     *
     * @param folderId Folder id in fangcloud
     * @param accessibleById AccessibleBy id that you want to invite
     * @param collabRole Collab role the user will grant
     * @param invitationMessage invitation message
     * @return Detailed collab information
     * @throws YfyException
     */
    public YfyCollab inviteCollab(long folderId, AccessibleByTypeEnum accessibleByType, long accessibleById,
                                  CollabRoleEnum collabRole, String invitationMessage) throws YfyException {
        return inviteCollab(new InviteCollabArg(folderId, accessibleByType.getType(), accessibleById, collabRole.getRole(), invitationMessage));
    }

    private YfyCollab inviteCollab(InviteCollabArg inviteCollabArg) throws YfyException {
        return client.doPost(INVITE_COLLAB_PATH,
                null,
                inviteCollabArg,
                YfyCollab.class);
    }

    /**
     * Update a collab role by collab id
     *
     * @param collabId Collab id
     * @param collabRole New collab role
     * @return Detailed collab information
     * @throws YfyException
     */
    public YfyCollab updateCollab(long collabId, CollabRoleEnum collabRole) throws YfyException {
        String[] params = { String.valueOf(collabId) };
        return updateCollab(params, new UpdateCollabArg(collabRole.getRole()));
    }

    private YfyCollab updateCollab(String[] params, UpdateCollabArg updateCollabArg) throws YfyException {
        return client.doPost(UPDATE_COLLAB_PATH,
                params,
                updateCollabArg,
                YfyCollab.class);
    }

    /**
     * Delete collab by collab id
     *
     * @param collabId Collab id
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult deleteCollab(long collabId) throws YfyException {
        String[] params = { String.valueOf(collabId) };
        return deleteCollab(params);
    }

    private SuccessResult deleteCollab(String[] params) throws YfyException {
        return client.doPost(DELETE_COLLAB_PATH,
                params,
                null,
                SuccessResult.class);
    }



}
