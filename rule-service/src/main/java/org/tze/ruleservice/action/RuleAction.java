package org.tze.ruleservice.action;

import org.tze.ruleservice.vo.RuleVO;

import java.util.List;
import java.util.Map;

public interface RuleAction {
    void execute(Map<String, Object> fact, Map<String, Object> params, List<Map<String, Object>> result);
}
