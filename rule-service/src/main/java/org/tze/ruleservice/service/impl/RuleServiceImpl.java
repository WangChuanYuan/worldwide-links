package org.tze.ruleservice.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tze.ruleservice.dao.RuleDAO;
import org.tze.ruleservice.po.RulePO;
import org.tze.ruleservice.service.RuleEngine;
import org.tze.ruleservice.service.RuleService;
import org.tze.ruleservice.util.DrlUtil;
import org.tze.ruleservice.util.DroolsUtil;
import org.tze.ruleservice.vo.ActionVO;
import org.tze.ruleservice.vo.RuleVO;
import org.tze.ruleservice.vo.TriggerVO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleDAO ruleDAO;

    @Autowired
    private RuleEngine ruleEngine;

    @Override
    @Transactional
    public RuleVO create(RuleVO rule) {
        try {
            RulePO rulePO = ruleDAO.saveAndFlush(toPO(rule));
            rule.setId(rulePO.getId());

            StringBuffer drl = new StringBuffer();
            DrlUtil.insertRuleDrl(drl, rule);
            rule.setDrl(drl.toString());
            rulePO.setDrl(drl.toString());
            ruleDAO.flush();

            DroolsUtil.getInstance().addNewRule(rule.getProjectId(), rule);
            return rule;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public RuleVO getRule(Long ruleId) {
        RulePO rulePO = ruleDAO.findById(ruleId).orElse(null);
        return rulePO == null ? null : toVO(rulePO);
    }

    @Override
    public List<RuleVO> getRules(Long projectId) {
        return ruleDAO.getByProjectId(projectId).stream().map(this::toVO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RuleVO update(RuleVO rule) {
        try {
            StringBuffer drl = new StringBuffer();
            DrlUtil.insertRuleDrl(drl, rule);
            rule.setDrl(drl.toString());

            ruleDAO.saveAndFlush(toPO(rule));
            DroolsUtil.getInstance().updateRule(rule.getProjectId(), rule);
            return rule;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public boolean delete(Long ruleId) {
        try {
            RulePO rulePO = ruleDAO.findById(ruleId).orElse(null);
            if (rulePO != null) {
                ruleDAO.delete(rulePO);
                DroolsUtil.getInstance().deleteRule(rulePO.getProjectId(), ruleId);
            } else return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void execute(Long projectId, Map params) {
        ruleEngine.executeRuleEngine(projectId, params);
    }

    private RulePO toPO(RuleVO rule) {
        RulePO rulePO = new RulePO();
        rulePO.setId(rule.getId())
                .setProjectId(rule.getProjectId())
                .setProductId(rule.getProductId())
                .setDeviceId(rule.getDeviceId())
                .setName(rule.getName())
                .setDescription(rule.getDescription())
                .setBegin(rule.getBegin())
                .setEnd(rule.getEnd())
                .setEnabled(rule.isEnabled())
                .setDrl(rule.getDrl());
        rulePO.setTriggersJson(JSON.toJSONString(rule.getTriggers()));
        rulePO.setActionsJson(JSON.toJSONString(rule.getActions()));
        return rulePO;
    }

    private RuleVO toVO(RulePO rulePO) {
        RuleVO rule = new RuleVO();
        rule.setId(rulePO.getId())
                .setProjectId(rulePO.getProjectId())
                .setProductId(rulePO.getProductId())
                .setDeviceId(rulePO.getDeviceId())
                .setName(rulePO.getName())
                .setDescription(rulePO.getDescription())
                .setBegin(rulePO.getBegin())
                .setEnd(rulePO.getEnd())
                .setEnabled(rulePO.isEnabled())
                .setDrl(rulePO.getDrl());
        rule.setTriggers(JSON.parseArray(rulePO.getTriggersJson(), TriggerVO.class));
        rule.setActions(JSON.parseArray(rulePO.getActionsJson(), ActionVO.class));
        return rule;
    }

}
