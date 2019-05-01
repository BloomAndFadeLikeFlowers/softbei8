package com.atguigu.springcloud.service;

import java.util.Map;

import com.atguigu.springcloud.common.ParamData;
import com.atguigu.springcloud.common.ResultData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * @author zzyy
 * @Description: 修改microservicecloud-api工程，根据已经有的DeptClientService接口
 * <p>
 * 新建
 * <p>
 * 一个实现了FallbackFactory接口的类DeptClientServiceFallbackFactory
 * @date 2018年4月21日
 */
//@FeignClient(value = "MICROSERVICECLOUD-DEPT/ribbon")
@FeignClient(value = "MICROSERVICECLOUD-PROVIDER"/*,fallbackFactory=DeptClientServiceFallbackFactory.class*/)
public interface DeptClientService {
    @RequestMapping(value = "/dept/get", method = RequestMethod.GET)
    public ResultData get(@RequestParam("id") long id);

    @RequestMapping(value = "/dept/del", method = RequestMethod.GET)
    public ResultData del(@RequestParam("id") long id);

    @RequestMapping(value = "/dept/findAll", method = RequestMethod.POST)
    public ResultData findAll(@RequestBody ParamData paramData);

    @RequestMapping(value = "/dept/add", method = RequestMethod.POST)
    public ResultData add(Map dept);

}
