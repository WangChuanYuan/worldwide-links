package org.tze.deviceservice.feign.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tze.connectservice.feign.FeignConfiguration;
import org.tze.connectservice.feign.feignEntity.Device;

/**
 * @Author: WangMo
 * @Description:
 */

@FeignClient(value = "device-service",configuration = FeignConfiguration.class)
public interface DeviceFeignClient {
    @RequestMapping(value = "/device/login",method = RequestMethod.GET)
    Device deviceLogin(@RequestParam("deviceId")Long deviceId, @RequestParam("devicePw")String devicePw);
}
