package org.tze.ruleservice.service;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.tze.ruleservice.action.RuleAction;
import org.tze.ruleservice.dao.RuleDAO;
import org.tze.ruleservice.po.RulePO;
import org.tze.ruleservice.util.DrlUtil;
import org.tze.ruleservice.util.DroolsUtil;
import org.tze.ruleservice.util.PackageScanner;
import org.tze.ruleservice.util.SpringContextUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleEngine {

    @Autowired
    private RuleDAO ruleDAO;

    private static Map<String, RuleAction> ruleActions = new HashMap<>();

    static{
        System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm");

        PackageScanner scan = new PackageScanner("org.tze.ruleservice.action.impl");
        try {
            List<String> actionClazz  = scan.getFullyQualifiedClassNameList();
            for (String clazz : actionClazz) {
                String beanName = SpringContextUtil.getBeanNameOfClazzAsConvention(clazz);
                ruleActions.put(beanName, SpringContextUtil.getBean(beanName));
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
        StringBuffer drlBuffer = new StringBuffer();
        DrlUtil.insertPackageAndGlobal(drlBuffer);

        // 查询项目已有的规则
        List<RulePO> rules = ruleDAO.getByProjectId(projectId);

        // import语句
        DrlUtil.insertImport(drlBuffer);
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
        // 加载全局变量
        // 返回结果
        List<Map<String, Object>> results = new ArrayList<>();
        session.setGlobal("$results", results);
        // 行为服务
        for (RuleAction action : ruleActions.values()) {
            String actionName = action.getName();
            session.setGlobal("$action_" + actionName, action);
        }

        session.fireAllRules();
        session.dispose();
        return results;
    }

}
