package org.tze.ruleservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.tze.ruleservice.po.LogPO;

public interface LogDAO extends JpaRepository<LogPO, Long>, JpaSpecificationExecutor<LogPO> {
}
