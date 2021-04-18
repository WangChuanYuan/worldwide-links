package org.tze.deviceservice.service;

import org.tze.deviceservice.entity.ModelEvent;
import org.tze.deviceservice.entity.ModelPro;

import java.util.List;

public interface ModelProService {
    ModelPro createModelPro(ModelPro modelPro);
    boolean deleteModelPro(Long modelProId);
    boolean modifyModelPro(ModelPro modelPro);
    List<ModelPro> getAll();
}
