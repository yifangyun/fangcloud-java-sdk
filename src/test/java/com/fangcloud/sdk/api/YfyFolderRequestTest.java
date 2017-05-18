package com.fangcloud.sdk.api;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.file.YfyFile;
import com.fangcloud.sdk.api.folder.GetChildrenResult;
import com.fangcloud.sdk.api.folder.YfyFolder;
import com.fangcloud.sdk.api.folder.YfyFolderRequest;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.fangcloud.sdk.SdkTestUtil.assertFileNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertFolderNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertPagingResultNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class YfyFolderRequestTest {
    private static final String PARENT_NAME = "folder-api-test";
    private static final String FOLDER_NAME = "java-sdk-test";

    private YfyFolderRequest folderRequest;
    private long testParentId;
    private long testFolderId;

    @Before
    public void before() throws YfyException {
        YfyAppInfo.initAppInfo("java-auto-test", "java-auto-test");
        YfyClient client = new YfyClient(new YfyRequestConfig(), System.getenv().get("YFY_TOKEN"));
        folderRequest = client.folders();
        testParentId = createAndAssertFolder(PARENT_NAME, 0L);
        testFolderId = createAndAssertFolder(FOLDER_NAME, testParentId);
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
    public void testGetFolder() throws YfyException {
        assertFolderNotNull(folderRequest.getFolder(testFolderId));
    }
    
    @Test
    public void testUpdateFolder() throws YfyException {
        String folderName = FOLDER_NAME + "-update";
        YfyFolder folder = folderRequest.updateFolder(testFolderId, folderName);
        assertFolderNotNull(folder);
        assertEquals(folder.getName(), folderName);
    }

    @Test
    public void testDeleteAndRestoreFolder() throws YfyException {
        assertTrue(folderRequest.deleteFolder(testFolderId).getSuccess());
        assertTrue(folderRequest.restoreFolderFromTrash(testFolderId).getSuccess());
    }

    // @Test
    // public void testDeleteAndRestoreFolderBatch() throws YfyException {
    //     List<Long> folderIds = new ArrayList<Long>() {{
    //         add(testFolderId);
    //     }};
    //     assertTrue(folderRequest.deleteFolder(folderIds).getSuccess());
    //     assertTrue(folderRequest.restoreFolderFromTrash(folderIds).getSuccess());
    //     folderRequest.deleteAllFolderInTrash();
    //     assertTrue(folderRequest.deleteFolder(folderIds).getSuccess());
    //     assertTrue(folderRequest.restoreAllFolderInTrash().getSuccess());
    // }

    @Test
    public void testDeleteFolderFromTrash() throws YfyException {
        assertTrue(folderRequest.deleteFolder(testFolderId).getSuccess());
        assertTrue(folderRequest.deleteFolderFromTrash(testFolderId).getSuccess());
        testFolderId = createAndAssertFolder(FOLDER_NAME, testParentId);
        assertTrue(folderRequest.deleteFolder(testFolderId).getSuccess());
        // assertTrue(folderRequest.deleteFolderFromTrash(new ArrayList<Long>() {{
        //     add(testFolderId);
        // }}).getSuccess());
        assertTrue(folderRequest.deleteFolderFromTrash(testFolderId).getSuccess());
        testFolderId = createAndAssertFolder(FOLDER_NAME, testParentId);
        assertTrue(folderRequest.deleteFolder(testFolderId).getSuccess());
        // assertTrue(folderRequest.deleteAllFolderInTrash().getSuccess());
        assertTrue(folderRequest.deleteFolderFromTrash(testFolderId).getSuccess());
        testFolderId = createAndAssertFolder(FOLDER_NAME, testParentId);
    }

    @Test
    public void testMoveFolder() throws YfyException {
        assertTrue(folderRequest.moveFolder(testFolderId, 0L).getSuccess());
        assertTrue(folderRequest.moveFolder(testFolderId, testParentId).getSuccess());
        // assertTrue(folderRequest.moveFolder(new ArrayList<Long>() {{
        //     add(testFolderId);
        // }}, testParentId).getSuccess());
    }

    @Test
    public void testGetChildren() throws YfyException {
        GetChildrenResult getChildrenResult = folderRequest.getChildren(testParentId, 0, 2, ItemTypeEnum.ITEM);
        assertPagingResultNotNull(getChildrenResult);
        assertNotNull(getChildrenResult.getFolders());
        for (YfyFolder folder : getChildrenResult.getFolders()) {
            assertFolderNotNull(folder);
        }
        assertNotNull(getChildrenResult.getFiles());
        for (YfyFile file : getChildrenResult.getFiles()) {
            assertFileNotNull(file);
        }
    }
}
