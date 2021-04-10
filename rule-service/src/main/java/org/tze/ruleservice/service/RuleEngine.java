package org.tze.ruleservice.service;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.tze.ruleservice.action.RuleAction;
import org.tze.ruleservice.po.RulePO;
import org.tze.ruleservice.util.DrlUtil;
import org.tze.ruleservice.util.DroolsUtil;
import org.tze.ruleservice.util.PackageScanner;
import org.tze.ruleservice.util.SpringContextUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuleEngine {

    @Autowired
    private RuleDAO ruleDAO;

    private static List<RuleAction> ruleActions = new ArrayList<>();

    static{
        System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm");

        PackageScanner scan = new PackageScanner("org.tze.ruleservice.action.impl");
        try {
            List<String> actionClazz  = scan.getFullyQualifiedClassNameList();
            for (String clazz : actionClazz) {
                ruleActions.add(SpringContextUtil.getBeanByClazzAsConvention(clazz));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> executeRuleEngine(Long projectId, Map<String, Object> fact) {

        KieSession ksession = DroolsUtil.getInstance().getDrlSessionInCache(projectId);
        if (ksession != null) {
            return handleDrlSession(projectId, ksession, fact);
        } else {
            return handleInEngine(projectId, fact);
        }
    }

    private List<Map<String,Object>> handleInEngine(Long projectId, Map<String,Object> fact) {
        StringBuffer drlBuffer = new StringBuffer("package org.tze.ruleservice.dsl;").append(System.lineSeparator());
        drlBuffer.append("global java.util.List $result;").append(System.lineSeparator());

        // 查询项目已有的规则
        List<RulePO> rules = ruleDAO.getRulesByproject(projectId);

        // import语句
        DrlUtil.insertImportDrl(drlBuffer);
        // 规则语句
        for(RulePO rule : rules){
            if (!rule.isEnabled()) continue;
            drlBuffer.append(rule.getDrl());
        }
        // 初始化drools
        KieSession session = DroolsUtil.getInstance().getDrlSession(projectId, drlBuffer.toString());
        return handleDrlSession(projectId, session, fact);
    }

    private List<Map<String, Object>> handleDrlSession(Long projectId, KieSession session, Map<String, Object> fact) {
        // 加载事实对象
        session.insert(fact);
        // 加载全局变量，用于返回结果
        List<Map<String, Object>> result = new ArrayList<>();
        session.setGlobal("$result", result);
        // 加载动作对象
        for (RuleAction action : ruleActions)
            session.insert(action);

        session.fireAllRules();
        session.dispose();
        return result;
    }

}
