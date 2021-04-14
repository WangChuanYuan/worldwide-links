package org.tze.ruleservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tze.ruleservice.po.RulePO;

import java.util.List;

public interface RuleDAO extends JpaRepository<RulePO, Long> {

    List<RulePO> getByProjectId(Long projectId);
}
