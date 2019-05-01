package com.atguigu.springcloud.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.atguigu.springcloud.utils.MyRequestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RequestLogAspect {

    private Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);
    private static String requestBeginFlag = "RequestBegin";
    private static String requestMessageFlag = "RequestMessage";
    private static String requestEndFlag = "RequestEnd";

    @Pointcut("execution( * com.atguigu.springcloud.controller.*.*(..))")//两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void requestPointCut() {

    }

    @Before("requestPointCut()")
    public void requestBefore(JoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = MyRequestUtils.getRequest();
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        String requestUUID = MyRequestUtils.getRequestUUID(request);
        logger.info(requestBeginFlag + ":" + requestUUID);
    }

    @Around("requestPointCut()")
    public Object requestAround(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = MyRequestUtils.getRequest();

        String method = request.getMethod();
        String url = request.getRequestURI();
        String queryString = request.getQueryString();
        String ip = MyRequestUtils.getIpAddr(request);
        String ControllerMethod = pjp.getSignature().getDeclaringTypeName() + "."
                + pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        String params = "";

        Object result = pjp.proceed();

        long endTime = System.currentTimeMillis();
        try {
            long startTime = (long) request.getAttribute("startTime");
            //获取请求参数集合并进行遍历拼接
            if (args.length > 0) {
                if ("POST".equals(method)) {
                    params = JSONArray.toJSONString(args);
                } else if ("GET".equals(method)) {
                    params = StringUtils.isEmpty(queryString) ? "" : queryString;
                }
                // params = URLDecoder.decode(params, "UTF-8");
            }

            String requestUUID = MyRequestUtils.getRequestUUID(request);

            //"requestUUID:{}，ip:{},requestMethod:{},url:{},params:{},elapsed:{}ms,ControllerMethod;{},responseBody:{}"
            String responseBody = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
            logger.info(requestMessageFlag + ":{}\t{}\t{}\t{}\t{}\t{}\t{}\t{}",
                    requestUUID, ip, method, url, params, (endTime - startTime),ControllerMethod,responseBody);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("log error !!", e);
        }

        return result;
    }

    @After("requestPointCut()")
    public void requestAfter() throws Throwable {

        HttpServletRequest request = MyRequestUtils.getRequest();
        String requestUUID = MyRequestUtils.getRequestUUID(request);
        logger.info(requestEndFlag + ":" + requestUUID);
    }
}
