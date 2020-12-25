package com.Engulf.dao;

import com.Engulf.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 在mybatis中针对，CRUD一共有四个注解
 * 分别是：
 * @Select
 * @Insert
 * @Update
 * @Delete
 */

@CacheNamespace(blocking = true)
public interface IUserDao {
    /**
     * 查询所有用户
     * @return
     */
    @Select("select * from user;")
    //id属性为唯一标识
    @Results(id = "userMap",value = {
            @Result(id = true,property = "userId",column = "id"),
            @Result(property = "userName",column = "username"),
            @Result(property = "userAddress",column = "address"),
            @Result(property = "userSex",column = "sex"),
            @Result(property = "userBirthday",column = "birthday"),
            @Result(property = "accounts",column = "id",
                    many = @Many(select = "com.Engulf.dao.IAccountDao.findAccountByUid"
                    ,fetchType = FetchType.LAZY))
    })
    List<User> findAll();


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{kindred}")
    @ResultMap(value={"userMap"})
    User findById(Integer id);


    /**
     * 模糊查询
     * @param vname
     * @return
     */
    @Select("select * from user where username like #{neeko}")
    @ResultMap(value={"userMap"})
    List<User> findUserByVagueName(String vname);
}
