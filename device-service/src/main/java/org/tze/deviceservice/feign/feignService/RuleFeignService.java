package org.tze.deviceservice.feign.feignService;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tze.connectservice.feign.feignClient.RuleFeignClient;

import java.util.Map;

/**
 * @Author: WangMo
 * @Description:
 */
@Service
public class RuleFeignService {
    @Autowired
    RuleFeignClient ruleFeignClient;

    @HystrixCommand(fallbackMethod = "executeFallbackMethod")
    public void execute(Long projectId,Map params){
        ruleFeignClient.execute(projectId,params);
    }

    public void executeFallbackMethod(Long projectId,Map params){
        System.out.println("规则引擎微服务--excute接口调用");
    }
}
