package com.Engulf.dao;

import com.Engulf.domain.Account;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface IAccountDao {
    /**
     * 查询所有账户，并且获取每个账户所属的用户信息
     * @return
     */
    @Select("select * from account")
    @Results(id = "accountMap",value={
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "uid",column = "uid"),
            @Result(property = "money",column = "money"),
            @Result(property = "user",column = "uid",one = @One (select = "com.Engulf.dao.IUserDao.findById",fetchType = FetchType.EAGER))
    })
    List<Account> findAll();


    /**
     * 根据用户id查询账户信息
     * @param id
     * @return
     */
    @Select("select * from account where uid = #{id}")
    List<Account> findAccountByUid(Integer id);
}
