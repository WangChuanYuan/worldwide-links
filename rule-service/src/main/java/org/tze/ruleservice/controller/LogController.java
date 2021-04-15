package org.tze.ruleservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tze.ruleservice.po.LogPO;
import org.tze.ruleservice.service.LogService;

import java.util.List;

@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/{projectId}/logs")
    public List<LogPO> getSellingGoods(@PathVariable Long projectId,
                                       @RequestParam(required = false) Long ruleId,
                                       @RequestParam(required = false) Long productId,
                                       @RequestParam(required = false) Long deviceId) {
        return logService.getLogs(projectId, ruleId, productId, deviceId);
    }
}
