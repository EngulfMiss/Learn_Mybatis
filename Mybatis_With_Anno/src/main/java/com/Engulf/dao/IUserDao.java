package com.Engulf.dao;

import com.Engulf.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 在mybatis中针对，CRUD一共有四个注解
 * 分别是：
 * @Select
 * @Insert
 * @Update
 * @Delete
 */

public interface IUserDao {
    /**
     * 查询所有用户
     * @return
     */
    @Select("select * from user;")
    List<User> findAll();

    /**
     * 保存用户
     */
    @Insert("insert into user(username,address,sex,birthday) values(#{username},#{address},#{sex},#{birthday})")
    void saveUser(User user);

    /**
     * 更新用户
     * @param user
     */
    @Update("update user set username=#{username},address=#{address},sex=#{sex},birthday=#{birthday} where id = #{id}")
    void updateUser(User user);


    /**
     * 删除用户
     * @param id
     */
    @Delete("delete from user where id = #{gnar}")
    void deleteUser(Integer id);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{kindred}")
    User findById(Integer id);


    /**
     * 模糊查询
     * @param vname
     * @return
     */
    @Select("select * from user where username like #{neeko}")
    List<User> findUserByVagueName(String vname);


    /**
     * 查询总用户数量
     * @return
     */
    @Select("select count(*) from user")
    int findTotal();
}
