package org.tze.connectservice.feign.feignEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: WangMo
 * @Description:
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    private Long projectId;
    private Long productId;
    private Long deviceId;
    private String deviceName;
    private String logo;
    private String location;
    private String model;
    private String industry;
    private String sn;
    private Date lastActive;
    private String deviceState;
    private Integer isEnable;
    private String username;
    private String clientId;
    private String password;
    private int isSuperuser;
    private String token;
    private String description;
    private String modelServe;
    private String modelPro;
}
