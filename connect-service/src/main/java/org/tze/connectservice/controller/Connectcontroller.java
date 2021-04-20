package org.tze.connectservice.controller;

import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.springframework.web.bind.annotation.*;
import org.tze.connectservice.feign.feignEntity.Device;
import org.tze.connectservice.server.adapter.MqttMsgBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public String sendMsg(@RequestBody Map msg){
        System.out.println(msg.get("topicName"));
        MqttMsgBack.serverSendMsg2Clinet((String)msg.get("topicName"),(String)msg.get("msg"),-1);
        return "success";
    }

    @RequestMapping(value = "/connect/disconnect",method = RequestMethod.POST)
    public boolean disconnect(@RequestParam("deviceId") Long deviceId){

        return MqttMsgBack.disconnect(deviceId);
    }
}
