package org.tze.deviceservice.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tze.deviceservice.entity.ModelPro;
import org.tze.deviceservice.entity.ModelServe;

import java.util.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
    private Long productId;

    private Long projectId;
    private String productName;
    private String description;
    private boolean enabled;
    private List<ModelServe> modelServe;
    private List<ModelPro> modelPro;

}
