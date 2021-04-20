package org.tze.connectservice.server.adapter;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tze.connectservice.feign.feignEntity.Device;
import org.tze.connectservice.feign.feignService.DeviceFeignService;
import org.tze.connectservice.feign.feignService.RuleFeignService;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author: WangMo
 * @Description: 对MQTT客户端发送消息后，处理的返回消息，基于MQTT协议的，需要MQTT协议的主要内容
 */


public class MqttMsgBack {

    private static Logger log =  LoggerFactory.getLogger(MqttMsgBack.class);

    static ConcurrentHashMap<String,Set<Channel>> chm=new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Channel,Device> connectChannel=new ConcurrentHashMap<>();
    static ConcurrentHashMap<Long,Channel> deviceIdMap=new ConcurrentHashMap<>();

    @Autowired
    DeviceFeignService deviceFeignService;

    @Autowired
    RuleFeignService ruleFeignService;
    private static MqttMsgBack mqttMsgBack;

    @PostConstruct
    public void init(){
        mqttMsgBack=this;
        mqttMsgBack.deviceFeignService =this.deviceFeignService;
    }


    /**
     * 	确认连接请求
     * @param channel
     * @param mqttMessage
     */
    public static void connack (Channel channel, MqttMessage mqttMessage) {
        MqttConnectMessage mqttConnectMessage = (MqttConnectMessage) mqttMessage;

        MqttFixedHeader mqttFixedHeaderInfo = mqttConnectMessage.fixedHeader();
        MqttConnectVariableHeader mqttConnectVariableHeaderInfo = mqttConnectMessage.variableHeader();

        //密码验证
        String userName=mqttConnectMessage.payload().userName();
        String password=mqttConnectMessage.payload().password();
        System.out.println("username: "+userName);

/*        Device device=mqttMsgBack.deviceFeignService.deviceLogin(Long.parseLong(userName),password);
        if(device==null){
            System.out.println("Device Login Failed!");
            return;
        }*/

        Device device=new Device();
        device.setDeviceId(1L);

        connectChannel.put(channel,device);
        deviceIdMap.put(device.getDeviceId(),channel);
        //	构建返回报文， 可变报头
        MqttConnAckVariableHeader mqttConnAckVariableHeaderBack = new MqttConnAckVariableHeader(MqttConnectReturnCode.CONNECTION_ACCEPTED, mqttConnectVariableHeaderInfo.isCleanSession());
        //	构建返回报文， 固定报头
        MqttFixedHeader mqttFixedHeaderBack = new MqttFixedHeader(MqttMessageType.CONNACK,mqttFixedHeaderInfo.isDup(), MqttQoS.AT_MOST_ONCE, mqttFixedHeaderInfo.isRetain(), 0x02);
        //	构建CONNACK消息体
        MqttConnAckMessage connAck = new MqttConnAckMessage(mqttFixedHeaderBack, mqttConnAckVariableHeaderBack);
        log.info("back--"+connAck.toString());
        channel.writeAndFlush(connAck);
    }

    /**
     * 	根据qos发布确认
     * @param channel
     * @param mqttMessage
     */
    public static void puback (Channel channel, MqttMessage mqttMessage) {
        MqttPublishMessage mqttPublishMessage = (MqttPublishMessage) mqttMessage;
        MqttFixedHeader mqttFixedHeaderInfo = mqttPublishMessage.fixedHeader();
        MqttQoS qos = (MqttQoS) mqttFixedHeaderInfo.qosLevel();
        byte[] headBytes = new byte[mqttPublishMessage.payload().readableBytes()];
        mqttPublishMessage.payload().readBytes(headBytes);

        String topicName=mqttPublishMessage.variableHeader().topicName();
        int msgID=mqttPublishMessage.variableHeader().messageId();
        System.out.println("TopicName:"+topicName);
        String data = new String(headBytes);
        System.out.println("publish data--"+data);

        //发消息给iot平台
        String[] msg=data.split(",");
        boolean isFormat=true;
        Map<String,Object> m=new HashMap<>();
        m.put("productId",connectChannel.get(channel).getProductId());
        m.put("deviceId",connectChannel.get(channel).getDeviceId());
        for (String s:msg){
            String[] detail=s.split("=");
            if(detail.length!=2) {
                isFormat=false;
                break;
            }
            try {
                m.put(detail[0],Double.parseDouble(detail[1]));
            }catch (Exception e){
                isFormat=false;
                break;
            }
        }
        if(isFormat){
            mqttMsgBack.ruleFeignService.execute(connectChannel.get(channel).getProjectId(),m);
        }

        serverSendMsg2Clinet(topicName,data,msgID);

        switch (qos) {
            case AT_MOST_ONCE: 		//	至多一次
                break;
            case AT_LEAST_ONCE:		//	至少一次
                //	构建返回报文， 可变报头
                MqttMessageIdVariableHeader mqttMessageIdVariableHeaderBack = MqttMessageIdVariableHeader.from(mqttPublishMessage.variableHeader().packetId());
                //	构建返回报文， 固定报头
                MqttFixedHeader mqttFixedHeaderBack = new MqttFixedHeader(MqttMessageType.PUBACK,mqttFixedHeaderInfo.isDup(), MqttQoS.AT_MOST_ONCE, mqttFixedHeaderInfo.isRetain(), 0x02);
                //	构建PUBACK消息体
                MqttPubAckMessage pubAck = new MqttPubAckMessage(mqttFixedHeaderBack, mqttMessageIdVariableHeaderBack);
                log.info("back--"+pubAck.toString());
                channel.writeAndFlush(pubAck);
                break;
            case EXACTLY_ONCE:		//	刚好一次
                //	构建返回报文， 固定报头
                MqttFixedHeader mqttFixedHeaderBack2 = new MqttFixedHeader(MqttMessageType.PUBREC,false, MqttQoS.AT_LEAST_ONCE,false,0x02);
                //	构建返回报文， 可变报头
                MqttMessageIdVariableHeader mqttMessageIdVariableHeaderBack2 = MqttMessageIdVariableHeader.from(mqttPublishMessage.variableHeader().packetId());
                MqttMessage mqttMessageBack = new MqttMessage(mqttFixedHeaderBack2,mqttMessageIdVariableHeaderBack2);
                log.info("back--"+mqttMessageBack.toString());
                channel.writeAndFlush(mqttMessageBack);
                break;
            default:
                break;
        }
    }

    /**
     * 	发布完成 qos2
     * @param channel
     * @param mqttMessage
     */
    public static void pubcomp (Channel channel, MqttMessage mqttMessage) {
        MqttMessageIdVariableHeader messageIdVariableHeader = (MqttMessageIdVariableHeader) mqttMessage.variableHeader();
        //	构建返回报文， 固定报头
        MqttFixedHeader mqttFixedHeaderBack = new MqttFixedHeader(MqttMessageType.PUBCOMP,false, MqttQoS.AT_MOST_ONCE,false,0x02);
        //	构建返回报文， 可变报头
        MqttMessageIdVariableHeader mqttMessageIdVariableHeaderBack = MqttMessageIdVariableHeader.from(messageIdVariableHeader.messageId());
        MqttMessage mqttMessageBack = new MqttMessage(mqttFixedHeaderBack,mqttMessageIdVariableHeaderBack);
        log.info("back--"+mqttMessageBack.toString());
        channel.writeAndFlush(mqttMessageBack);
    }

    /**
     * 	订阅确认
     * @param channel
     * @param mqttMessage
     */
    public static void suback(Channel channel, MqttMessage mqttMessage) {
        MqttSubscribeMessage mqttSubscribeMessage = (MqttSubscribeMessage) mqttMessage;
        MqttMessageIdVariableHeader messageIdVariableHeader = mqttSubscribeMessage.variableHeader();


        //	构建返回报文， 可变报头
        MqttMessageIdVariableHeader variableHeaderBack = MqttMessageIdVariableHeader.from(messageIdVariableHeader.messageId());
        Set<String> topics = mqttSubscribeMessage.payload().topicSubscriptions().stream().map(mqttTopicSubscription -> mqttTopicSubscription.topicName()).collect(Collectors.toSet());
        for(String s:topics){
            if(chm.containsKey(s)){
                chm.get(s).add(channel);
            }
            else {
                Set<Channel> set=new HashSet<>();
                set.add(channel);
                chm.put(s,set);
            }
            System.out.println("订阅："+s);
        }

        //log.info(topics.toString());
        List<Integer> grantedQoSLevels = new ArrayList<>(topics.size());
        for (int i = 0; i < topics.size(); i++) {
            grantedQoSLevels.add(mqttSubscribeMessage.payload().topicSubscriptions().get(i).qualityOfService().value());
        }
        //	构建返回报文	有效负载
        MqttSubAckPayload payloadBack = new MqttSubAckPayload(grantedQoSLevels);
        //	构建返回报文	固定报头
        MqttFixedHeader mqttFixedHeaderBack = new MqttFixedHeader(MqttMessageType.SUBACK, false, MqttQoS.AT_MOST_ONCE, false, 2+topics.size());
        //	构建返回报文	订阅确认
        MqttSubAckMessage subAck = new MqttSubAckMessage(mqttFixedHeaderBack,variableHeaderBack, payloadBack);
        log.info("back--"+subAck.toString());
        channel.writeAndFlush(subAck);
    }

    /**
     * 	取消订阅确认
     * @param channel
     * @param mqttMessage
     */
    public static void unsuback(Channel channel, MqttMessage mqttMessage) {
        MqttMessageIdVariableHeader messageIdVariableHeader = (MqttMessageIdVariableHeader) mqttMessage.variableHeader();
        //	构建返回报文	可变报头
        MqttMessageIdVariableHeader variableHeaderBack = MqttMessageIdVariableHeader.from(messageIdVariableHeader.messageId());
        //	构建返回报文	固定报头
        MqttFixedHeader mqttFixedHeaderBack = new MqttFixedHeader(MqttMessageType.UNSUBACK, false, MqttQoS.AT_MOST_ONCE, false, 2);
        //	构建返回报文	取消订阅确认
        MqttUnsubAckMessage unSubAck = new MqttUnsubAckMessage(mqttFixedHeaderBack,variableHeaderBack);
        MqttUnsubscribeMessage mqttUnsubscribeMessage=(MqttUnsubscribeMessage)mqttMessage;
        //Set<String> topics = mqttUnsubscribeMessage.payload().topics().stream().map(mqttTopicSubscription -> mqttTopicSubscription.topicName()).collect(Collectors.toSet());
/*        for(String s:topics){
            if(chm.containsKey(s)){
                chm.get(s).add(channel);
            }
            else {
                Set<Channel> set=new HashSet<>();
                set.add(channel);
                chm.put(s,set);
            }
            System.out.println("订阅："+s);
        }*/
        log.info("back--"+unSubAck.toString());
        channel.writeAndFlush(unSubAck);
    }

    /**
     * 	心跳响应
     * @param channel
     * @param mqttMessage
     */
    public static void pingresp (Channel channel, MqttMessage mqttMessage) {
        //	心跳响应报文	11010000 00000000  固定报文
        MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.PINGRESP, false, MqttQoS.AT_MOST_ONCE, false, 0);
        MqttMessage mqttMessageBack = new MqttMessage(fixedHeader);
        log.info("back--"+mqttMessageBack.toString());
        channel.writeAndFlush(mqttMessageBack);
    }


    /**
     * 客户端断开连接
     * @param channel
     * @param mqttMessage
     */
    public static void disconnect(Channel channel,MqttMessage mqttMessage){
        connectChannel.remove(channel);
    }

    /**
     *
     * @param channel
     * @param topic
     * @param msgID
     * @param data
     */

    public static void sendMsg2Client(Channel channel,String topic,int msgID,String data){
        if(channel!=null){
            MqttFixedHeader Header = new MqttFixedHeader(
                    MqttMessageType.PUBLISH, true,
                    MqttQoS.AT_MOST_ONCE, false, 0);
            MqttPublishVariableHeader publishVariableHeader =
                    new MqttPublishVariableHeader(
                            topic,
                            msgID);


            MqttPublishMessage publishMessage = new MqttPublishMessage(
                    Header, publishVariableHeader,
                    Unpooled.copiedBuffer(data.getBytes()));

            try {
                System.out.println(publishMessage.toString());
                channel.writeAndFlush(publishMessage);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            System.out.println("对" + channel.id() + "发送了 id:"+msgID+"消息："
                    + data);
        }
    }

    public static void serverSendMsg2Clinet(String topic,String msg,int msgID){
        if(chm.containsKey(topic)){
            System.out.println("sendTopicName: "+topic);
            for (Channel channel1:chm.get(topic)) {
                System.out.println(channel1.id());
                sendMsg2Client(channel1, topic, msgID, msg);
            }
        }
    }

    public static boolean disconnect(Long deviceId){
        Channel channel=deviceIdMap.get(deviceId);
        deviceIdMap.remove(deviceId);
        if(channel!=null){
            System.out.println("断开连接："+deviceId);
            MqttFixedHeader Header = new MqttFixedHeader(
                    MqttMessageType.DISCONNECT, true,
                    MqttQoS.AT_MOST_ONCE, false, 0);


            MqttMessage disconnectMessage = new MqttMessage(
                    Header);

            try {
                System.out.println(disconnectMessage.toString());
                channel.writeAndFlush(disconnectMessage);
            }
            catch (Exception e){
                return false;
            }finally {
                connectChannel.remove(channel);
                channel.close();
            }

            return true;
        }


        return true;
    }
}

