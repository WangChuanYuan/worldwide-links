package org.tze.deviceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tze.deviceservice.entity.Device;
import org.tze.deviceservice.service.DeviceService;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @RequestMapping(value = "/device/create/{projectId}",method = RequestMethod.POST)
    public Device createDevice(@RequestBody Device device,@PathVariable Long projectId){
        System.out.println("接口调用"+device.toString());
        device.setProductId(projectId);
        device.setProjectId(Long.valueOf(1));
        device.setProductId(Long.valueOf(1));
        Device result=deviceService.createDevice(device);
        return device;
    }

    @RequestMapping(value = "/device/create",method = RequestMethod.PUT)
    public Device updateDevice(@RequestBody Device device){
        System.out.println("接口调用"+device.toString());
        boolean result=deviceService.updateDevice(device);
        return device;
    }

    @RequestMapping(value = "/device/login",method = RequestMethod.GET)
    public Device deviceLogin(@RequestParam("deviceId")Long deviceId,@RequestParam("devicePw")String devicePw){
        return deviceService.deviceLogin(deviceId,devicePw);
    }

    @RequestMapping(value = "/device/delete/{deviceId}",method = RequestMethod.DELETE)
    public boolean deleteDevice(@PathVariable("deviceId")Long deviceId){
        System.out.println("删除"+deviceId);
        return deviceService.deleteDevice(deviceId);
    }


    @RequestMapping(value = "/device/onlineDevice/{deviceId}",method = RequestMethod.GET)
    public boolean onlineDevice(@PathVariable("deviceId") Long deviceId){
        System.out.println("onlineDevice");
        System.out.println(deviceId);
        Device device=deviceService.getSingleDevice(deviceId);
        device.setDeviceState("上线");
        return deviceService.updateDevice(device);
    }

    @RequestMapping(value = "/device/offlineDevice/{deviceId}",method = RequestMethod.GET)
    public boolean offlineDevice(@PathVariable("deviceId")Long deviceId){
        System.out.println("offlineDevice");
        System.out.println(deviceId);
        Device device=deviceService.getSingleDevice(deviceId);
        device.setDeviceState("下线");
        return deviceService.updateDevice(device);
    }

    @RequestMapping(value = "/device/startDevice/{deviceId}",method = RequestMethod.GET)
    public boolean startDevice(@PathVariable("deviceId")Long deviceId){
        System.out.println("startDevice");
        Device device=deviceService.getSingleDevice(deviceId);
        device.setIsEnable(1);
        return deviceService.updateDevice(device);
    }

    @RequestMapping(value = "/device/endDevice/{deviceId}",method = RequestMethod.GET)
    public boolean endDevice(@PathVariable("deviceId")Long deviceId){
        System.out.println("endDevice");
        Device device=deviceService.getSingleDevice(deviceId);
        device.setIsEnable(2);
        device.setDeviceState("禁用");
        return deviceService.updateDevice(device);
    }

    @RequestMapping(value = "/device/getOnlineDevice",method = RequestMethod.GET)
    public List<Device> getOnlineDevice(){
        System.out.println("getOnlineDevice");
        return deviceService.getDeviceListByState("上线");
    }

    @RequestMapping(value = "/device/getDeviceByProject",method = RequestMethod.GET)
    public List<Device> getByProject(@RequestParam("projectId") Long projectId){
        return deviceService.getDeviceListByProject(projectId);
    }

    @RequestMapping(value = "/device/getAll",method = RequestMethod.GET)
    public List<Device> getAll(){
        return deviceService.getDeviceAll();
    }

    @RequestMapping(value = "/device/getDeviceByProduct/{productId}",method = RequestMethod.GET)
    public List<Device> getByProduct(@PathParam("productId") Long productId){
        return deviceService.getDeviceListByProduct(productId);
    }

    @RequestMapping(value = "/device/getDeviceById/{deviceId}",method = RequestMethod.GET)
    public Device getSingleDeviceById(@PathParam("deviceId")Long deviceId){
        return deviceService.getSingleDevice(deviceId);
    }

    @RequestMapping(value = "/device/getDeviceByName/{deviceName}",method = RequestMethod.GET)
    public Device getSingleDeviceByName(@PathParam("deviceName")String deviceName){
        return deviceService.getSingleDevice(deviceName);
    }
}
