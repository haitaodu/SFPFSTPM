package com.smartflow.dto.maintenanceitem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartflow.common.enumpack.Category;
import com.smartflow.common.enumpack.Page;

import javax.validation.constraints.NotNull;

/**
 * @author ：tao
 * @date ：Created in 2020/8/9 17:07
 */

public class MaintenanceItemSearchDTO {
    @NotNull(message = "分页页码不能为空")
    private Integer pageIndex;
    @NotNull(message = "分页大小不能为空")
    private Integer pageSize;
    private String maintenanceItem;



    @JsonProperty("PageIndex")
    public Integer getPageIndex() {
        return pageIndex;

    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }
    @JsonProperty("PageSize")
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    @JsonProperty("MaintenanceItemName")
    public String getMaintenanceItem() {
        return maintenanceItem;
    }

    public void setMaintenanceItem(String maintenanceItem) {
        this.maintenanceItem = maintenanceItem;
    }

    @Override
    public String toString() {
        return "MaintenanceItemSearchDTO{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", maintenanceitem='" + maintenanceItem + '\'' +
                '}';
    }
}
