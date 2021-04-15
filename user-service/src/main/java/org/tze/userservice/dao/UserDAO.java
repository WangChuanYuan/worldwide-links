package org.tze.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tze.userservice.entity.User;

/**
 * @Author: WangMo
 * @Description:
 */

public interface UserDAO extends JpaRepository<User,String> {
}
