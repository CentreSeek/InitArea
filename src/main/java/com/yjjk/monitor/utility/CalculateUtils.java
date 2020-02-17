/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: CalculateUtils
 * Author:   CentreS
 * Date:     2019/11/19 16:10
 * Description: 计算模块
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.utility;

import java.math.BigDecimal;

/**
 * @author CentreS
 * @Description: 计算模块
 * @create 2019/11/19
 */
public class CalculateUtils {
    private static final BigDecimal c = new BigDecimal("2");

    public static BigDecimal avg(BigDecimal a, BigDecimal b) {
        return a.add(b).divide(c);
    }
}
