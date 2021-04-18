package org.tze.deviceservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tze.deviceservice.dao.ModelProDAO;
import org.tze.deviceservice.dao.ModelServeDAO;
import org.tze.deviceservice.entity.ModelServe;
import org.tze.deviceservice.service.ModelServeService;

import java.util.List;
@Service
public class ModelServeServiceImpl implements ModelServeService {
    @Autowired
    private ModelServeDAO modelServeDAO;

    @Override
    public ModelServe createModelServe(ModelServe modelServe) {
        try {
            return modelServeDAO.save(modelServe);
        } catch (Exception e) {
            throw new RuntimeException("注册失败:" + e.toString());
        }
    }

    @Override
    public boolean deleteModelServe(Long modelSerceId) {
        try{
            modelServeDAO.deleteById(modelSerceId);
            return true;
        }catch (Exception e) {
            throw new RuntimeException("删除失败:" + e.toString());
        }
    }

    @Override
    public boolean modifyModelSerce(ModelServe modelServe) {
        try {
            modelServeDAO.saveAndFlush(modelServe);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("注册失败:" + e.toString());
        }
    }

    @Override
    public List<ModelServe> getAll() {
        try {
            return modelServeDAO.findAll();
        }catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }
}
