package org.tze.deviceservice.service;

import org.tze.deviceservice.entity.ModelEvent;
import org.tze.deviceservice.entity.ModelServe;

import java.util.List;

public interface ModelEventService {
    ModelEvent createModelEvent(ModelEvent modelEvent);
    boolean deleteModelEvent(Long modelEventId);
    boolean modifyModelEvent(ModelEvent modelEvent);
    List<ModelEvent> getAll();
}
