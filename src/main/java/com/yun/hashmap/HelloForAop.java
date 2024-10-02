package com.yun.hashmap;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/10/02/9:12
 * @Description:
 */
@Slf4j
public class HelloForAop implements MapAop{
    @Override
    public void print(AopContext aopContext) {
        log.info("aop前置操作:K值{}，V值{}",aopContext.getKey(),aopContext.getValue());
    }
}
