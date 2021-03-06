package com.Engulf.dao;

import com.Engulf.domain.User;
import com.Engulf.mybatis.annotations.Select;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有的操作
     * @return
     */
    @Select("select * from user")
    public abstract List<User> findAll();
}
