package com.Engulf.test;

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
        //1.读取配置文件，生成字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //3.获取SqlSessionFactory对象
        factory = builder.build(in);
    }


    @After
    public void destroy() throws Exception{
        in.close();
    }


    /*
    二级缓存:
        它指的是Mybatis中SqlSessionFactory对象的缓存。由同一个SqlSessionFactory对象创建的
        SqlSession共享其缓存。二级缓存存放的是数据而不是对象

        二级缓存的使用步骤：
            1.让Mybatis框架支持二级缓存(在核心配置文件SqlMapConfig.xml中配置)
            2.让当前的映射文件支持二级缓存(在IUserDao.xml中配置)
            3.让当前的操作支持二级缓存(在select标签中配置)
     */
    @Test
    public void testSecondCache(){
        SqlSession sqlSession = factory.openSession();
        IUserDao dao1 = sqlSession.getMapper(IUserDao.class);
        User user1 = dao1.findById(48);
        System.out.println(user1);
        sqlSession.close();//一级缓存消除

        SqlSession sqlSession2 = factory.openSession();
        IUserDao dao2 = sqlSession2.getMapper(IUserDao.class);
        User user2 = dao2.findById(48);
        System.out.println(user2);
        sqlSession2.close();

        System.out.println(user1 == user2);
    }

}
