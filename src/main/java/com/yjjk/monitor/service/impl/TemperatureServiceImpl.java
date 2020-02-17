package com.yjjk.monitor.service.impl;

import com.yjjk.monitor.entity.CurrentTemperature;
import com.yjjk.monitor.service.BaseService;
import com.yjjk.monitor.service.TemperatureService;
import com.yjjk.monitor.utility.ExcelUtils;
import com.yjjk.monitor.utility.FileUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
