package org.tze.ruleservice.util;

import org.tze.ruleservice.vo.ActionVO;
import org.tze.ruleservice.vo.ConditionVO;
import org.tze.ruleservice.vo.RuleVO;
import org.tze.ruleservice.vo.TriggerVO;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrlUtil {

    public static StringBuffer insertPackageAndGlobal(StringBuffer drlBuffer) {
        drlBuffer.append("package org.tze.ruleservice.dsl;").append(System.lineSeparator());
        drlBuffer.append("global java.util.List $results;").append(System.lineSeparator());
        // 全局行为对象作为服务
        PackageScanner scan = new PackageScanner("org.tze.ruleservice.action.impl");
        try {
            List<String> actionClazz  = scan.getFullyQualifiedClassNameList();
            for (String clazz : actionClazz) {
                String beanName = SpringContextUtil.getBeanNameOfClazzAsConvention(clazz);
                drlBuffer.append("global ").append(clazz).
                        append(" $action_").append(beanName).append(";").append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drlBuffer;
    }

    public static StringBuffer insertImport(StringBuffer drlBuffer){
        // 导入基本类
        drlBuffer.append("import java.lang.*;").append(System.lineSeparator());
        drlBuffer.append("import java.util.Map;").append(System.lineSeparator());
        drlBuffer.append("import java.util.HashMap;").append(System.lineSeparator());
        drlBuffer.append("import java.util.List;").append(System.lineSeparator());

        // 导入行为类
        drlBuffer.append("import org.tze.ruleservice.action.RuleAction;").append(System.lineSeparator());
        drlBuffer.append("import org.tze.ruleservice.action.impl.*;").append(System.lineSeparator());

        return drlBuffer;
    }

    public static StringBuffer insertRuleDrl(StringBuffer drlBuffer, RuleVO rule) {
        insertRuleName(drlBuffer,rule);
        insertExpiryDate(drlBuffer, rule);
        insertRuleCondition(drlBuffer, rule);
        insertRuleAction(drlBuffer, rule);
        return drlBuffer;
    }

    private static StringBuffer insertRuleName(StringBuffer drlBuffer, RuleVO rule) {
        drlBuffer.append("rule").append(" ").append("rule_").append(rule.getId()).append(System.lineSeparator());
        return drlBuffer;
    }

    private static StringBuffer insertExpiryDate(StringBuffer drlBuffer, RuleVO rule) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (rule.getBegin() != null) {
            String begin = dtf.format(rule.getBegin());
            drlBuffer.append("date-effective \"").append(begin).append("\"").append(System.lineSeparator());
        }
        if (rule.getEnd() != null) {
            String end = dtf.format(rule.getEnd());
            drlBuffer.append("date-expires \"").append(end).append("\"").append(System.lineSeparator());
        }
        return drlBuffer;
    }

    private static StringBuffer insertRuleCondition(StringBuffer drlBuffer, RuleVO rule) {
        drlBuffer.append("when").append(System.lineSeparator());
        // 拼接规则条件
        if (rule.getTriggers() == null || rule.getTriggers().isEmpty()) {
            drlBuffer.append("eval(true)").append(System.lineSeparator());
        } else {
            drlBuffer.append("$fact:Map( ");
            for (int i = 0; i < rule.getTriggers().size(); i++) {
                TriggerVO trigger = rule.getTriggers().get(i);
                // begin trigger
                drlBuffer.append("( ");
                for (int j = 0; j < trigger.getConditions().size(); j ++) {
                    ConditionVO condition = trigger.getConditions().get(j);
                    drlBuffer.append("this[\"").append(condition.getProperty()).append("\"]")
                            .append(condition.getOperator());
                    if (condition.getValue() instanceof String) {
                        // String
                        drlBuffer.append("\"").append(condition.getValue()).append("\"");
                    } else {
                        // Number
                        drlBuffer.append(condition.getValue());
                    }
                    if (j != trigger.getConditions().size() - 1) {
                        drlBuffer.append(" && ");
                    }
                }
                drlBuffer.append(" )");
                if (i != rule.getTriggers().size() - 1) {
                    drlBuffer.append(" || ");
                }
            }
            drlBuffer.append(" )").append(System.lineSeparator());
        }
        return drlBuffer;
    }

    private static StringBuffer insertRuleAction(StringBuffer drlBuffer, RuleVO rule) {
        drlBuffer.append("then").append(System.lineSeparator());
        List<ActionVO> actions = rule.getActions();
        if (actions != null && !actions.isEmpty()) {
            for (int i = 0; i < actions.size(); i++) {
                ActionVO action = actions.get(i);
                drlBuffer.append("Map params = new HashMap();").append(System.lineSeparator());
                // put ruleId
                drlBuffer.append("params.put(\"").append("ruleId").append("\", ");
                drlBuffer.append(rule.getId()).append(");").append(System.lineSeparator());
                // put ruleName
                drlBuffer.append("params.put(\"").append("ruleName").append("\", \"");
                drlBuffer.append(rule.getName()).append("\");").append(System.lineSeparator());
                // put other params
                for (Map.Entry<String, Object> param : action.getParams().entrySet()) {
                    drlBuffer.append("params.put(\"").append(param.getKey()).append("\", ");
                    if (param.getValue() instanceof String) {
                        // String
                        drlBuffer.append("\"");
                        drlBuffer.append(param.getValue());
                        drlBuffer.append("\"");
                    } else {
                        // Number
                        drlBuffer.append(param.getValue());
                    }
                    drlBuffer.append(");").append(System.lineSeparator());
                }
                drlBuffer.append("$action_").append(action.getName()).append("execute(");
                drlBuffer.append("$fact, params, $results);").append(System.lineSeparator());
            }
        }
        drlBuffer.append("end").append(System.lineSeparator());
        return drlBuffer;
    }

}
