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

/**
 * 测试mybatis的crud操作
 */

public class MybatisTest {

    private InputStream in = null;
    private SqlSession sqlSession = null;
    private IUserDao UserDao = null;

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


    @Test
    public void testFindAll(){
        /**
         * 测试查询所有
         */

        //6.执行查询所有方法
        List<User> users = UserDao.findAll();
        for (User user:users){
            System.out.println(user);
        }
    }





    /**
     * 测试查询一个的方法
     */
    @Test
    public void testfindByName(){
        //6.执行查询一个方法
        User user = UserDao.findByName("Neeko");
        System.out.println(user);
    }

}
