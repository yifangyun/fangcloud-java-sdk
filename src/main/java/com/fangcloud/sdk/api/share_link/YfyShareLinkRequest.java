package com.fangcloud.sdk.api.share_link;

import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.api.SuccessResult;
import com.fangcloud.sdk.exception.YfyException;

import java.util.HashMap;
import java.util.Map;

public class YfyShareLinkRequest {
    private final static String ITEM_API_PATH = YfySdkConstant.API_VERSION + "share_link/";
    private final static String GET_SHARE_LINK_PATH = ITEM_API_PATH + "%s/info";
    private final static String CREATE_SHARE_LINK_PATH = ITEM_API_PATH + "create";
    private final static String UPDATE_SHARE_LINK_PATH = ITEM_API_PATH + "%s/update";
    private final static String REVOKE_SHARE_LINK_PATH = ITEM_API_PATH + "%s/revoke";

    private final YfyClient<?>.YfyInternalClient client;

    public YfyShareLinkRequest(YfyClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Retrieve share link info by unique name
     *
     * @param uniqueName Unique name of share link
     * @return Detailed information of share link
     * @throws YfyException
     */
    public YfyShareLink getShareLink(String uniqueName) throws YfyException {
        String[] params = { uniqueName };
        return getShareLink(params, null);
    }

    /**
     * Retrieve share link info by unique name
     *
     * @param uniqueName Unique name of share link
     * @param password password of the share link
     * @return Detailed information of share link
     * @throws YfyException
     */
    public YfyShareLink getShareLink(String uniqueName, final String password) throws YfyException {
        String[] params = { uniqueName };
        Map<String, String> mapParams = new HashMap<String, String>() {{
           put(YfySdkConstant.PASSWORD, password);
        }};
        return getShareLink(params, mapParams);
    }

    private YfyShareLink getShareLink(String[] params, Map<String, String> mapParams) throws YfyException {
        return client.doGet(GET_SHARE_LINK_PATH,
                params,
                mapParams,
                YfyShareLink.class);
    }

    /**
     * Create file share link with all personal params. If password is null or empty,server will not set password
     *
     * @param fileId File id in fangcloud
     * @param access Share link access type
     * @param disableDownload Is need to disable others download
     * @param dueTime Due time of the share link
     * @param password Set password to share link(set null to disable password)
     * @return Detailed information of share link
     * @throws YfyException
     */
    public YfyShareLink createFileShareLink(long fileId, ShareLinkAccessEnum access, boolean disableDownload,
                                            String dueTime, String password) throws YfyException {
        boolean passwordProtected = false;
        if (password != null && !password.isEmpty()) {
            passwordProtected = true;
        }
        return createShareLink(new CreateShareLinkArg(null, fileId, access.getAccess(), disableDownload,
                dueTime, passwordProtected, password));
    }

    /**
     * Create folder share link with all personal params. If password is null or empty,server will not set password
     *
     * @param folderId Folder id in fangcloud
     * @param access Share link access type
     * @param disableDownload Is need to disable others download
     * @param dueTime Due time of the share link
     * @param password Set password to share link(set null to disable password)
     * @return Detailed information of share link
     * @throws YfyException
     */
    public YfyShareLink createFolderShareLink(long folderId, ShareLinkAccessEnum access, boolean disableDownload,
                                            String dueTime, String password) throws YfyException {
        boolean passwordProtected = false;
        if (password != null && !password.isEmpty()) {
            passwordProtected = true;
        }
        return createShareLink(new CreateShareLinkArg(folderId, null, access.getAccess(), disableDownload,
                dueTime, passwordProtected, password));
    }

    private YfyShareLink createShareLink(CreateShareLinkArg createShareLinkArg) throws YfyException {
        return client.doPost(CREATE_SHARE_LINK_PATH,
                null,
                createShareLinkArg,
                YfyShareLink.class);
    }

    /**
     * Update folder share link with all personal params. If password is null or empty,server will not set password
     *
     * @param uniqueName Unique name of share link
     * @param access Share link access type
     * @param disableDownload Is need to disable others download
     * @param dueTime Due time of the share link
     * @param password Set password to share link(set null to disable password)
     * @return Detailed information of share link
     * @throws YfyException
     */
    public YfyShareLink updateShareLink(String uniqueName, ShareLinkAccessEnum access, boolean disableDownload,
                                         String dueTime, String password) throws YfyException {
        String[] params = { uniqueName };
        boolean passwordProtected = false;
        if (password != null && !password.isEmpty()) {
            passwordProtected = true;
        }
        return updateShareLink(params, new UpdateShareLinkArg(access.getAccess(), disableDownload, dueTime, passwordProtected, password));
    }

    private YfyShareLink updateShareLink(String[] params, UpdateShareLinkArg updateShareLinkArg) throws YfyException {
        return client.doPost(UPDATE_SHARE_LINK_PATH,
                params,
                updateShareLinkArg,
                YfyShareLink.class);
    }

    /**
     * Revoke share link by unique name
     *
     * @param uniqueName Unique name of share link
     * @return An object only has one attribute named success
     * @throws YfyException
     */
    public SuccessResult revokeShareLink(String uniqueName) throws YfyException {
        String[] params = { uniqueName };
        return revokeShareLink(params);
    }

    private SuccessResult revokeShareLink(String[] params) throws YfyException {
        return client.doPost(REVOKE_SHARE_LINK_PATH,
                params,
                null,
                SuccessResult.class);
    }

}
