package com.fangcloud.sdk.api;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyHost;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.collab.ListCollabResult;
import com.fangcloud.sdk.api.collab.YfyCollab;
import com.fangcloud.sdk.api.collab.YfyCollabRequest;
import com.fangcloud.sdk.api.folder.YfyFolder;
import com.fangcloud.sdk.api.folder.YfyFolderRequest;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.fangcloud.sdk.SdkTestUtil.assertCollabNull;
import static com.fangcloud.sdk.SdkTestUtil.assertFolderNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class YfyCollabRequestTest {
    private static final String PARENT_NAME = "collab-api-test";
    private static final String FOLDER_NAME = "java-sdk-test";
    private static final YfyHost testHost = new YfyHost("platform.fangcloud.net", "oauth-server.fangcloud.net");

    private YfyFolderRequest folderRequest;
    private YfyCollabRequest collabRequest;
    private long testParentId;
    private long testFolderId;
    private long collabUserId;

    @Before
    public void before() throws YfyException {
        YfyAppInfo.initAppInfo("java-auto-test", "java-auto-test", testHost);
        YfyClient client = new YfyClient(new YfyRequestConfig(), System.getenv().get("YFY_TOKEN"));
        folderRequest = client.folders();
        collabRequest = client.collabs();
        testParentId = createAndAssertFolder(PARENT_NAME, 0L);
        testFolderId = createAndAssertFolder(FOLDER_NAME, testParentId);
        collabUserId = 1L;
    }

    private long createAndAssertFolder(String name, long parentId) throws YfyException {
        YfyFolder folder = folderRequest.createFolder(name, parentId);
        assertFolderNotNull(folder);
        return folder.getId();
    }

    @After
    public void after() throws YfyException {
        assertTrue(folderRequest.deleteFolder(testParentId).getSuccess());
        assertTrue(folderRequest.deleteFolderFromTrash(testParentId).getSuccess());
    }

    @Test
    public void testCollab() throws YfyException {
        YfyCollab collab = collabRequest.inviteCollab(testFolderId, collabUserId, CollabRoleEnum.COOWNER, "hello");
        assertCollabNull(collab);
        assertEquals(CollabRoleEnum.COOWNER.getRole(), collab.getRole());

        collab = collabRequest.updateCollab(collab.getCollabId(), CollabRoleEnum.EDITOR);
        assertCollabNull(collab);
        assertEquals(CollabRoleEnum.EDITOR.getRole(), collab.getRole());

        collab = collabRequest.getCollab(collab.getCollabId());
        assertCollabNull(collab);

        ListCollabResult listCollabResult = folderRequest.listCollab(testFolderId);
        assertNotNull(listCollabResult);
        assertNotNull(listCollabResult.getFinalRole());
        assertNotNull(listCollabResult.getCollabInfo());
        assertEquals(2, listCollabResult.getCollabInfo().size());
        for (YfyCollab yfyCollab : listCollabResult.getCollabInfo()) {
            assertCollabNull(yfyCollab);
        }

        assertTrue(collabRequest.deleteCollab(collab.getCollabId()).getSuccess());
    }


}
