package com.centres.area.mapper;

import com.centres.area.entity.AreaInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AreaInfoMapper {
    int deleteAll();

    int insert(AreaInfoEntity record);

    int insertSelective(AreaInfoEntity record);

    AreaInfoEntity selectByCode(String code);

}