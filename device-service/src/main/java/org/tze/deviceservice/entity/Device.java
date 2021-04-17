package org.tze.deviceservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Device {

    private static final long serialVersionUID = 1L;

    /**
     * 项目
     */
    private Long projectId;

    /**
     * 产品簇
     */
    private Long productId;

    /**
     * 设备
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;

    /**
     * 名称
     */
    private String deviceName;

    /**
     * Logo
     */
    private String logo;

    /**
     * 地理位置
     */
    private String location;

    /**
     * 型号
     */
    private String model;

    /**
     * 厂家
     */
    private String industry;

    /**
     * 序列号
     */
    private String sn;
    /**
     *
     */
    private Date lastActive;

    /**
     * 1:上线;2:线线;3未激活
     */
    private Integer state;

    /**
     * 1:启用;2:禁用
     */
    private Integer isEnable;

    /**
     * MQTT用户名
     */
    private String username;

    /**
     * MQTT ClientID
     */
    private String clientId;

    /**
     * MQTT密码
     */
    private String password;

    /**
     * 是否超级权限
     */
    private int isSuperuser;

    /**
     * 认证token
     */
    private String token;
//
//    /**
//     * 参数
//     */
//    @TableField(typeHandler = JacksonTypeHandler.class)
//    private List<FieldParam> fieldParams;

    /**
     * 描述
     */
    private String description;
    /**
     *
     */
//    @TableField(exist = false)
//    private String project;
//    @TableField(exist = false)
//    private String product;
}
