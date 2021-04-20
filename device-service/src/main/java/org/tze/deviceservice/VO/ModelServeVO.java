package org.tze.deviceservice.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tze.deviceservice.entity.ModelPro;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelServeVO {
    private Long id;
    private String identifier;
    private String name;
    private List<ModelPro> params;//参数
    private String description;
}
