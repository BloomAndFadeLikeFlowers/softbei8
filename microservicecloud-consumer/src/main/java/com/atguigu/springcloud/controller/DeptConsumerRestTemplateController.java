package com.atguigu.springcloud.controller;

import java.util.Map;

import com.atguigu.springcloud.common.ParamData;
import com.atguigu.springcloud.common.ResultCode;
import com.atguigu.springcloud.common.ResultData;
import com.atguigu.springcloud.config.ApplicationStaticConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/restTemplate/dept")
@Api(value = "DeptConsumerRestTemplateController", description = "RestTempLate调用微服务")
public class DeptConsumerRestTemplateController {

    @Autowired
    private ApplicationStaticConfig applicationStaticConfig;


    /**
     * 使用 使用restTemplate访问restful接口非常的简单粗暴无脑。 (url, requestMap,
     * ResponseBean.class)这三个参数分别代表 REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。
     */
    @Autowired
    private RestTemplate restTemplate;

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
            resultData = restTemplate.postForObject(applicationStaticConfig.getProviderUrl() + "/dept/add", dept, ResultData.class);
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setResultCode(ResultCode.ERROR);
            resultData.setErrorMessage(e.getMessage());
        }
        return resultData;
    }

    @ApiOperation(value = "根据id查询部门")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultData get(@RequestParam("id") Long id) {
        ResultData resultData = new ResultData();
        try {
            resultData = restTemplate.getForObject(applicationStaticConfig.getProviderUrl() + "/dept/get?id=" + id, ResultData.class);
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setResultCode(ResultCode.ERROR);
            resultData.setErrorMessage(e.getMessage());
        }
        return resultData;
    }

    @ApiOperation(value = "根据id删除部门")
    @RequestMapping(value = "/dept/del", method = RequestMethod.GET)
    public ResultData del(@RequestParam("id") Long id) {
        ResultData resultData = new ResultData();
        try {
            resultData = restTemplate.getForObject(applicationStaticConfig.getProviderUrl() + "/dept/del?id=" + id, ResultData.class);
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
            resultData = restTemplate.postForObject(applicationStaticConfig.getProviderUrl() + "/dept/findAll", paramData, ResultData.class);
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setResultCode(ResultCode.ERROR);
            resultData.setErrorMessage(e.getMessage());
        }
        return resultData;
    }
}
