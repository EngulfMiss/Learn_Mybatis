package com.Engulf;

import com.Engulf.dao.IUserDao;
import com.Engulf.domain.User;
import com.sun.security.jgss.GSSUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class AnnoCRUDTest {
    private InputStream in = null;
    private SqlSessionFactory factory = null;
    private SqlSession sqlSession = null;
    private IUserDao userDao = null;

    @Before
    public void init() throws Exception{
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After
    public void destroy() throws Exception{
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }


    /**
     * 查询所有用户(附带账户信息)
     */
    @Test
    public void testFindAll(){
        List<User> users = userDao.findAll();
        for(User user : users){
            System.out.println("-------每个用户信息---------");
            System.out.println(user);
//            System.out.println(user.getAccounts());
        }
    }


    /**
     * 根据id查询用户
     */
    @Test
    public void findByIdTest(){
        User user = userDao.findById(50);
        System.out.println(user);
    }

    /**
     * 模糊查询
     */
    @Test
    public void findByVagueName(){
        List<User> users = userDao.findUserByVagueName("%王%");
        for(User user : users){
            System.out.println(user);
        }
    }
}
