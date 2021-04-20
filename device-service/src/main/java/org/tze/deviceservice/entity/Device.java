package org.tze.deviceservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Device {

    private static final long serialVersionUID = 1L;

    private Long projectId;

    private Long productId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
