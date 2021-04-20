package org.tze.ruleservice.action.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tze.ruleservice.action.RuleAction;

import java.util.List;
import java.util.Map;

@Service("ctrlAction")
public class CtrlActionImpl implements RuleAction {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getName() {
        return "ctrlAction";
    }

    @Override
    public void execute(Map<String, Object> fact, Map<String, Object> params, List<Map<String, Object>> results) {
        String url = String.format("http://%s/connect/sendMsg", "connect-service");

        String methodName = (String) params.get("method");
        params.remove("method");
        StringBuilder cmd = new StringBuilder(methodName);
        cmd.append(" ");
        for (Map.Entry<String, Object> args : params.entrySet()) {
            String key = args.getKey();
            if (key.equals("ruleId") || key.equals("ruleName")
                    || key.equals("projectId") || key.equals("productId") || key.equals("deviceId"))
                continue;
            cmd.append("--").append(key).append(" ").append(args.getValue()).append(" ");
        }

        JSONObject paramsJson = new JSONObject();
        paramsJson.put("topicName", String.valueOf((Integer) fact.get("deviceId")));
        paramsJson.put("msg", cmd.toString());
        restTemplate.postForObject(url, paramsJson, String.class);
    }
}
