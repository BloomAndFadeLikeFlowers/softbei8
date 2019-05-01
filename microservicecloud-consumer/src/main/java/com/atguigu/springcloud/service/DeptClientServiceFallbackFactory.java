package com.atguigu.springcloud.service;

import java.util.List;
import java.util.Map;

import com.atguigu.springcloud.common.ParamData;
import com.atguigu.springcloud.common.ResultData;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component // 不要忘记添加，不要忘记添加
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public ResultData get(long id) {
                return null;
            }

            @Override
            public ResultData del(long id) {
                return null;
            }

            @Override
            public ResultData findAll(ParamData paramData) {
                return null;
            }

            @Override
            public ResultData add(Map dept) {
                return null;
            }
        };
    }
}
