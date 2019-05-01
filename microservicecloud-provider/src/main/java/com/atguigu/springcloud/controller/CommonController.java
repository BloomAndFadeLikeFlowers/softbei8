package com.atguigu.springcloud.controller;

import com.alibaba.fastjson.JSONArray;
import com.atguigu.springcloud.common.ResultCode;
import com.atguigu.springcloud.common.ResultData;
import com.atguigu.springcloud.config.ApplicationStaticConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class CommonController {

    @Autowired
    private DiscoveryClient client;
    @Autowired
    private ApplicationStaticConfig applicationStaticConfig;

    @Autowired
    Environment environment;

    private Map other = new LinkedHashMap();

    @RequestMapping(value = "/checkHealthy", method = RequestMethod.GET)
    public ResultData checkHealthy() {
        ResultData resultData = new ResultData()
                .setResultCode(ResultCode.SUCCESS)
                .setContent(client.getServices()
                        .contains(applicationStaticConfig.getApplicationName()));
        other.put("RequestChainMessage",Arrays.asList(getRequestChainMessage()));
        resultData.setOther(other);
        return resultData;
    }
    private Map getRequestChainMessage() {
        Map requestChainMessage = new LinkedHashMap();
        requestChainMessage.put("ApplicationName", applicationStaticConfig.getApplicationName());
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
        }
        requestChainMessage.put("LocalIp", localHost.getHostAddress());// 返回格式为：xxx.xxx.xxx
        requestChainMessage.put("Port", environment.getProperty("local.server.port"));
        requestChainMessage.put("HostName", localHost.getHostName());// 一般是返回电脑用户名
        return requestChainMessage;
    }

    @RequestMapping(value = "/discovery", method = RequestMethod.GET)
    public Object discovery() {
        return this.client;
    }

}
