package org.tze.connectservice.feign.feignService;

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


    public Device deviceLogin(Long deviceId, String devicePw){
        System.out.println(deviceId+" " +devicePw);
        return deviceFeignClient.deviceLogin(deviceId,devicePw);
    }


}
