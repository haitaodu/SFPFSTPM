package com.smartflow.common.enumpack;

/**
 * @author ：tao
 * @date ：Created in 2020/8/4 10:11
 */
public enum Category {
    /**
     *TPM的设备维保类型
     */
//    CLEADN(1,"清洁清扫"),
//    CHECK(2,"点检"),
//    MANTAINENCE(3,"维护保养"),
//    CORRECT(4,"校准"),
//    REPLACE(5,"更换"),
//    REPAIRE(6,"维修");

    CHECK(1,"点检"),
    MANTAINENCE(2,"维护保养");
    private int key;
    private String value;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    Category(int key, String value)
    {
     this.key=key;
     this.value=value;
    }
}
