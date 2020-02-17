/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: ReflectUtils
 * Author:   CentreS
 * Date:     2019/9/24 9:27
 * Description: 反射工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author CentreS
 * @Description: 反射工具类
 * @create 2019/9/24
 */
public class ReflectUtils {

    /**
     * 2个实体类相互转换赋值()
     * 字段名的类型和名称必须完全相同才可以，继承的父类字段无法赋值
     *
     * @param source object
     * @param target Object.class
     * @return
     */
    public static <T> T transformToBean(Object source, Class<T> target) {
        // 得到类对象
        Object o = null;
        Class sourceCla = source.getClass();
        Field[] sourceFields = sourceCla.getDeclaredFields();
        Field[] targetFields = target.getDeclaredFields();
        try {
            o = target.newInstance();
            for (int i = 0; i < targetFields.length; i++) {
                Field ft = targetFields[i];
                // 设置些属性是可以访问的
                boolean isStaticFt = Modifier.isStatic(ft.getModifiers());
                if (isStaticFt) {
                    continue;
                }
                // 设置些属性是可以访问的
                ft.setAccessible(true);

                for (int j = 0; j < sourceFields.length; j++) {
                    Field fs = sourceFields[j];
                    // 设置些属性是可以访问的
                    boolean isStaticFs = Modifier.isStatic(fs.getModifiers());
                    if (isStaticFs) {
                        continue;
                    }
                    // 设置些属性是可以访问的
                    fs.setAccessible(true);
                    if (fs.getName().equals(ft.getName()) && fs.getType().toString().equals(ft.getType().toString())) {
                        // 得到此属性的值
                        Object val = fs.get(source);

                        ft.set(o, val);
                        break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) o;
    }
}
