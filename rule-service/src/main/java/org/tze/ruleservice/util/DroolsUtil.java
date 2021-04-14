package org.tze.ruleservice.util;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.tze.ruleservice.vo.RuleVO;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DroolsUtil {

    private static Map<Long, InternalKnowledgeBase> ruleCache = new ConcurrentHashMap<>();

    private static volatile DroolsUtil instance;

    private DroolsUtil() {
    }

    public static DroolsUtil getInstance() {
        if (instance == null) {
            synchronized (DroolsUtil.class) {
                if (instance == null) {
                    instance = new DroolsUtil();
                }
            }
        }
        return instance;
    }

    public KieSession getDrlSession(Long projectId) {
        InternalKnowledgeBase kbase = ruleCache.get(projectId);
        if (kbase == null) {
            return null;
        } else {
            return kbase.newKieSession();
        }
    }

    public KieSession newDrlSession(Long projectId, String rule) {
        System.out.println(rule);
        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        Reader strReader = new StringReader(rule);
        kbuilder.add(ResourceFactory.newReaderResource(strReader), ResourceType.DRL);

        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error : errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Unable to compile drool rules.");
        }

        Collection<KiePackage> pkgs = kbuilder.getKnowledgePackages();
        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addPackages(pkgs);
        ruleCache.put(projectId, kbase);
        return kbase.newKieSession();
    }

    public void addNewRule(Long projectId, RuleVO rule) {
        try {
            InternalKnowledgeBase kbase = ruleCache.get(projectId);
            if (kbase == null) return;

            StringBuffer drlBuffer = new StringBuffer();
            DrlUtil.insertPackageAndGlobal(drlBuffer);
            DrlUtil.insertImport(drlBuffer);
            DrlUtil.insertRuleDrl(drlBuffer, rule);

            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
            Resource resource = ResourceFactory.newReaderResource(new StringReader(drlBuffer.toString()));
            kbuilder.add(resource, ResourceType.DRL);
            if (kbuilder.hasErrors()) {
                System.err.println(kbuilder.getErrors());
            }
            kbase.addPackages(kbuilder.getKnowledgePackages());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRule(Long projectId, Long ruleId) {
        InternalKnowledgeBase kbase = ruleCache.get(projectId);
        if (kbase == null) return;

        String packageName = "org.tze.ruleservice.dsl";
        String ruleName = "rule_" + ruleId;
        Rule rule = kbase.getRule(packageName, ruleName);
        if (rule != null) {
            kbase.removeRule(packageName, ruleName);
        }
    }

    public void updateRule(Long projectId, RuleVO ruleVO) {
        InternalKnowledgeBase kbase = ruleCache.get(projectId);
        if (kbase == null) return;

        String packageName = "org.tze.ruleservice.dsl";
        String ruleName = "rule_" + ruleVO.getId();
        Rule rule = kbase.getRule(packageName, ruleName);
        if (rule != null) {
            kbase.removeRule(packageName, ruleName);
            addNewRule(projectId, ruleVO);
        }
    }
}
