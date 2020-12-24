package com.Engulf.dao;

import com.Engulf.domain.User;

import java.util.List;

/**
 * 用户的持久层接口
 */

public interface IUserDao {
    /**
     * 查询所有用户，同时获取用户下所有账户的信息
     * @return
     */
    List<User> findAll();

    /**
     * 根据用户名查询用户
     * @param name
     * @return
     */
    User findByName(String name);


    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User findById(Integer id);

}
