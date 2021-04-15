package org.tze.deviceservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.tze.deviceservice.dao.DeviceDAO;
import org.tze.deviceservice.entity.Device;
import org.tze.deviceservice.service.DeviceService;

import java.util.List;

public class DeviceServiceImpl implements DeviceService {
    @Autowired
    DeviceDAO deviceDAO;

    @Override
    public Device createDevice(Device device) {
        try {
           return deviceDAO.save(device);
        }catch (Exception e) {
            throw new RuntimeException("注册失败:" + e.toString());
        }
    }

    @Override
    public List<Device> getDeviceListByProject(Long projectId) {
        try {
            List<Device> result=deviceDAO.getDeviceByProjectId(projectId);
            return result;
        }catch (Exception e) {
            throw new RuntimeException("获取失败:" + e.toString());
        }
    }

    @Override
    public List<Device> getDeviceListByProduct(Long productId) {
        try {
            List<Device> result=deviceDAO.getDeviceByProductId(productId);
            return result;
        }catch (Exception e) {
            throw new RuntimeException("获取失败:" + e.toString());
        }
    }

    @Override
    public List<Device> getDeviceListByUsername(String username) {
        try {
            List<Device> result=deviceDAO.getDeviceByUserName(username);
            return result;
        }catch (Exception e) {
            throw new RuntimeException("获取失败:" + e.toString());
        }
    }

    @Override
    public Device getSingleDevice(String deviceName) {
        try {
            return deviceDAO.getDeviceByDeviceName(deviceName);
        }catch (Exception e) {
            throw new RuntimeException("获取失败:" + e.toString());
        }
    }

    @Override
    public Device getSingleDevice(Long deviceId) {
        try {
            return deviceDAO.getOne(deviceId);
        }catch (Exception e) {
            throw new RuntimeException("获取失败:" + e.toString());
        }
    }

    @Override
    public boolean deleteDevice(Long id) {
        try {
            deviceDAO.delete(deviceDAO.getOne(id));
            return true;
        }catch (Exception e) {
            throw new RuntimeException("获取失败:" + e.toString());
        }
    }

    @Override
    public boolean updateDevice(Device device) {
        try {
            deviceDAO.saveAndFlush(device);
            return true;
        }catch (Exception e) {
            throw new RuntimeException("获取失败:" + e.toString());
        }
    }
}
