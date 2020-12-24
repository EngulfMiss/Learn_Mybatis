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
import java.util.List;

public class FirstLevelTest {
    private InputStream in = null;
    private SqlSession sqlSession = null;
    private IUserDao UserDao = null;
    private SqlSessionFactory factory = null;

    @Before
    public void init() throws Exception{
        //1.读取配置文件，生成字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //3.获取SqlSessionFactory对象
        factory = builder.build(in);
        //4.获取SqlSession对象
        sqlSession = factory.openSession();
        //5.获取dao的代理对象
        UserDao = sqlSession.getMapper(IUserDao.class);
    }


    @After
    public void destroy() throws Exception{
        //提交事务
        sqlSession.commit();
        //7.释放资源
        sqlSession.close();
        in.close();
    }


    /*
    一级缓存：
        它指的是Mybatis中SqlSession对象的缓存
        一级缓存在SqlSession对象中
        如果SqlSession被销毁，缓存也就被销毁
     */
    @Test
    public void testFirstCache(){
        User user1 = UserDao.findByName("Gnar");
        System.out.println(user1);

//        sqlSession.close();
        sqlSession.clearCache();//此方法也可以清空缓存

        //再次创建SqlSession对象
        sqlSession = factory.openSession();

        UserDao = sqlSession.getMapper(IUserDao.class);

        User user2 = UserDao.findByName("Gnar");
        System.out.println(user2);
        System.out.println(user1 == user2);
    }


    /**
     * 测试缓存的同步
     */
    @Test
    public void testClearCache(){
        //1.根据名称查询用户
        User user1 = UserDao.findById(48);
        System.out.println(user1);

        //2.更新用户信息
        //当调用SqlSession的修改，添加，删除，commit()，close()等方法时，就会清空一级缓存
        user1.setUsername("Gnardada");
        user1.setAddress("联盟大陆");
        UserDao.updateUser(user1);

        User user2 = UserDao.findById(48);
        System.out.println(user2);
        System.out.println(user1 == user2);
    }

}
