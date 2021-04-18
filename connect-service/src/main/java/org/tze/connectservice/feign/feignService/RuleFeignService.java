package org.tze.connectservice.feign.feignService;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.tze.connectservice.feign.feignClient.RuleFeignClient;
import org.tze.connectservice.feign.feignEntity.Device;

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
