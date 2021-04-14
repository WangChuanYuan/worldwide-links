package org.tze.ruleservice.service;

import org.tze.ruleservice.vo.RuleVO;

import java.util.List;
import java.util.Map;

public interface RuleService {

    RuleVO create(RuleVO rule);

    RuleVO getRule(Long ruleId);

    List<RuleVO> getRules(Long projectId);

    RuleVO update(RuleVO rule);

    boolean delete(Long ruleId);

    void execute(Long projectId, Map params);

}
