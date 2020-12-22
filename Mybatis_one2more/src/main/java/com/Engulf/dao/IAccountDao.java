package com.Engulf.dao;

import com.Engulf.domain.Account;
import com.Engulf.domain.AccountUser;

import java.util.List;

public interface IAccountDao {
    /**
     * 查询所有账户,同时获取到当前账户的所属用户信息
     * @return
     */
    List<Account> findAll();

    /**
     * 查询所有账户 ，并且带有用户名称和地址信息
     * @return
     */
    List<AccountUser> findAllAccount();
}
