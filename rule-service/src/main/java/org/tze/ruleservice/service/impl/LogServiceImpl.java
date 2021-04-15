package org.tze.ruleservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.tze.ruleservice.dao.LogDAO;
import org.tze.ruleservice.po.LogPO;
import org.tze.ruleservice.service.LogService;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDAO logDAO;

    @Override
    public List<LogPO> getLogs(Long projectId, Long ruleId, Long productId, Long deviceId) {
        Specification<LogPO> specification = (Specification<LogPO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> conditions = new ArrayList<>();
            Predicate projectCond = null;
            if (null != projectId) {
                projectCond = criteriaBuilder.equal(root.get("projectId").as(Long.class), projectId);
                conditions.add(projectCond);
            }
            Predicate ruleCond = null;
            if (null != ruleId) {
                ruleCond = criteriaBuilder.equal(root.get("ruleId").as(Long.class), ruleId);
                conditions.add(ruleCond);
            }
            Predicate productCond = null;
            if (null != productId) {
                productCond = criteriaBuilder.equal(root.get("productId").as(Long.class), productId);
                conditions.add(productCond);
            }
            Predicate deviceCond = null;
            if (null != deviceId) {
                deviceCond = criteriaBuilder.equal(root.get("deviceId").as(Long.class), deviceId);
                conditions.add(deviceCond);
            }
            Predicate[] predicates = new Predicate[conditions.size()];
            return criteriaBuilder.and(conditions.toArray(predicates));
        };
        return logDAO.findAll(specification);
    }
}
