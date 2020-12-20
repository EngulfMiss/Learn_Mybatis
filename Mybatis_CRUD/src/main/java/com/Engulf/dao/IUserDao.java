package com.Engulf.dao;

import com.Engulf.domain.QueryVo;
import com.Engulf.domain.User;
import com.Engulf.domain.User2;

import java.util.List;

/**
 * 用户的持久层接口
 */

public interface IUserDao {
    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll();

    /**
     * 查询所有用户(实体类和数据库属性名称不一致)
     * @return
     */
    List<User2> findAll2();

    /**
     * 保存账户的方法
     * @param user
     */
    void saveUser(User user);

    /**
     * 更新用户的方法
     * @param user
     */
    void updateUser(User user);

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteUser(Integer id);

    /**
     * 根据用户名查询用户
     * @param name
     * @return
     */
    User findByName(String name);

    /**
     * 名称的模糊查询
     * @param VagueName
     * @return
     */
    List<User> findByVagueName(String VagueName);

    /**
     * 查询总用户数
     * @return
     */
    int findTotal();


    /**
     * 通过查询类QueryVo查询用户
     * @param vo
     * @return
     */
    List<User> findUserByVo(QueryVo vo);
}
