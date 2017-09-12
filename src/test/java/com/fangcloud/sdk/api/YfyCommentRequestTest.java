package com.fangcloud.sdk.api;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.comment.ListCommentResult;
import com.fangcloud.sdk.api.comment.YfyComment;
import com.fangcloud.sdk.api.comment.YfyCommentRequest;
import com.fangcloud.sdk.api.file.YfyFile;
import com.fangcloud.sdk.api.file.YfyFileRequest;
import com.fangcloud.sdk.api.folder.YfyFolder;
import com.fangcloud.sdk.api.folder.YfyFolderRequest;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.fangcloud.sdk.SdkTestUtil.assertCommentNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertFileNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertFolderNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class YfyCommentRequestTest {
    private static final String PARENT_NAME = "collab-api-test";
    private static final String FILE_NAME = "java-sdk-test.txt";

    private YfyFolderRequest folderRequest;
    private YfyFileRequest fileRequest;
    private YfyCommentRequest commentRequest;
    private long testParentId;
    private long testFileId;

    @Before
    public void before() throws YfyException {
        YfyAppInfo.initAppInfo("test-client", "123456");
        YfyClient client = new YfyClient(new YfyRequestConfig(), System.getenv().get("YFY_TOKEN"));
        folderRequest = client.folders();
        fileRequest = client.files();
        commentRequest = client.comments();
        testParentId = createAndAssertFolder(PARENT_NAME, 0L);
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
    public void testComment() throws YfyException {
        YfyComment comment = commentRequest.createComment(testFileId, "hello");
        assertCommentNotNull(comment);

        ListCommentResult listCommentResult = fileRequest.listComment(testFileId);
        assertNotNull(listCommentResult);
        assertNotNull(listCommentResult.getComments());
        assertEquals(1, listCommentResult.getComments().size());
        for (YfyComment yfyComment : listCommentResult.getComments()) {
            assertCommentNotNull(yfyComment);
        }

        assertTrue(commentRequest.deleteComment(comment.getCommentId()).getSuccess());
    }


}
