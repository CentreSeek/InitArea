/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: Temperature
 * Author:   CentreS
 * Date:     2019/9/24 11:04
 * Description: 温度静态变量
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.constant;

import java.math.BigDecimal;

/**
 * @author CentreS
 * @Description: 温度静态变量
 * @create 2019/9/24
 */
public class TemperatureConstant {
    /**
     * 高低温状态
     */
    public static final String LOW_TEMPERATURE = "BLUE";
    public static final String NORMAL_TEMPERATURE = "GREEN";
    public static final String HIGHER_TEMPERATURE = "ORANGE";
    public static final String HIGH_TEMPERATURE = "RED";

    /**
     * 体温预警
     */
    public static final Integer TEMPERATURE_ALERT_NORMAL = 0;
    public static final Integer TEMPERATURE_ALERT_LOW = 1;
    public static final Integer TEMPERATURE_ALERT_HIGH = 2;

    public static final Double[] TEMPERATURE_LIST = {33.0, 33.1, 33.2, 33.3, 33.4, 33.5, 33.6, 33.7, 33.8, 33.9, 34.0, 34.1, 34.2, 34.3, 34.4, 34.5
            , 34.6, 34.7, 34.8, 34.9, 35.0, 35.1, 35.2, 35.3, 35.4, 35.5, 35.6, 35.7, 35.8, 35.9, 36.0, 36.1, 36.2, 36.3, 36.4, 36.5, 36.6, 36.7,
            36.8, 36.9, 37.0, 37.1, 37.2, 37.3, 37.4, 37.5, 37.6, 37.7, 37.8, 37.9, 38.0, 38.1, 38.2, 38.3, 38.4, 38.5, 38.6, 38.7, 38.8, 38.9,
            39.0, 39.1, 39.2, 39.3, 39.4, 39.5, 39.6, 39.7, 39.8, 39.9, 40.0, 40.1, 40.2, 40.3, 40.4, 40.5, 40.6, 40.7, 40.8, 40.9, 41.0, 41.1,
            41.2, 41.3, 41.4, 41.5, 41.6, 41.7, 41.8, 41.9, 42.0};

    public static final String ALERT_TYPE_DEFAULT = "default";
    public static final String ALERT_TYPE_DEPARTMENT = "department";
    /** 体温预开关 */
    public static final Integer ALERT_STATUS_START = 0;
    public static final Integer ALERT_STATUS_CLOSE = 1;

    /** 体温预警默认科室id */
    public static final Integer DEFAULT_DEPARTMENT_ID = -1;

    public static double add(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
