package org.tze.deviceservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tze.deviceservice.entity.ModelPro;
import org.tze.deviceservice.entity.ModelServe;
import org.tze.deviceservice.entity.Product;
import org.tze.deviceservice.service.DeviceService;
import org.tze.deviceservice.service.ModelProService;
import org.tze.deviceservice.service.ModelServeService;

import java.util.List;

@RestController
public class ModelController {
    @Autowired
    ModelProService modelProService;
    @Autowired
    ModelServeService modelServeService;

    @RequestMapping(value = "/modelPro/create",method = RequestMethod.POST)
    public ModelPro createModelPro(@RequestBody ModelPro modelPro){
        System.out.println("接口调用"+modelPro.toString());
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
    public ModelServe createModelServe(@RequestBody ModelServe modelServe){
        System.out.println("接口调用"+modelServe.toString());
        ModelServe re=modelServeService.createModelServe(modelServe);
        return re;
    }
    @RequestMapping(value = "/modelServe/delete",method = RequestMethod.DELETE)
    public boolean deleteModelServe(@RequestParam("modelServeId")Long modelServe){
        return modelServeService.deleteModelServe(modelServe);
    }

    @RequestMapping(value = "/modelServe/getAll",method = RequestMethod.GET)
    public List<ModelServe> getModelServeList( ){
        return modelServeService.getAll();
    }

    @RequestMapping(value = "/modelServe/updateModelServe",method = RequestMethod.PUT)
    public boolean updateModelServe(@RequestBody ModelServe modelPro){
        return modelServeService.modifyModelSerce(modelPro);
    }
}
