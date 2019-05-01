package com.atguigu.springcloud.controller;

import java.util.Map;

import com.atguigu.springcloud.common.ParamData;
import com.atguigu.springcloud.common.ResultCode;
import com.atguigu.springcloud.common.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.atguigu.springcloud.service.DeptClientService;

@RestController
@RequestMapping(value = "/feign/dept")
@Api(value = "DeptConsumerFeignController", description = "Feign调用微服务")
public class DeptConsumerFeignController {
    @Autowired
    private DeptClientService service;

    @ApiOperation(value = "根据id查询部门")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultData get(Long id) {
        ResultData resultData = new ResultData();
        try {
            resultData = this.service.get(id);
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setResultCode(ResultCode.ERROR);
            resultData.setErrorMessage(e.getMessage());
        }
        return resultData;
    }

    @ApiOperation(value = "根据id删除部门")
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ResultData del(Long id) {
        ResultData resultData = new ResultData();
        try {
            resultData = this.service.del(id);
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setResultCode(ResultCode.ERROR);
            resultData.setErrorMessage(e.getMessage());
        }
        return resultData;
    }

    @ApiOperation(value = "分页查询查询部门信息")
    @ApiImplicitParam(name = "paramData", required = true, dataType = "ParamData",
            value = "查询参数\n" +
                    "{\n" +
                    "  \"pageNumber\": 0,\n" +
                    "  \"pageSize\": 5,\n" +
                    "  \"paramMap\": {" +
                    "    \"deptNo\":1,\n" +
                    "    \"deptName\": \"开发部\",\n" +
                    "    \"dbSource\": \"clouddb01\"\n" +
                    "   }\n" +
                    "}")
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ResultData findAll(@RequestBody ParamData paramData) {
        ResultData resultData = new ResultData();
        try {
            resultData = this.service.findAll(paramData);
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setResultCode(ResultCode.ERROR);
            resultData.setErrorMessage(e.getMessage());
        }
        return resultData;
    }

    @ApiOperation(value = "新增部门")
    @ApiImplicitParam(name = "dept", dataType = "Map", required = true,
            value = "部门信息\n" +
                    "{\n" +
                    "    \"deptName\": \"开发部123\",\n" +
                    "    \"dbSource\": \"clouddb01\"\n" +
                    "}")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultData add(@RequestBody Map dept) {
        ResultData resultData = new ResultData();
        try {
            resultData = this.service.add(dept);
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setResultCode(ResultCode.ERROR);
            resultData.setErrorMessage(e.getMessage());
        }
        return resultData;
    }
}
