package org.tze.deviceservice.feign.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tze.deviceservice.entity.Device;
import org.tze.deviceservice.feign.FeignConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WangMo
 * @Description:
 */

@FeignClient(value = "connect-service",configuration = FeignConfiguration.class)
public interface ConnectFeignClient {

    @RequestMapping(value = "/connect/getOnlineDevice",method = RequestMethod.GET)
     List<Device> getOnlineDevice();

    @RequestMapping(value = "/connect/disconnect",method = RequestMethod.POST)
    boolean disconnect(@RequestParam("deviceId") Long deviceId);
}
