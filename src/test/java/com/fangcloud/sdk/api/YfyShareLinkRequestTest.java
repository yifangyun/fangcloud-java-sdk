package com.fangcloud.sdk.api;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyHost;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.file.YfyFile;
import com.fangcloud.sdk.api.file.YfyFileRequest;
import com.fangcloud.sdk.api.folder.YfyFolder;
import com.fangcloud.sdk.api.folder.YfyFolderRequest;
import com.fangcloud.sdk.api.share_link.ListShareLinkResult;
import com.fangcloud.sdk.api.share_link.ShareLinkAccessEnum;
import com.fangcloud.sdk.api.share_link.YfyShareLink;
import com.fangcloud.sdk.api.share_link.YfyShareLinkRequest;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.fangcloud.sdk.SdkTestUtil.assertFileNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertFolderNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertShareLinkNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class YfyShareLinkRequestTest {
    private static final String PARENT_NAME = "share-link-api-test";
    private static final String FILE_NAME = "java-sdk-test.txt";
    private static final String FOLDER_NAME = "java-sdk-test";
    private static final YfyHost testHost = new YfyHost("platform.fangcloud.net", "oauth-server.fangcloud.net");

    private YfyFileRequest fileRequest;
    private YfyFolderRequest folderRequest;
    private YfyShareLinkRequest shareLinkRequest;
    private long testParentId;
    private long testFolderId;
    private long testFileId;

    @Before
    public void before() throws YfyException {
        YfyAppInfo.initAppInfo("java-auto-test", "java-auto-test", testHost);
        YfyClient client = new YfyClient(new YfyRequestConfig(), System.getenv().get("YFY_TOKEN"));
        folderRequest = client.folders();
        fileRequest = client.files();
        shareLinkRequest = client.shareLinks();
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
    public void testFolderShareLink() throws YfyException {
        // test folder
        YfyShareLink publicShareLink = shareLinkRequest.createFolderShareLink(testFolderId,
                ShareLinkAccessEnum.PUBLIC, false, "2017-05-11", null);
        assertShareLinkNotNull(publicShareLink);
        YfyShareLink companyShareLink = shareLinkRequest.updateShareLink(publicShareLink.getUniqueName(),
                ShareLinkAccessEnum.COMPANY, false, "2017-05-11", null);
        assertShareLinkNotNull(companyShareLink);
        YfyShareLink disableDownloadShareLink = shareLinkRequest.createFolderShareLink(testFolderId,
                ShareLinkAccessEnum.PUBLIC, true, "2017-05-11", null);
        assertShareLinkNotNull(disableDownloadShareLink);
        YfyShareLink passwordShareLink = shareLinkRequest.updateShareLink(disableDownloadShareLink.getUniqueName(),
                ShareLinkAccessEnum.PUBLIC, false, "2017-05-11", "wenyichao");
        assertShareLinkNotNull(passwordShareLink);
        assertTrue(passwordShareLink.getPasswordProtected());

        YfyShareLink companyShareLinkInfo = shareLinkRequest.getShareLink(companyShareLink.getUniqueName());
        assertShareLinkNotNull(companyShareLinkInfo);
        YfyShareLink passwordShareLinkInfo = shareLinkRequest.getShareLink(passwordShareLink.getUniqueName(), "wenyichao");
        assertShareLinkNotNull(passwordShareLinkInfo);

        ListShareLinkResult listShareLinkResult = folderRequest.listShareLink(testFolderId, 0);
        assertNotNull(listShareLinkResult);
        assertNotNull(listShareLinkResult.getShareLinks());
        assertEquals(listShareLinkResult.getShareLinks().size(), 2);
        for (YfyShareLink shareLink : listShareLinkResult.getShareLinks()) {
            assertShareLinkNotNull(shareLink);
        }

        assertTrue(shareLinkRequest.revokeShareLink(companyShareLinkInfo.getUniqueName()).getSuccess());
        assertTrue(shareLinkRequest.revokeShareLink(passwordShareLinkInfo.getUniqueName()).getSuccess());

    }

    @Test
    public void testFileShareLink() throws YfyException {
        // test folder
        YfyShareLink publicShareLink = shareLinkRequest.createFileShareLink(testFileId,
                ShareLinkAccessEnum.PUBLIC, false, "2017-05-11", null);
        assertShareLinkNotNull(publicShareLink);
        YfyShareLink companyShareLink = shareLinkRequest.updateShareLink(publicShareLink.getUniqueName(),
                ShareLinkAccessEnum.COMPANY, false, "2017-05-11", null);
        assertShareLinkNotNull(companyShareLink);
        YfyShareLink disableDownloadShareLink = shareLinkRequest.createFileShareLink(testFileId,
                ShareLinkAccessEnum.PUBLIC, true, "2017-05-11", null);
        assertShareLinkNotNull(disableDownloadShareLink);
        YfyShareLink passwordShareLink = shareLinkRequest.updateShareLink(disableDownloadShareLink.getUniqueName(),
                ShareLinkAccessEnum.PUBLIC, false, "2017-05-11", "wenyichao");
        assertShareLinkNotNull(passwordShareLink);
        assertTrue(passwordShareLink.getPasswordProtected());

        YfyShareLink companyShareLinkInfo = shareLinkRequest.getShareLink(companyShareLink.getUniqueName());
        assertShareLinkNotNull(companyShareLinkInfo);
        YfyShareLink passwordShareLinkInfo = shareLinkRequest.getShareLink(passwordShareLink.getUniqueName(), "wenyichao");
        assertShareLinkNotNull(passwordShareLinkInfo);

        ListShareLinkResult listShareLinkResult = folderRequest.listShareLink(testFolderId, 0);
        assertNotNull(listShareLinkResult);
        assertNotNull(listShareLinkResult.getShareLinks());
        assertEquals(listShareLinkResult.getShareLinks().size(), 2);
        for (YfyShareLink shareLink : listShareLinkResult.getShareLinks()) {
            assertShareLinkNotNull(shareLink);
        }

        assertTrue(shareLinkRequest.revokeShareLink(companyShareLinkInfo.getUniqueName()).getSuccess());
        assertTrue(shareLinkRequest.revokeShareLink(passwordShareLinkInfo.getUniqueName()).getSuccess());

    }
}
