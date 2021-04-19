package org.tze.connectservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tze.connectservice.feign.feignEntity.Device;
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
    public List<Device> getOnlineDevice(){
        List<Device> list=new ArrayList<>();
        for(Device device:MqttMsgBack.connectChannel.values()){
            list.add(device);
        }
        return list;
        
    }

    @RequestMapping(value = "/connect/sendMsg",method = RequestMethod.POST)
    public void sendMsg(@RequestParam("topicName") String topicName,@RequestParam("msg")String msg){
        MqttMsgBack.serverSendMsg2Clinet(topicName,msg,-1);
    }
}
