package com.Engulf.dao;

import com.Engulf.domain.User;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有的操作
     * @return
     */
    public abstract List<User> findAll();
}
