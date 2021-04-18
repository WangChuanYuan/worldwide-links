package org.tze.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tze.userservice.dao.UserDAO;
import org.tze.userservice.entity.User;
import org.tze.userservice.service.UserService;

/**
 * @Author: WangMo
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public boolean userLogin(String userId, String password) {
        if (userDAO.existsById(userId))
            return password.equals(userDAO.findById(userId).get().getPassword());
        else
            return false;
    }

    @Override
    public User registerUser(String userId, String password) {
        if (userDAO.existsById(userId))
            return null;
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        return userDAO.saveAndFlush(user);
    }

    @Override
    public User getSingleUser(String userId) {
        if (userDAO.existsById(userId))
            return userDAO.findById(userId).get();
        return null;
    }
}
