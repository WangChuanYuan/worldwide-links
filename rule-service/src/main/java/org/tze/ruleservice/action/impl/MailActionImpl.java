package org.tze.ruleservice.action.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tze.ruleservice.action.RuleAction;

import java.util.List;
import java.util.Map;

@Service("mailAction")
public class MailActionImpl implements RuleAction {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getName() {
        return "mailAction";
    }

    @Override
    public void execute(Map<String, Object> fact, Map<String, Object> params, List<Map<String, Object>> results) {
        String url = String.format("http://%s/notice", "mail-service");
        params.putAll(fact);
        JSONObject paramsJson = JSONObject.parseObject(JSON.toJSONString(params));
        restTemplate.postForObject(url, paramsJson, String.class);
    }
}
