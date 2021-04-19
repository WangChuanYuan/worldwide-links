package org.tze.deviceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tze.deviceservice.entity.Device;
import org.tze.deviceservice.service.DeviceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @RequestMapping(value = "/device/create",method = RequestMethod.POST)
    public Device createDevice(@RequestBody Device device){
        System.out.println("接口调用"+device.toString());
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

    @RequestMapping(value = "/device/delete",method = RequestMethod.DELETE)
    public boolean deleteDevice(@RequestParam("deviceId")Long deviceId){
        return deviceService.deleteDevice(deviceId);
    }


    @RequestMapping(value = "/device/onlineDevice/{deviceId}",method = RequestMethod.PUT)
    public boolean onlineDevice(@RequestParam("deviceId")Long deviceId){
        Device device=deviceService.getSingleDevice(deviceId);
        device.setState(1);
        return deviceService.updateDevice(device);
    }

    @RequestMapping(value = "/device/offlineDevice",method = RequestMethod.PUT)
    public boolean offlineDevice(@RequestParam("deviceId")Long deviceId){
        Device device=deviceService.getSingleDevice(deviceId);
        device.setState(2);
        return deviceService.updateDevice(device);
    }

    @RequestMapping(value = "/device/startDevice",method = RequestMethod.PUT)
    public boolean startDevice(@RequestParam("deviceId")Long deviceId){
        Device device=deviceService.getSingleDevice(deviceId);
        device.setIsEnable(1);
        return deviceService.updateDevice(device);
    }

    @RequestMapping(value = "/device/endDevice",method = RequestMethod.PUT)
    public boolean endDevice(@RequestParam("deviceId")Long deviceId){
        Device device=deviceService.getSingleDevice(deviceId);
        device.setIsEnable(2);
        return deviceService.updateDevice(device);
    }

    @RequestMapping(value = "/device/getOnlineDevice",method = RequestMethod.GET)
    public List<Device> getOnlineDevice(){
        return deviceService.getDeviceListByState(1);
    }

    @RequestMapping(value = "/device/getDeviceByProject",method = RequestMethod.GET)
    public List<Device> getByProject(@RequestParam("projectId") Long projectId){
        return deviceService.getDeviceListByProject(projectId);
    }

    @RequestMapping(value = "/device/getAllDevice",method = RequestMethod.GET)
    public List<Device> getAll(@RequestParam Long projectId){
        return deviceService.getDeviceAll();
    }

    @RequestMapping(value = "/device/getDeviceByProduct",method = RequestMethod.GET)
    public List<Device> getByProduct(@RequestParam("productId") Long productId){
        return deviceService.getDeviceListByProduct(productId);
    }

    @RequestMapping(value = "/device/getDeviceById",method = RequestMethod.GET)
    public Device getSingleDeviceById(@RequestParam("deviceId")Long deviceId){
        return deviceService.getSingleDevice(deviceId);
    }

    @RequestMapping(value = "/device/getDeviceByName",method = RequestMethod.GET)
    public Device getSingleDeviceByName(@RequestParam("deviceName")String deviceName){
        return deviceService.getSingleDevice(deviceName);
    }
}
