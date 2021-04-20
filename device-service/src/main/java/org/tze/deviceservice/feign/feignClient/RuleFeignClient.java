package org.tze.deviceservice.feign.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.tze.connectservice.feign.FeignConfiguration;

import java.util.Map;

@FeignClient(value = "rule-service",configuration = FeignConfiguration.class)
public interface RuleFeignClient {
    @PostMapping("/{projectId}/rules/_execute")
    void execute(@PathVariable Long projectId, @RequestBody Map params);
}
