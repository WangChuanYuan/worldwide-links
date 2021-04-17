package org.tze.connectservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tze.connectservice.server.adapter.MqttMsgBack;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WangMo
 * @Description:
 */

@RestController
public class Connectcontroller {

    @RequestMapping(value = "/connect/getOnlineDevice",method = RequestMethod.GET)
    public List<Long> getOnlineDevice(){
        List<Long> list=new ArrayList<>();
        for(Long id:MqttMsgBack.connectChannel.values()){
            list.add(id);
        }
        return list;
    }
}
