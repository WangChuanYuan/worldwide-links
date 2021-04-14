package org.tze.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tze.userservice.service.UserService;

/**
 * @Author: WangMo
 * @Description:
 */

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public boolean userLogin(@RequestParam("userId")String userID,@RequestParam("password")String password){
        return userService.userLogin(userID,password);
    }

    @RequestMapping(value = "/user/regiser",method = RequestMethod.POST)
    public boolean userRegiser(@RequestParam("userId")String userID,@RequestParam("password")String password){
        if(userService.registerUser(userID,password)!=null)
            return true;
        else
            return false;
    }
}
