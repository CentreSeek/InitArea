package com.yjjk.monitor.service;

import com.yjjk.monitor.entity.CurrentTemperature;
import com.yjjk.monitor.entity.properties.AreaSign;

import java.util.List;

/**
 * @program: monitor
 * @description: ${description}
 * @author: CentreS
 * @create: 2020-02-06 14:55:43
 **/
public interface TemperatureService {

    /**
     * 获取体温数据
     *
     * @return
     */
    List<CurrentTemperature> getTemperatureInfoList();

    /**
     * 定期并清理temperatureInfo表数据
     *
     * @param dateOfOneMonthAgo
     * @return
     */
    int temperatureInfoTask(String dateOfOneMonthAgo);

    /**
     * 保存定期并清理temperatureInfo表数据
     * @param dateOfOneMonthAgo
     * @return
     */
    int temperatureInfoPersistent(String dateOfOneMonthAgo);

    /**
     * 远程获取token
     * @param areaSign
     * @return
     */
    String getToken(AreaSign areaSign);

    /**
     * 远程推送体温数据
     * @param areaSign
     * @param token
     * @param data
     * @return
     */
    boolean pushTemperature(AreaSign areaSign,String token,String data);
}
