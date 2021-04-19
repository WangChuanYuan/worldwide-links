package org.tze.deviceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tze.deviceservice.entity.Device;

import java.math.BigInteger;
import java.util.List;

public interface DeviceDAO extends JpaRepository<Device,Long> {
    List<Device> getDeviceByProductId (Long productId);
    List<Device> getDeviceByProjectId (Long projectId);
    List<Device> getDeviceByUsername(String username);
    List<Device> getDeviceByDeviceState(String deviceState);
    Device getDeviceByDeviceName (String deviceName);
    Device getDeviceByDeviceIdAndPassword (Long deviceId,String password);
}
