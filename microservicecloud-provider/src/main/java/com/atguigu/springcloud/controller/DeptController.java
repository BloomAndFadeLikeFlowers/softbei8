package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.common.ParamData;
import com.atguigu.springcloud.common.ResultCode;
import com.atguigu.springcloud.common.ResultData;
import com.atguigu.springcloud.config.ApplicationStaticConfig;
import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DeptController {
    @Autowired
    private DeptService service;
    @Autowired
    private ApplicationStaticConfig applicationStaticConfig;

    @RequestMapping(value = "/dept/add", method = RequestMethod.POST)
    public ResultData add(@RequestBody Dept dept) {
        ResultData resultData = new ResultData();
        try {
            resultData.setContent(service.add(dept));
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setErrorMessage(e.getMessage());
            resultData.setResultCode(ResultCode.ERROR);
        }
        return resultData;
    }

    @RequestMapping(value = "/dept/get", method = RequestMethod.GET)
    public ResultData get(@RequestParam("id") Long id) {
        ResultData resultData = new ResultData();
        try {
            resultData.setContent(service.get(id));
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setErrorMessage(e.getMessage());
            resultData.setResultCode(ResultCode.ERROR);
        }
        return resultData;
    }

    @RequestMapping(value = "/dept/del", method = RequestMethod.GET)
    public ResultData del(@RequestParam("id") Long id) {
        ResultData resultData = new ResultData();
        try {
            service.del(id);
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setErrorMessage(e.getMessage());
            resultData.setResultCode(ResultCode.ERROR);
        }
        return resultData;
    }

    @RequestMapping(value = "/dept/findAll", method = RequestMethod.POST)
    public ResultData findAll(@RequestBody ParamData paramData) {
        ResultData resultData = new ResultData();
        try {
            resultData.setContent(service.findAll(paramData));
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setErrorMessage(e.getMessage());
            resultData.setResultCode(ResultCode.ERROR);
        }
        return resultData;
    }
}
