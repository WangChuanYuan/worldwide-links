package org.tze.userservice.service;

import org.tze.userservice.entity.User;

public interface UserService {
    boolean userLogin(String userId,String password);
    User registerUser(String userId,String password);
    User getSingleUser(String userId);
}
