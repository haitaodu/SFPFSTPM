package com.smartflow.common.util;

import com.smartflow.common.enumpack.Category;

/**
 * @author ：tao
 * @date ：Created in 2020/8/4 14:52
 */

public class CategoryUtil {

    public static String getValueByKey(int key)
    {
        for (Category category:Category.values())
        {

            if (key==category.getKey())
            {
                return category.getValue();
            }
        }
        return "无此选项";
    }
}
