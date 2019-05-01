package com.atguigu.springcloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atguigu.springcloud.common.ResultCode;
import com.atguigu.springcloud.common.ResultData;
import com.atguigu.springcloud.config.ApplicationStaticConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.function.Consumer;

@Api(value = "CheckHealthyController", description = "健康监测")
@RestController
public class CommonController {

    @Autowired
    Environment environment;
    @Autowired
    private DiscoveryClient client;
    @Autowired
    private ApplicationStaticConfig applicationStaticConfig;
    @Autowired
    private RestTemplate restTemplate;

    private Map other = new LinkedHashMap();

    /**
     * 判断当前服务在注册中心中是否存在
     *
     * @return
     */
    @ApiOperation(value = "Consumer健康监测")
    @RequestMapping(value = "/consumerCheckHealthy", method = RequestMethod.GET)
    public ResultData consumerCheckHealthy() {
        other.put("LocalApplicationName", applicationStaticConfig.getApplicationName());
        ResultData resultData = new ResultData()
                .setResultCode(ResultCode.SUCCESS)
                .setContent(client.getServices()
                        .contains(applicationStaticConfig.getApplicationName()));
        other.put("RequestChainMessage", Arrays.asList(getRequestChainMessage()));
        resultData.setOther(other);
        return resultData;
    }

    @ApiOperation(value = "Provider健康监测")
    @RequestMapping(value = "/providerCheckHealthy", method = RequestMethod.GET)
    public ResultData providerCheckHealthy() {
        other.put("LocalApplicationName", applicationStaticConfig.getApplicationName());
        ResultData resultData = restTemplate.getForObject(applicationStaticConfig.getProviderUrl() + "/checkHealthy", ResultData.class);
        List<Map> value = (List<Map>) resultData.getOther().get("RequestChainMessage");
        value.add(0, getRequestChainMessage());
        other.put("RequestChainMessage", value);
        resultData.setOther(other);
        return resultData;
    }

    @ApiOperation(value = "Gateway健康监测")
    @RequestMapping(value = "/gatewayCheckHealthy", method = RequestMethod.GET)
    public ResultData gatewayCheckHealthy() {

        other.put("LocalApplicationName", applicationStaticConfig.getApplicationName());
        ResultData resultData = restTemplate.getForObject(applicationStaticConfig.getGatewayUrl() + "/checkHealthy", ResultData.class);
        List<Map> value = (List<Map>) resultData.getOther().get("RequestChainMessage");
        value.add(0, getRequestChainMessage());
        other.put("RequestChainMessage", value);
        resultData.setOther(other);
        return resultData;
    }

    /**
     * 查询注册中心的服务和实例列表
     *
     * @return
     */
    @ApiOperation(value = "查询注册中心的服务和实例列表")
    @RequestMapping(value = "/discovery", method = RequestMethod.GET)
    public ResultData discovery() {
        other.put("ApplicationName", applicationStaticConfig.getApplicationName());
        ResultData resultData = new ResultData()
                .setResultCode(ResultCode.SUCCESS)
                .setContent(this.client)
                .setOther(other);
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

//    /**
//     * 查询注册中心的服务列表
//     *
//     * @return
//     */
//    @ApiOperation(value = "获取注册中心服务列表")
//    @RequestMapping(value = "/getServiceList", method = RequestMethod.GET)
//    public ResultData getServiceList() {
//        ResultData resultData = new ResultData()
//                .setResultCode(ResultCode.SUCCESS)
//                .setContent(JSONArray.parse(JSON.toJSONString(client.getServices()).toLowerCase()))
//                .setOther(other);
//        return resultData;
//    }
//
//    /**
//     * 微服务调用链路测试
//     *
//     * @return
//     */
//    @ApiOperation(value = "微服务调用链路测试")
//    @ApiImplicitParam(name = "serverList", dataType = "List", required = true,
//            value = "微服务调用列表\n" +
//                    "[\n" +
//                    "    \"microservicecloud-consumer\",\n" +
//                    "    \"microservicecloud-provider\"\n" +
//                    "]")
//    @RequestMapping(value = "/execServerList", method = RequestMethod.POST)
//    public ResultData execServerList(@RequestBody List serverList) {
//        if (serverList.size() == 1) {
//            ResultData resultData = new ResultData()
//                    .setResultCode(ResultCode.SUCCESS)
//                    .setContent(JSONArray.parse(JSON.toJSONString(client.getServices()).toLowerCase()))
//                    .setOther(other);
//            return resultData;
//        } else if (serverList.size() > 1) {
//            serverList.remove(0);
//            ResultData resultData = restTemplate.getForObject(applicationStaticConfig.getProviderUrl() + "/checkHealthy", ResultData.class);
//        }else {
//
//        }
//        return null;
//    }
}
