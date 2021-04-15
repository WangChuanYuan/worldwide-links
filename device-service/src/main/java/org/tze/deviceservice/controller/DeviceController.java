package org.tze.deviceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tze.deviceservice.entity.Device;
import org.tze.deviceservice.entity.Product;
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
        Device result=deviceService.createDevice(device);
        return device;
    }

    @RequestMapping(value = "/device/update",method = RequestMethod.POST)
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

    @RequestMapping(value = "/device/getDeviceByProject",method = RequestMethod.GET)
    public List<Device> getByProject(@RequestParam("projectId") Long projectId){
        return deviceService.getDeviceListByProject(projectId);
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
