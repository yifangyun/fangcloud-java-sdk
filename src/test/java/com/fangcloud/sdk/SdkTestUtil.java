package com.fangcloud.sdk;

import com.fangcloud.sdk.api.PagingResult;
import com.fangcloud.sdk.api.YfyItem;
import com.fangcloud.sdk.api.YfyMiniDepartment;
import com.fangcloud.sdk.api.YfyMiniElement;
import com.fangcloud.sdk.api.YfyMiniUser;
import com.fangcloud.sdk.api.YfyPathFolder;
import com.fangcloud.sdk.api.collab.YfyCollab;
import com.fangcloud.sdk.api.comment.YfyComment;
import com.fangcloud.sdk.api.department.YfyDepartment;
import com.fangcloud.sdk.api.file.YfyFile;
import com.fangcloud.sdk.api.folder.YfyFolder;
import com.fangcloud.sdk.api.share_link.YfyShareLink;
import com.fangcloud.sdk.api.user.YfyUser;

import java.io.File;

import static org.junit.Assert.assertNotNull;

public class SdkTestUtil {
    public static void assertFileNotNull(YfyFile file) {
        assertNotNull(file);
        assertItemNotNull(file);
        assertNotNull(file.getSha1());
        assertNotNull(file.getCommentsCount());
    }

    public static void assertFolderNotNull(YfyFolder folder) {
        assertNotNull(folder);
        assertItemNotNull(folder);
        assertNotNull(folder.getItemCount());
        assertNotNull(folder.getFolderType());
    }

    public static void assertItemNotNull(YfyItem item) {
        assertNotNull(item);
        assertNotNull(item.getId());
        assertNotNull(item.getType());
        assertNotNull(item.getName());
        assertNotNull(item.getSize());
        assertNotNull(item.getCreatedAt());
        assertNotNull(item.getModifiedAt());
        assertNotNull(item.getDescription());
        assertNotNull(item.getPath());
        for (YfyPathFolder pathFolder : item.getPath()) {
            assertPathFolderNotNull(pathFolder);
        }
        assertMiniUserNotNull(item.getOwnedBy());
        assertPathFolderNotNull(item.getParent());
        assertNotNull(item.getSequenceId());
    }

    public static void assertPathFolderNotNull(YfyPathFolder pathFolder) {
        assertNotNull(pathFolder);
        assertNotNull(pathFolder.getId());
        assertNotNull(pathFolder.getName());
        assertNotNull(pathFolder.getType());
    }

    public static void assertMiniUserNotNull(YfyMiniUser miniUser) {
        assertNotNull(miniUser);
        assertNotNull(miniUser.getId());
        assertNotNull(miniUser.getName());
        assertNotNull(miniUser.getLogin());
        assertNotNull(miniUser.getEnterpriseId());
    }

    public static void assertMiniElementNotNull(YfyMiniElement miniElement) {
        assertNotNull(miniElement);
        assertNotNull(miniElement.getId());
        assertNotNull(miniElement.getName());
        assertNotNull(miniElement.getType());
    }

    public static void assertPagingResultNotNull(PagingResult pagingResult) {
        assertNotNull(pagingResult);
        assertNotNull(pagingResult.getTotalCount());
        assertNotNull(pagingResult.getPageCapacity());
        assertNotNull(pagingResult.getPageId());
        assertNotNull(pagingResult.getPageCount());
    }

    public static void assertUserNotNull(YfyUser user) {
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getEnterprise());
        assertNotNull(user.getEnterprise().getId());
        assertNotNull(user.getEnterprise().getName());
        assertNotNull(user.getEnterprise().getAdminUserId());
        assertNotNull(user.getName());
        assertNotNull(user.getPhone());
        assertNotNull(user.getEmail());
        assertNotNull(user.getProfilePicKey());
        assertNotNull(user.getActive());
        assertNotNull(user.getFullNamePinyin());
        assertNotNull(user.getPinyinFirstLetters());
    }

    public static void assertShareLinkNotNull(YfyShareLink shareLink) {
        assertNotNull(shareLink);
        assertNotNull(shareLink.getAccess());
        assertNotNull(shareLink.getDisableDownload());
        assertNotNull(shareLink.getDueTime());
        assertNotNull(shareLink.getPasswordProtected());
        assertNotNull(shareLink.getShareLink());
        assertNotNull(shareLink.getUniqueName());
        assertNotNull(shareLink.getCreatedAt());
        assertNotNull(shareLink.getModifiedAt());
        assertNotNull(shareLink.getDownloadCountTotal());
        assertNotNull(shareLink.getViewCount());
    }

    public static void assertCollabNotNull(YfyCollab collab) {
        assertNotNull(collab);
        assertNotNull(collab.getAccepted());
        assertNotNull(collab.getRole());
        if (!collab.getRole().equals("owner")) {
            assertNotNull(collab.getId());
        }
        assertMiniElementNotNull(collab.getAccessibleBy());
    }

    public static void assertCommentNotNull(YfyComment comment) {
        assertNotNull(comment);
        assertNotNull(comment.getCommentId());
        assertNotNull(comment.getContent());
        assertNotNull(comment.getCreatedAt());
        assertNotNull(comment.getFileId());
        assertMiniUserNotNull(comment.getUser());
    }

    public static void assertDepartmentNotNull(YfyDepartment department) {
        assertNotNull(department);
        assertNotNull(department.getId());
        assertNotNull(department.getName());
        assertNotNull(department.getParentId());
        assertNotNull(department.getUserCount());
    }

    public static void assertMiniDepartmentNotNull(YfyMiniDepartment department) {
        assertNotNull(department);
        assertNotNull(department.getId());
        assertNotNull(department.getName());
        assertNotNull(department.getChildrenDepartmentsCount());
        assertNotNull(department.getUserCount());
        assertNotNull(department.getDirectItemCount());
    }

    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
        } else {
            throw new RuntimeException(fileName + " is not exist");
        }
    }


}
