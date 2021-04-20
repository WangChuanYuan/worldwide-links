package org.tze.deviceservice.feign.feignService;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tze.connectservice.feign.feignClient.DeviceFeignClient;
import org.tze.connectservice.feign.feignEntity.Device;

/**
 * @Author: WangMo
 * @Description:
 */

@Service
public class DeviceFeignService {
    @Autowired
    DeviceFeignClient deviceFeignClient;

    @HystrixCommand(fallbackMethod = "deviceLoginFallbackMethod")
    public Device deviceLogin(Long deviceId, String devicePw){
        return deviceFeignClient.deviceLogin(deviceId,devicePw);
    }

    public Device deviceLoginFallbackMethod(long coachId){
        System.out.println("设备微服务--登录接口调用失败");
        return null;
    }
}
