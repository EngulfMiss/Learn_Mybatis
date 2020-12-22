package com.Engulf.test;

import com.Engulf.dao.IAccountDao;
import com.Engulf.domain.Account;
import com.Engulf.domain.AccountUser;
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
    private SqlSession sqlSession = null;
    private IAccountDao accountDao = null;

    @Before
    public void init() throws Exception{
        //1.读取配置文件，生成字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //3.获取SqlSessionFactory对象
        SqlSessionFactory factory = builder.build(in);
        //4.获取SqlSession对象
        sqlSession = factory.openSession();
        //5.获取dao的代理对象
        accountDao = sqlSession.getMapper(IAccountDao.class);
    }


    @After
    public void destroy() throws Exception{
        //提交事务
        sqlSession.commit();
        //7.释放资源
        sqlSession.close();
        in.close();
    }

    /**
     * 测试查询所有账户
     */
    @Test
    public void testFindAllAccount(){
        List<Account> accounts = accountDao.findAll();
        for(Account account:accounts){
            System.out.println("--------------------------------");
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }


    /**
     * 测试查询所有账户,同时包含用户名称和地址
     */
    @Test
    public void testFindAllAccounWithUserInf(){
        List<AccountUser> aus = accountDao.findAllAccount();
        for(AccountUser au:aus){
            System.out.println(au);
        }
    }
}
