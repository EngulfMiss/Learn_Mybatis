package com.Engulf;

import com.Engulf.dao.IUserDao;
import com.Engulf.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

public class SecondLevelCacheTest {
    private InputStream in = null;
    private SqlSessionFactory factory = null;


    @Before
    public void init() throws Exception{
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
    }

    @After
    public void destroy() throws Exception{
        in.close();
    }

    @Test
    public void testFindById(){
        SqlSession sqlSession = factory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = userDao.findById(50);
        System.out.println(user);
        sqlSession.close();  //释放一级缓存

        SqlSession sqlSession2 = factory.openSession();  //再次打开SqlSession
        IUserDao userDao2 = sqlSession2.getMapper(IUserDao.class);
        User user2 = userDao2.findById(50);
        System.out.println(user2);
        sqlSession2.close();

    }
}
