package org.tze.ruleservice.action;

import java.util.List;
import java.util.Map;

public interface RuleAction {
    String getName();  // 这里的名称需要与ActionVO的name相同，约定为实现类的BeanName
    void execute(Map<String, Object> fact, Map<String, Object> params, List<Map<String, Object>> results);
}
