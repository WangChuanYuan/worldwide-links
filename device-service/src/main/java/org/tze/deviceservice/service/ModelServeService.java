package org.tze.deviceservice.service;

import org.tze.deviceservice.entity.ModelServe;

import java.util.*;

public interface ModelServeService {
    ModelServe createModelServe(ModelServe modelServe);
    boolean deleteModelServe(Long modelSerceId);
    boolean modifyModelSerce(ModelServe modelServe);
    List<ModelServe> getAll();
}
