package com.Engulf;

import com.Engulf.dao.IAccountDao;
import com.Engulf.dao.IUserDao;
import com.Engulf.domain.Account;
import com.Engulf.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class AccountTest {
    private InputStream in = null;
    private SqlSessionFactory factory = null;
    private SqlSession sqlSession = null;
    private IAccountDao accountDao = null;

    @Before
    public void init() throws Exception{
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        accountDao = sqlSession.getMapper(IAccountDao.class);
    }

    @After
    public void destroy() throws Exception{
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }


    /**
     * 查询所有用户
     */
    @Test
    public void testFindAll(){
        List<Account> accounts = accountDao.findAll();
        for(Account account : accounts){
            System.out.println("--------账户信息----------");
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }
}
