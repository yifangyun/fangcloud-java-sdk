package com.fangcloud.sdk.api;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.file.YfyFile;
import com.fangcloud.sdk.api.file.YfyFileRequest;
import com.fangcloud.sdk.api.folder.YfyFolder;
import com.fangcloud.sdk.api.folder.YfyFolderRequest;
import com.fangcloud.sdk.api.trash.YfyTrashRequest;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.fangcloud.sdk.SdkTestUtil.assertFileNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertFolderNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YfyTrashRequestTest {
    private static final String PARENT_NAME = "share-link-api-test";
    private static final String FILE_NAME = "java-sdk-test.txt";
    private static final String FOLDER_NAME = "java-sdk-test";

    private YfyFileRequest fileRequest;
    private YfyFolderRequest folderRequest;
    private YfyTrashRequest trashRequest;
    private long testParentId;
    private long testFolderId;
    private long testFileId;

    @Before
    public void before() throws YfyException {
        YfyAppInfo.initAppInfo("test-client", "123456");
        YfyClient client = new YfyClient(new YfyRequestConfig(), System.getenv().get("YFY_TOKEN"));
        fileRequest = client.files();
        folderRequest = client.folders();
        trashRequest = client.trashs();
        testParentId = createAndAssertFolder(PARENT_NAME, 0L);
        testFolderId = createAndAssertFolder(FOLDER_NAME, testParentId);
        testFileId = uploadAndAssertFile(FILE_NAME, testParentId);
    }

    private long createAndAssertFolder(String name, long parentId) throws YfyException {
        YfyFolder folder = folderRequest.createFolder(name, parentId);
        assertFolderNotNull(folder);
        return folder.getId();
    }

    private long uploadAndAssertFile(String fileName, long parentId) throws YfyException {
        YfyFile file = fileRequest.directUploadFile(parentId, fileName,
                YfyFileRequestTest.class.getResourceAsStream("/" + fileName));
        assertFileNotNull(file);
        assertEquals(fileName, file.getName());
        return file.getId();
    }

    @After
    public void after() throws YfyException {
        assertTrue(folderRequest.deleteFolder(testParentId).getSuccess());
        assertTrue(folderRequest.deleteFolderFromTrash(testParentId).getSuccess());
    }

    @Test
    public void testRestoreAndClearTrash() throws YfyException {
        assertTrue(trashRequest.clearTrash(ItemTypeEnum.ITEM).getSuccess());
        assertTrue(fileRequest.deleteFile(testFileId).getSuccess());
        assertTrue(folderRequest.deleteFolder(testFolderId).getSuccess());
        assertTrue(trashRequest.restoreTrash(ItemTypeEnum.ITEM).getSuccess());
        assertFileNotNull(fileRequest.getFile(testFileId));
        assertFolderNotNull(folderRequest.getFolder(testFolderId));
    }
}
