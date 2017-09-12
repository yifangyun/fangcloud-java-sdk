package com.fangcloud.sdk.api.department;

import com.fangcloud.sdk.api.YfyBaseDTO;
import com.fangcloud.sdk.api.YfyMiniDepartment;
import com.fangcloud.sdk.api.PagingResult;

import java.util.List;

public class DepartmentChildrenResult extends YfyBaseDTO {
    private List<YfyMiniDepartment> children;

    public List<YfyMiniDepartment> getChildren() {
        return children;
    }

    public void setChildren(List<YfyMiniDepartment> children) {
        this.children = children;
    }
}
