package org.tze.deviceservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tze.deviceservice.dao.ModelProDAO;
import org.tze.deviceservice.dao.ProductDAO;
import org.tze.deviceservice.entity.ModelEvent;
import org.tze.deviceservice.entity.ModelPro;
import org.tze.deviceservice.service.ModelProService;

import java.util.List;

@Service
public class ModelProServiceImpl implements ModelProService {

    @Autowired
    private ModelProDAO modelProDAO;

    @Override
    public ModelPro createModelPro(ModelPro modelEvent) {
        try {
            return modelProDAO.save(modelEvent);
        } catch (Exception e) {
            throw new RuntimeException("注册失败:" + e.toString());
        }
    }

    @Override
    public boolean deleteModelPro(Long modelProId) {
        try{
            modelProDAO.deleteById(modelProId);
            return true;
        }catch (Exception e) {
            throw new RuntimeException("删除失败:" + e.toString());
        }
    }

    @Override
    public boolean modifyModelPro(ModelPro modelPro) {
        try {
            modelProDAO.saveAndFlush(modelPro);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("注册失败:" + e.toString());
        }
    }

    @Override
    public List<ModelPro> getAll() {
        try {
            return modelProDAO.findAll();
        }catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }
}
