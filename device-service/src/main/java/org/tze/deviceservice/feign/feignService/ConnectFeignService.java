package org.tze.deviceservice.feign.feignService;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.tze.deviceservice.entity.Device;
import org.tze.deviceservice.feign.feignClient.ConnectFeignClient;


import java.util.List;

/**
 * @Author: WangMo
 * @Description:
 */

@Service
public class ConnectFeignService {
    @Autowired
    ConnectFeignClient connectFeignClient;


    public  List<Device> getOnlineDevice(){
        return connectFeignClient.getOnlineDevice();
    }

    public  boolean disconnect(@RequestParam("deviceId") Long deviceId){
        return connectFeignClient.disconnect(deviceId);
    }


}
