package org.tze.deviceservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tze.deviceservice.entity.Device;

import java.math.BigInteger;
import java.util.List;

public interface DeviceDAO extends JpaRepository<Device,Long> {
    List<Device> getDeviceByProductId (Long productId);
    List<Device> getDeviceByProjectId (Long projectId);
    List<Device> getDeviceByUserName (String username);
    Device getDeviceByDeviceName (String deviceName);
}
