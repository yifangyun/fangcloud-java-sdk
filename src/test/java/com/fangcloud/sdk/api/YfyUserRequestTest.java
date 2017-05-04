package com.fangcloud.sdk.api;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyHost;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.user.SearchUserResult;
import com.fangcloud.sdk.api.user.YfyUser;
import com.fangcloud.sdk.api.user.YfyUserRequest;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.Before;
import org.junit.Test;

import static com.fangcloud.sdk.SdkTestUtil.assertMiniUserNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertUserNotNull;
import static com.fangcloud.sdk.SdkTestUtil.deleteFile;
import static org.junit.Assert.assertEquals;

public class YfyUserRequestTest {
    private static final YfyHost testHost = new YfyHost("platform.fangcloud.net", "oauth-server.fangcloud.net");
    private static final long USER_ID = 468L;
    private static final String PROFILE_PIC_NAME = "user_profile_pic_" + USER_ID + ".jpeg";

    private YfyUserRequest userRequest;

    @Before
    public void before() throws YfyException {
        YfyAppInfo.initAppInfo("java-auto-test", "java-auto-test", testHost);
        YfyClient client = new YfyClient(new YfyRequestConfig(), System.getenv().get("YFY_TOKEN"));
        userRequest = client.users();
    }

    @Test
    public void testGetSelf() throws YfyException {
        assertUserNotNull(userRequest.getSelf());
    }

    @Test
    public void testGetUserAndDownloadPic() throws YfyException {
        YfyUser user = userRequest.getUser(USER_ID);
        assertUserNotNull(user);
        userRequest.downloadProfilePic(USER_ID, user.getProfilePicKey(), PROFILE_PIC_NAME);
        deleteFile(PROFILE_PIC_NAME);
    }

    @Test
    public void testUpdateSelf() throws YfyException {
        YfyUser originUser = userRequest.getSelf();
        assertUserNotNull(originUser);
        YfyUser user = userRequest.updateSelf(originUser.getName() + "1");
        assertUserNotNull(user);
        assertEquals(originUser.getName() + "1", user.getName());
        userRequest.updateSelf(originUser.getName());
    }

    @Test
    public void testSearchUser() throws YfyException {
        SearchUserResult searchUserResult = userRequest.searchUser("tt", 0);
        for (YfyMiniUser yfyMiniUser : searchUserResult.getUsers()) {
            assertMiniUserNotNull(yfyMiniUser);
        }
    }
}
