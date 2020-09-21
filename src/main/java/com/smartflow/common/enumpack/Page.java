package com.smartflow.common.enumpack;

/**
 * @author ：tao
 * @date ：Created in 2020/8/9 17:30

 */

public enum  Page {
    /**
     * 分页查询
     */
    PAGE_INDEX_NULL(202,"分页页码不能为空"),
    PAGE_SIZE_NULL(202,"分页大小不能为空");
    private int errCode;
    private String message;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    Page(int errCode, String message)
    {
        this.errCode=errCode;
        this.message=message;
    }
}
