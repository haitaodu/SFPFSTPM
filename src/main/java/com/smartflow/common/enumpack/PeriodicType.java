package com.smartflow.common.enumpack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PeriodicType {

    TEMPORARY(1, "临时的"),
    PERIODIC(2, "周期性的", PeriodicTypeChildren.values());
    private Integer key;
    private String label;
    private PeriodicTypeChildren[] children;

    enum PeriodicTypeChildren{

        WeekMap(1, "每周", Periodic.WeekType.class),
        MonthMap(1, "每月", Periodic.MonthType.class);
        private Integer key;
        private String label;
        private Class<? extends Periodic> children;
        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Class<? extends Periodic> getChildren() {
            return children;
        }

        public void setChildren(Class<? extends Periodic> children) {
            this.children = children;
        }

        PeriodicTypeChildren(Integer key, String label, Class<? extends Periodic> children) {
            this.key = key;
            this.label = label;
            this.children = children;
        }
    }
    interface Periodic {
        enum WeekType implements Periodic {
            MONDAY(1, "周一"),
            TUESDAY(2, "周二"),
            WEDNESDAY(3, "周三"),
            THURSDAY(4, "周四"),
            FRIDAY(5, "周五"),
            SATURDAY(6, "周六"),
            SUNDAY(7, "周日");

            WeekType(Integer key, String label) {
                this.key = key;
                this.label = label;
            }

            private Integer key;
            private String label;

            public Integer getKey() {
                return key;
            }

            public void setKey(Integer key) {
                this.key = key;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }

        enum MonthType implements Periodic {
            FIRST(1, "1号");

            MonthType(Integer key, String label) {
                this.key = key;
                this.label = label;
            }

            private Integer key;
            private String label;

            public Integer getKey() {
                return key;
            }

            public void setKey(Integer key) {
                this.key = key;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PeriodicTypeChildren[] getChildren() {
        return children;
    }

    public void setChildren(PeriodicTypeChildren[] children) {
        this.children = children;
    }

    PeriodicType(Integer key, String label) {
        this.key = key;
        this.label = label;
    }

    PeriodicType(Integer key, String label, PeriodicTypeChildren[] children) {
        this.key = key;
        this.label = label;
        this.children = children;
    }

    public static void main(String[] args) {
        List<Map<String,Object>> mapList=new ArrayList<>();
        for (PeriodicType periodicType:PeriodicType.values())
        {
            Map<String,Object> map=new HashMap<>();
            map.put("key",periodicType.getKey());
            map.put("label",periodicType.getLabel());
            List<Map<String,Object>> childrenList = new ArrayList<>();
            for (PeriodicTypeChildren childrenType:PeriodicTypeChildren.values())
            {
                Map<String,Object> childrenMap = new HashMap<>();
                childrenMap.put("key",childrenType.getKey());
                childrenMap.put("label",childrenType.getLabel());
                List<Map<String,Object>> monthWeekList = new ArrayList<>();
                for (Periodic.MonthType monthType:Periodic.MonthType.values()){
                    Map<String,Object> monthMap = new HashMap<>();
                    childrenMap.put("key",monthType.getKey());
                    childrenMap.put("label",monthType.getLabel());
                    monthWeekList.add(monthMap);
                }
                for (Periodic.WeekType weekType:Periodic.WeekType.values()){
                    Map<String,Object> weekMap = new HashMap<>();
                    childrenMap.put("key",weekType.getKey());
                    childrenMap.put("label",weekType.getLabel());
                    monthWeekList.add(weekMap);
                }
                childrenMap.put("children", monthWeekList);
                childrenList.add(childrenMap);
            }
            map.put("children", childrenList);
            mapList.add(map);
        }
    }
}
