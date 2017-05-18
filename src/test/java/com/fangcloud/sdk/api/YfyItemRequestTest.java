package com.fangcloud.sdk.api;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.file.YfyFile;
import com.fangcloud.sdk.api.folder.YfyFolder;
import com.fangcloud.sdk.api.item.SearchItemResult;
import com.fangcloud.sdk.api.item.YfyItemRequest;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.Before;
import org.junit.Test;

import static com.fangcloud.sdk.SdkTestUtil.assertFileNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertFolderNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertPagingResultNotNull;
import static org.junit.Assert.assertNotNull;

public class YfyItemRequestTest {
    private YfyItemRequest itemRequest;

    @Before
    public void before() throws YfyException {
        YfyAppInfo.initAppInfo("java-auto-test", "java-auto-test");
        YfyClient client = new YfyClient(new YfyRequestConfig(), System.getenv().get("YFY_TOKEN"));
        itemRequest = client.items();
    }

    @Test
    public void testSearchItem() throws YfyException {
        SearchItemResult searchItemResult = itemRequest.searchItem("test", ItemTypeEnum.ITEM, 0, 0);
        assertPagingResultNotNull(searchItemResult);
        assertNotNull(searchItemResult);
        assertNotNull(searchItemResult.getFolders());
        for (YfyFolder folder : searchItemResult.getFolders()) {
            assertFolderNotNull(folder);
        }
        assertNotNull(searchItemResult.getFiles());
        for (YfyFile file : searchItemResult.getFiles()) {
            assertFileNotNull(file);
        }
    }
}
