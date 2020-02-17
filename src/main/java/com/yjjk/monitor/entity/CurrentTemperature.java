package com.yjjk.monitor.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: monitor
 * @description: ${description}
 * @author: CentreS
 * @create: 2020-02-06 14:50:01
 **/
@Data
@Accessors(chain = true)
public class CurrentTemperature {

    private String machineNum;
    private Double temperature;
    private Integer battery;
    private String boxBatteryStatus;
    private Long timestamp;

}
