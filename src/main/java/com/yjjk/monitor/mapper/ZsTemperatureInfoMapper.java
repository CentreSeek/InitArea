package com.yjjk.monitor.mapper;

import com.yjjk.monitor.entity.CurrentTemperature;
import com.yjjk.monitor.entity.ZsTemperatureInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZsTemperatureInfoMapper {
    int deleteByPrimaryKey(Integer temperatureId);

    int insert(ZsTemperatureInfo record);

    int insertSelective(ZsTemperatureInfo record);

    ZsTemperatureInfo selectByPrimaryKey(Integer temperatureId);

    int updateByPrimaryKeySelective(ZsTemperatureInfo record);

    int updateByPrimaryKey(ZsTemperatureInfo record);

    /**
     * delete---定期清理temperatureInfo表数据
     * @param dateOfOneMonthAgo
     * @return
     */
    int temperatureInfoTask(String dateOfOneMonthAgo);

    /**
     * 导出体温数据
     * @param dateOfOneMonthAgo
     * @return
     */
    List<String> getExportTemperatures(String dateOfOneMonthAgo);

    /**
     * 获取体温数据
     * @return
     */
    List<CurrentTemperature> getTemperatureInfoList();
}