package com.fangcloud.sdk.api.user;

import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyRequestUtil;
import com.fangcloud.sdk.YfySdkConstant;
import com.fangcloud.sdk.exception.NetworkIOException;
import com.fangcloud.sdk.exception.YfyException;
import com.fangcloud.sdk.util.IOUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class YfyUserRequest {
    private final static String USER_PATH = YfySdkConstant.API_VERSION + "user/";
    private final static String SELF_INFO_PATH = USER_PATH + "info";
    private final static String USER_INFO_PATH = USER_PATH + "%s/info";
    private final static String PROFILE_PIC_DOWNLOAD_PATH = USER_PATH + "%s/profile_pic_download";
    private final static String UPDATE_USER_PATH = USER_PATH + "update";
    private final static String SEARCH_USER_PATH = USER_PATH + "search";

    private final YfyClient<?>.YfyInternalClient client;

    public YfyUserRequest(YfyClient.YfyInternalClient client) {
        this.client = client;
    }

    /**
     * Retrieve detailed self user information
     *
     * @return Detailed self user information
     * @throws YfyException
     */
    public YfyUser getSelf() throws YfyException {
        return client.doGet(SELF_INFO_PATH,
                null,
                null,
                YfyUser.class);
    }

    /**
     * Retrieve detailed user information
     *
     * @param userId User id in fangcloud
     * @return Detailed user information
     * @throws YfyException
     */
    public YfyUser getUser(long userId) throws YfyException {
        String[] param = { String.valueOf(userId) };
        return getUser(param);
    }

    private YfyUser getUser(String[] param) throws YfyException {
        return client.doGet(USER_INFO_PATH,
                param,
                null,
                YfyUser.class);
    }

    /**
     * Download the specific user profile picture
     *
     * @param userId User id in fangcloud
     * @param profilePicKey Profile picture key return by {@link this#getUser(long)}
     * @param savePath Where you'd like to save the file
     * @throws YfyException
     */
    public void downloadProfilePic(final long userId, final String profilePicKey, String savePath) throws YfyException {
        Object[] params = { String.valueOf(userId) };
        Map<String, String> mapParams = new HashMap<String, String>() {{
            put(YfySdkConstant.PROFILE_PIC_KEY, profilePicKey);
        }};
        String downloadUrl = YfyRequestUtil.buildUrlWithParams(
                client.getHost().getApi(), String.format(PROFILE_PIC_DOWNLOAD_PATH, params), mapParams);

        InputStream body = client.doDownload(downloadUrl, true);
        File file = new File(savePath);
        try {
            IOUtil.copyStreamToFile(body, file);
        } catch (IOException ex) {
            throw new NetworkIOException(ex);
        } finally {
            IOUtil.closeQuietly(body);
        }
    }

    /**
     * Update self user information
     *
     * @param name New name
     * @return Detailed user information
     * @throws YfyException
     */
    public YfyUser updateSelf(String name) throws YfyException {
        return updateSelf(new UpdateSelfArg(name));
    }

    private YfyUser updateSelf(UpdateSelfArg updateSelfArg) throws YfyException {
        return client.doPost(UPDATE_USER_PATH,
                null,
                updateSelfArg,
                YfyUser.class);
    }

    /**
     * Search user in the same enterprise with query key word
     *
     * @param queryWords Query words about user info
     * @param pageId Page id begin with 0
     * @return Detailed user information
     * @throws YfyException
     */
    public SearchUserResult searchUser(final String queryWords, final int pageId) throws YfyException {
        Map<String, String> params = new HashMap<String, String>() {{
            put(YfySdkConstant.QUERY_WORDS, queryWords);
            put(YfySdkConstant.PAGE_ID, String.valueOf(pageId));
        }};
        return searchUser(params);
    }

    private SearchUserResult searchUser(Map<String, String> params) throws YfyException {
        return client.doGet(SEARCH_USER_PATH,
                null,
                params,
                SearchUserResult.class);
    }

}
