package org.tze.deviceservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tze.deviceservice.VO.ModelServeVO;
import org.tze.deviceservice.entity.ModelPro;
import org.tze.deviceservice.entity.ModelServe;
import org.tze.deviceservice.entity.Product;
import org.tze.deviceservice.service.DeviceService;
import org.tze.deviceservice.service.ModelProService;
import org.tze.deviceservice.service.ModelServeService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ModelController {
    @Autowired
    ModelProService modelProService;
    @Autowired
    ModelServeService modelServeService;

    @RequestMapping(value = "/modelPro/create",method = RequestMethod.POST)
    public ModelPro createModelPro(@RequestBody ModelPro modelPro){
        System.out.println("接口调用Pro"+modelPro.toString());
        ModelPro re=modelProService.createModelPro(modelPro);
        return re;
    }
    @RequestMapping(value = "/modelPro/delete",method = RequestMethod.DELETE)
    public boolean deleteModelPro(@RequestParam("modelServeId")Long modelProId){
        return modelProService.deleteModelPro(modelProId);
    }

    @RequestMapping(value = "/modelPro/getAll",method = RequestMethod.GET)
    public List<ModelPro> getModelProList( ){
        return modelProService.getAll();
    }

    @RequestMapping(value = "/modelPro/updateModelPro",method = RequestMethod.PUT)
    public boolean updateModelPro(@RequestBody ModelPro modelPro){
        return modelProService.modifyModelPro(modelPro);
    }



    @RequestMapping(value = "/modelServe/create",method = RequestMethod.POST)
    public ModelServeVO createModelServe(@RequestBody ModelServeVO modelServeVO){
        System.out.println("接口调用Ser"+modelServeVO.toString());
        ModelServe re=modelServeService.createModelServe(trasnferModelServeVO(modelServeVO));
        return transferModelServe(re);
    }
    @RequestMapping(value = "/modelServe/delete",method = RequestMethod.DELETE)
    public boolean deleteModelServe(@RequestParam("modelServeId")Long modelServe){
        return modelServeService.deleteModelServe(modelServe);
    }

    @RequestMapping(value = "/modelServe/getAll",method = RequestMethod.GET)
    public List<ModelServeVO> getModelServeList( ){
        List<ModelServeVO> result=new ArrayList<>();
        for(ModelServe modelServe:modelServeService.getAll())
            result.add(transferModelServe(modelServe));
        return result;
    }

    @RequestMapping(value = "/modelServe/updateModelServe",method = RequestMethod.PUT)
    public boolean updateModelServe(@RequestBody ModelServeVO modelServeVO){
        return modelServeService.modifyModelSerce(trasnferModelServeVO(modelServeVO));
    }
    private ModelServeVO transferModelServe(ModelServe modelServe){
        ModelServeVO modelServeVO=new ModelServeVO();
        modelServeVO.setDescription(modelServe.getDescription());
        modelServeVO.setId(modelServe.getId());
        modelServeVO.setIdentifier(modelServe.getIdentifier());
        modelServeVO.setName(modelServe.getName());
        List<ModelPro> modelPros=new ArrayList<>();
        modelPros= JSONObject.parseArray(modelServe.getParams(),ModelPro.class);
        modelServeVO.setParams(modelPros);
        return modelServeVO;
    }

    private ModelServe trasnferModelServeVO(ModelServeVO modelServeVO){
        ModelServe modelServe=new ModelServe();
        modelServe.setDescription(modelServeVO.getDescription());
        modelServe.setIdentifier(modelServeVO.getIdentifier());
        modelServe.setName(modelServeVO.getName());
        modelServe.setParams(JSON.toJSONString(modelServeVO.getParams()));
        return modelServe;
    }
}
