package com.Engulf.dao;

import com.Engulf.domain.Account;

import java.util.List;

public interface IAccountDao {
    /**
     * 查询所有账户,同时获取到当前账户的所属用户信息
     * @return
     */
    List<Account> findAll();

    /**
     * 根据用户id查询账户信息
     * @param uid
     * @return
     */
    List<Account> findAccountByUid(Integer uid);
}
