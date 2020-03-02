/**
 * Copyright (C), 2019, 义金(杭州)健康科技有限公司
 * FileName: BaseController
 * Author:   CentreS
 * Date:     2019-06-18 15:59
 * Description: BaseController
 * History:
 * <author>          <time>          <version>          <desc>
 * CentreS         2019/06/18          1.0.0
 */
package com.centres.area.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.centres.area.entity.AreaInfoEntity;
import com.centres.area.mapper.AreaInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CentreS
 * @Description: BaseController
 * @create 2019-06-18
 */
@CrossOrigin
public class BaseController {

    protected static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Resource
    protected AreaInfoMapper areaInfoMapper;
}
