package org.tze.ruleservice.util;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

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

    public KieSession getDrlSessionInCache(Long projectId) {
        InternalKnowledgeBase kbase = ruleCache.get(projectId);
        if (kbase == null) {
            return null;
        } else {
            return kbase.newKieSession();
        }
    }

    public KieSession getDrlSession(Long projectId, String rule) {
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

    public boolean compileRule(String rule) {
        final KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        Reader strReader = new StringReader(rule);
        kbuilder.add(ResourceFactory.newReaderResource(strReader), ResourceType.DRL);

        if (kbuilder.hasErrors()) {
            System.out.println(kbuilder.getErrors().toString());
            return false;
        }
        return true;
    }
}
