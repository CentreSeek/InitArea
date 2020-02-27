/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: SaticScheduleTask
 * Author:   CentreS
 * Date:     2019-06-21 17:55
 * Description: 定时批量过期预约
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yjjk.monitor.configer;

import com.alibaba.fastjson.JSON;
import com.yjjk.monitor.entity.CurrentTemperature;
import com.yjjk.monitor.entity.ResultEntity;
import com.yjjk.monitor.entity.properties.AreaSign;
import com.yjjk.monitor.service.TemperatureService;
import com.yjjk.monitor.utility.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Configuration      // 1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
/** 2.开启定时任务 */
/**
 * @author CentreS
 * @Description: 定时批量过期预约
 * @create 2019-06-21
 */
public class TimingPlan {

    private static Logger logger = LoggerFactory.getLogger(TimingPlan.class);
    private String token = null;
    @Resource
    private TemperatureService temperatureService;
    @Resource
    private AreaSign areaSign;

    /**
     * 定时计划：1.清理过期预约
     */
    @Scheduled(cron = "0 0 0 1/15 * ?")
    private void configureTasks() {
        String date = DateUtil.getOneMonthAgo();
        int i = temperatureService.temperatureInfoTask(date);
        logger.info("执行体温定时计划     时间: " + date + "   执行条数:" + i);
    }

    /**
     * 实时体温数据推送
     */
//    @Scheduled(cron = "*/2 * * * * ?")
    @Scheduled(cron = "0 */1 * * * ?")
    private void pushTemperatureInfo() {
        if (token == null) {
            token = temperatureService.getToken(areaSign);
            logger.info("获取token ：" + token);
            if (token == null) {
                logger.error("获取token失败");
            }
        }
        logger.info("执行体温推送任务 ：" + DateUtil.getCurrentTime());
        List<CurrentTemperature> list = temperatureService.getTemperatureInfoList();
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setBoxBatteryStatus("NORMAL");
        }

        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setSuccess(true).setMessage("success").setData(list);
        String jsonResult = JSON.toJSONString(resultEntity);
        logger.info("推送数据     ：" + jsonResult);
        try {
            boolean result = temperatureService.pushTemperature(areaSign, token, jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
