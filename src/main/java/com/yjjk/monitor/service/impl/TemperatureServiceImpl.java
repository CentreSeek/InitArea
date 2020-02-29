package com.yjjk.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.yjjk.monitor.entity.CurrentTemperature;
import com.yjjk.monitor.entity.TokenResultEntity.PushResult;
import com.yjjk.monitor.entity.TokenResultEntity.TokenResult;
import com.yjjk.monitor.entity.properties.AreaSign;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.TemperatureService;
import com.yjjk.monitor.utility.ExcelUtils;
import com.yjjk.monitor.utility.FileUtils;
import com.yjjk.monitor.utility.NetUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: monitor
 * @description: ${description}
 * @author: CentreS
 * @create: 2020-02-06 14:56:08
 **/
@Service
public class TemperatureServiceImpl extends BaseService implements TemperatureService {
    @Override
    public List<CurrentTemperature> getTemperatureInfoList() {
        return super.zsTemperatureInfoMapper.getTemperatureInfoList();

    }

    @Override
    public int temperatureInfoTask(String dateOfOneMonthAgo) {
        temperatureInfoPersistent(dateOfOneMonthAgo);
        return this.zsTemperatureInfoMapper.temperatureInfoTask(dateOfOneMonthAgo);
    }

    @Override
    public int temperatureInfoPersistent(String dateOfOneMonthAgo) {
        List<String> exportTemperatures = this.zsTemperatureInfoMapper.getExportTemperatures(dateOfOneMonthAgo);
        ExcelUtils.writeToTxt(exportTemperatures, FileUtils.getRootPath() + "\\ExportData\\TemperatureExport");
        return exportTemperatures.size();
    }

    @Override
    public String getToken(AreaSign areaSign) {
        try {
            TokenResult accessToken = NetUtils.getAccessToken(areaSign);
            if (accessToken.getCode() != 0) {
                return null;
            } else {
                return accessToken.getToken();
            }
        } catch (IOException e) {
            logger.error("网络链接故障");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean pushTemperature(AreaSign areaSign, String token, String data) {
        Map header = new HashMap();
//        header.put("Authorization", token);
        Map content = new HashMap();
        content.put("command", "savetempdata");
        content.put("dept", data);

        String s = NetUtils.postMap(areaSign.getAreaSign(), header, content);
        PushResult pushResult = JSON.parseObject(s, PushResult.class);
        if ("success".equals(pushResult.getStatus())) {
            return true;
        } else{
            logger.error(pushResult.getStatus());
            return false;
        }
    }

}
