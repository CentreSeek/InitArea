/**
 * Copyright (C), 2019, 寻央大人留书
 * FileName: ParamUtils
 * Author:   CentreS
 * Date:     2019/11/14 13:47
 * Description: 参数工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.utility;

/**
 * @Description: 参数工具
 * @author CentreS
 * @create 2019/11/14
 */
public class ParamUtils {

    /**
     * 获取machineTypeId
     * @param machineType 0:体温贴 1：心电贴
     * @return
     */
    public static Integer setMachineTypeId(Integer machineType){
        if (machineType != null) {
            switch (machineType) {
                case 0:
                    return 4;
                case 1:
                    return 3;
            }
        }
        return null;
    }
}
