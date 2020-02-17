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
package com.yjjk.monitor.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yjjk.monitor.filter.AliValueFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;

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

    /** 返回值数值 */
    private static String RESULT_CODE_SUCCESS = "200";
    private static String RESULT_CODE_FAIL = "300";







    /**
     * WEB端返回值，判断是否过期
     *
     * @param request
     * @param response
     * @param obj
     */
    public void returnResult(long startTime, HttpServletRequest request, HttpServletResponse response,
                             boolean resultCode, String message, Object obj) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("message", message);
            resultMap.put("data", obj);
            //判断是否已经登录
            if (resultCode) {
                resultMap.put("code", RESULT_CODE_SUCCESS);
            } else {
                resultMap.put("code", RESULT_CODE_FAIL);
            }
            //解决long型过长精度丢失的问题
            String jsonArray = JSON.toJSONString(resultMap, new AliValueFilter(), new SerializerFeature[0]);
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            response.setContentType("text/xml;");
            response.setHeader("Cache-Control", "no-cache");
            out.print(jsonArray);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /**
//     * validate params
//     *
//     * @param bindingResult
//     * @return
//     */
//    protected CommonResult validParams(BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            FieldError fieldError = bindingResult.getFieldError();
//            return processBindingError(fieldError);
//        }
//        return ResultUtil.returnSuccess("");
//    }

//    /**
//     * 根据spring binding 错误信息自定义返回错误码和错误信息
//     *
//     * @param fieldError
//     * @return
//     */
//    private CommonResult processBindingError(FieldError fieldError) {
//        String code = fieldError.getCode();
//        logger.debug("validator error code: {}", code);
//        switch (code) {
//            case "NotEmpty":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_EMPTY.getCode(), fieldError.getDefaultMessage());
//            case "NotBlank":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_EMPTY.getCode(), fieldError.getDefaultMessage());
//            case "NotNull":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_EMPTY.getCode(), fieldError.getDefaultMessage());
//            case "Pattern":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            case "Min":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            case "Max":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            case "Length":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            case "Range":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            case "Email":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            case "DecimalMin":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            case "DecimalMax":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            case "Size":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            case "Digits":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            case "Past":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            case "Future":
//                return ResultUtil.returnError(ErrorCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage());
//            default:
//                return ResultUtil.returnError(ErrorCodeEnum.UNKNOWN_ERROR);
//        }
//    }

}
