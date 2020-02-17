package com.yjjk.monitor.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class WeblogAspect
{
    static final Logger logger = LoggerFactory.getLogger(WeblogAspect.class);
//    @Resource
//    LoginStateService loginStateService;

    @Pointcut("execution(public * com.yjjk.monitor.controller.*.*(..))")
    private void controllerAspect() {}

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint)
    {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        logger.info("[ip:{}] [url:{}] [method:({}) {}]", new Object[] { request.getRemoteAddr(), request.getRequestURI(), request
                .getMethod(), joinPoint.getSignature() });
        logger.info("args: " + Arrays.toString(joinPoint.getArgs()));
    }


}
