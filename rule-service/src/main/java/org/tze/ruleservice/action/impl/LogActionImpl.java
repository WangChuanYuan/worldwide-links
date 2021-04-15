package org.tze.ruleservice.action.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tze.ruleservice.action.RuleAction;
import org.tze.ruleservice.dao.LogDAO;
import org.tze.ruleservice.po.LogPO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service("logAction")
public class LogActionImpl implements RuleAction {

    @Autowired
    private LogDAO logDAO;

    @Override
    public String getName() {
        return "logAction";
    }

    @Override
    @Transactional
    public void execute(Map<String, Object> fact, Map<String, Object> params, List<Map<String, Object>> results) {
        LogPO logPO = new LogPO();
        logPO.setTime(LocalDateTime.now())
                .setProjectId(((Integer) params.get("projectId")).longValue())
                .setRuleId(((Integer) params.get("ruleId")).longValue())
                .setDeviceId(((Integer) fact.get("deviceId")).longValue())
                .setProductId(((Integer) fact.get("productId")).longValue());

        StringBuilder logBuiler = new StringBuilder();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        logBuiler.append(dtf.format(logPO.getTime()))
                .append(" projectId: ").append(logPO.getProjectId()).append(",");
        if (logPO.getProductId() != null) {
            logBuiler.append(" productId: ").append(logPO.getProductId()).append(",");
        }
        if (logPO.getDeviceId() != null) {
            logBuiler.append(" deviceId: ").append(logPO.getDeviceId()).append(",");
        }
        logBuiler.append(" ruleId: ").append(logPO.getRuleId()).append(",");
        logBuiler.append(" fact(");
        for (Map.Entry<String, Object> f : fact.entrySet()) {
            logBuiler.append(f.getKey()).append(": ").append(f.getValue()).append(" ").append(",");
        }
        logBuiler.append(") ");
        logBuiler.append(" action(");
        for (Map.Entry<String, Object> p : params.entrySet()) {
            logBuiler.append(p.getKey()).append(": ").append(p.getValue()).append(" ").append(",");
        }
        logBuiler.append(") ");

        logPO.setContent(logBuiler.toString());
        logDAO.saveAndFlush(logPO);
    }
}
