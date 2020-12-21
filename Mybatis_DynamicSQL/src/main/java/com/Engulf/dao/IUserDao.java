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
     * 通过查询类QueryVo查询用户
     * @param vo
     * @return
     */
    List<User> findUserByVo(QueryVo vo);

    /**
     * 根据传入的参数条件查询
     * @param user 查询的条件，有可能有用户名，可能有性别，可能有地址，可能都有，也可能什么都没有
     * @return
     */
    List<User> findUserByCondition(User user);

    /**
     * 通过查询类QueryVo提供的id集合，查询用户信息
     * @param vo
     * @return
     */
    List<User> findUserInIds(QueryVo vo);
}
