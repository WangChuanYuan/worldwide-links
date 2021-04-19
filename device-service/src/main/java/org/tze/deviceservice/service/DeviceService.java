package org.tze.deviceservice.service;

import org.tze.deviceservice.entity.Device;
import org.tze.deviceservice.entity.Product;

import java.util.List;

public interface DeviceService {
    Device createDevice(Device device);
    List<Device> getDeviceListByProject(Long projectId);
    List<Device> getDeviceListByProduct(Long productId);
    List<Device> getDeviceListByUsername(String username);
    List<Device> getDeviceListByState(int state);
    List<Device> getDeviceAll();
    Device getSingleDevice(String deviceName);
    Device getSingleDevice(Long deviceId);
    Device deviceLogin(Long deviceId,String password);
    boolean deleteDevice (Long id);
    boolean updateDevice (Device device);
}
