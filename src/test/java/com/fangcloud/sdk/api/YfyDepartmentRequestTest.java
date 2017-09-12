package com.fangcloud.sdk.api;

import com.fangcloud.sdk.YfyAppInfo;
import com.fangcloud.sdk.YfyClient;
import com.fangcloud.sdk.YfyRequestConfig;
import com.fangcloud.sdk.api.department.DepartmentChildrenResult;
import com.fangcloud.sdk.api.department.YfyDepartmentRequest;
import com.fangcloud.sdk.exception.YfyException;
import org.junit.Before;
import org.junit.Test;

import static com.fangcloud.sdk.SdkTestUtil.assertDepartmentNotNull;
import static com.fangcloud.sdk.SdkTestUtil.assertMiniDepartmentNotNull;

public class YfyDepartmentRequestTest {
    private YfyDepartmentRequest departmentRequest;

    @Before
    public void before() throws YfyException {
        YfyAppInfo.initAppInfo("test-client", "123456");
        YfyClient client = new YfyClient(new YfyRequestConfig(), System.getenv().get("YFY_TOKEN"));
        departmentRequest = client.departments();
    }

    @Test
    public void testGetDepartment() throws YfyException {
        assertDepartmentNotNull(departmentRequest.getDepartment(0L));
    }

    @Test
    public void testGetDepartmentChildren() throws YfyException {
        DepartmentChildrenResult departmentChildrenResult = departmentRequest.getDepartmentChildren(0L, true);
        for (YfyMiniDepartment yfyMiniDepartment : departmentChildrenResult.getChildren()) {
            assertMiniDepartmentNotNull(yfyMiniDepartment);
        }
    }
}
