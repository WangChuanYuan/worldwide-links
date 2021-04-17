package org.tze.ruleservice.service;

import org.tze.ruleservice.po.LogPO;

import java.util.List;

public interface LogService {

   List<LogPO> getLogs(Long projectId, Long ruleId, Long productId, Long deviceId);
}
