package com.atguigu.springcloud.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class ResultData {
    private String resultCode;
    private String errorMessage;
    private Object content;
    private Map other;


}
