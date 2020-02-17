/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: ZsTemperatureInfo
 * Author:   CentreS
 * Date:     2019/7/19 9:39
 * Description: 体温记录
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 体温记录
 * @author CentreS
 * @create 2019/7/19
 */
@Data
@Accessors(chain = true)
public class ZsTemperatureInfo {
    private Integer temperatureId;

    private String machineNum;

    private BigDecimal temperature;
    /** 电量 */
    private Integer pattery;

    private String temperatureStatus;

    private Date createTime;

    private Long timestamp;

}