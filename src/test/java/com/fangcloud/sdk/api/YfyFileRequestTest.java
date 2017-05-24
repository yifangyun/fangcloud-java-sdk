package com.fangcloud.sdk.api;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.file.DownloadPreviewResult;
import com.fangcloud.sdk.api.file.PreviewKindEnum;
import com.fangcloud.sdk.api.file.PreviewResult;
import com.fangcloud.sdk.api.file.YfyFile;
import com.fangcloud.sdk.api.file.YfyFileRequest;
import com.fangcloud.sdk.api.folder.YfyFolder;
import com.fangcloud.sdk.api.folder.YfyFolderRequest;
import com.fangcloud.sdk.exception.OtherErrorException;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.fangcloud.sdk.SdkTestUtil.assertFileNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertFolderNotNull;
import static com.fangcloud.sdk.SdkTestUtil.deleteFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class YfyFileRequestTest {
    private static final String FILE_NAME = "java-sdk-test.txt";
    private static final String FOLDER_NAME = "file-api-test";

    private YfyFileRequest fileRequest;
    private YfyFolderRequest folderRequest;
    private long testParentId;
    private long testFileId;

    @Before
    public void before() throws YfyException {
        YfyAppInfo.initAppInfo("java-auto-test", "java-auto-test");
        YfyClient client = new YfyClient(new YfyRequestConfig(), System.getenv().get("YFY_TOKEN"));
        fileRequest = client.files();
        folderRequest = client.folders();
        testParentId = createAndAssertFolder(FOLDER_NAME, 0L);
        testFileId = uploadAndAssertFile(FILE_NAME, testParentId);
    }

    private long uploadAndAssertFile(String fileName, long parentId) throws YfyException {
        YfyFile file = fileRequest.directUploadFile(parentId, fileName,
                YfyFileRequestTest.class.getResourceAsStream("/" + fileName));
        assertFileNotNull(file);
        assertEquals(fileName, file.getName());
        return file.getId();
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
    public void testGetFile() throws YfyException {
        assertFileNotNull(fileRequest.getFile(testFileId));
    }

    @Test
    public void testUpdateFile() throws YfyException {
        String fileNewName = "java-sdk-test-update";
        YfyFile file = fileRequest.updateFile(testFileId, fileNewName, null);
        assertFileNotNull(file);
        assertEquals(fileNewName + ".txt", file.getName());
    }

    @Test
    public void testDeleteAndRestoreFile() throws YfyException {
        assertTrue(fileRequest.deleteFile(testFileId).getSuccess());
        assertTrue(fileRequest.restoreFileFromTrash(testFileId).getSuccess());
    }

    // @Test
    // public void testDeleteAndRestoreFileBatch() throws YfyException {
    //     List<Long> fileIds = new ArrayList<Long>() {{
    //         add(testFileId);
    //     }};
    //     assertTrue(fileRequest.deleteFile(fileIds).getSuccess());
    //     assertTrue(fileRequest.restoreFileFromTrash(fileIds).getSuccess());
    //     fileRequest.deleteAllFileInTrash();
    //     assertTrue(fileRequest.deleteFile(fileIds).getSuccess());
    //     assertTrue(fileRequest.restoreAllFileInTrash().getSuccess());
    // }

    @Test
    public void testDeleteFileFromTrash() throws YfyException {
        assertTrue(fileRequest.deleteFile(testFileId).getSuccess());
        assertTrue(fileRequest.deleteFileFromTrash(testFileId).getSuccess());
        testFileId = uploadAndAssertFile(FILE_NAME, testParentId);
        assertTrue(fileRequest.deleteFile(testFileId).getSuccess());
        // assertTrue(fileRequest.deleteFileFromTrash(new ArrayList<Long>() {{
        //     add(testFileId);
        // }}).getSuccess());
        assertTrue(fileRequest.deleteFileFromTrash(testFileId).getSuccess());
        testFileId = uploadAndAssertFile(FILE_NAME, testParentId);
        assertTrue(fileRequest.deleteFile(testFileId).getSuccess());
        // assertTrue(fileRequest.deleteAllFileInTrash().getSuccess());
        assertTrue(fileRequest.deleteFileFromTrash(testFileId).getSuccess());
        testFileId = uploadAndAssertFile(FILE_NAME, testParentId);
    }

    @Test
    public void testMoveFile() throws YfyException {
        assertTrue(fileRequest.moveFile(testFileId, 0L).getSuccess());
        // assertTrue(fileRequest.moveFile(new ArrayList<Long>() {{
        //     add(testFileId);
        // }}, testParentId).getSuccess());
        assertTrue(fileRequest.moveFile(testFileId, testParentId).getSuccess());
    }

    @Test
    public void testDirectDownloadFile() throws YfyException {
        fileRequest.directDownloadFile(testFileId, FILE_NAME);
        deleteFile(FILE_NAME);
    }

    @Test
    public void testDirectUploadNewVersionFile() throws YfyException {
        assertFileNotNull(fileRequest.directUploadNewVersionFile(testFileId, FILE_NAME, null,
                YfyFileRequestTest.class.getResourceAsStream("/" + FILE_NAME)));
    }

    @Test
    public void testPreview() throws YfyException {
        PreviewResult previewResult = fileRequest.preview(testFileId, PreviewKindEnum.IMAGE_THUMBNAIL, false);
        assertNotNull(previewResult);
        assertNotNull(previewResult.getCategory());
        assertNotNull(previewResult.getExifRotation());
        assertNotNull(previewResult.getFormat());
        assertNotNull(previewResult.getHas2048());
        assertNotNull(previewResult.getPageCount());
        assertNotNull(previewResult.getStatus());
    }

    @Test
    public void testDownloadPreview() throws YfyException {
        DownloadPreviewResult downloadPreviewResult = fileRequest.downloadPreview(testFileId, 1, PreviewKindEnum.IMAGE_THUMBNAIL);
        assertNotNull(downloadPreviewResult);
        assertNotNull(downloadPreviewResult.getStatus());
    }

    @Test
    public void testCopyFile() throws YfyException {
        YfyFile file = fileRequest.copyFile(testFileId, 0L);
        assertFileNotNull(file);
        assertTrue(fileRequest.deleteFile(file.getId()).getSuccess());
        assertTrue(fileRequest.deleteFileFromTrash(file.getId()).getSuccess());
    }

    @Test(expected = OtherErrorException.class)
    public void testUploadUrlExpired() throws YfyException {
        String fileName = "2" + FILE_NAME;
        String uploadUrl = fileRequest.preSignatureUpload(testParentId, fileName);
        fileRequest.uploadFile(uploadUrl, YfyFileRequestTest.class.getResourceAsStream("/" + FILE_NAME));
        fileRequest.uploadFile(uploadUrl, YfyFileRequestTest.class.getResourceAsStream("/" + FILE_NAME));
    }

    @Test(expected = OtherErrorException.class)
    public void testDownloadUrlExpired() throws YfyException {
        String downloadUrl = "https://download01.fangcloud.net/download/5f3aae24806b489492200040d4126b92/fdb1c1744480d41e05afa021a42037675108eee0056dd97ff92ada9b70ff43fd/IMG_0039.JPG";
        fileRequest.downloadFile(downloadUrl, FILE_NAME);
    }
}