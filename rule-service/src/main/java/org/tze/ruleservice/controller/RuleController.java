package org.tze.ruleservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tze.ruleservice.service.RuleService;
import org.tze.ruleservice.vo.RuleVO;

import java.util.List;
import java.util.Map;

@RestController
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @PostMapping("/{projectId}/rules")
    public RuleVO create(@PathVariable Long projectId, @RequestBody RuleVO rule) {
        return ruleService.create(rule);
    }

    @GetMapping("/{projectId}/rules/{ruleId}")
    public RuleVO getRule(@PathVariable Long projectId, @PathVariable Long ruleId) {
        return ruleService.getRule(ruleId);
    }

    @GetMapping("/{projectId}/rules")
    public List<RuleVO> getRules(@PathVariable Long projectId) {
        return ruleService.getRules(projectId);
    }

    @PutMapping("/{projectId}/rules")
    public RuleVO update(@PathVariable Long projectId, @RequestBody RuleVO rule) {
        return ruleService.update(rule);
    }

    @DeleteMapping("/{projectId}/rules/{ruleId}")
    public boolean delete(@PathVariable Long projectId, @PathVariable Long ruleId) {
        return ruleService.delete(ruleId);
    }

    @PostMapping("/{projectId}/rules/_execute")
    public void execute(@PathVariable Long projectId, @RequestBody Map params) {
        ruleService.execute(projectId, params);
    }

}
