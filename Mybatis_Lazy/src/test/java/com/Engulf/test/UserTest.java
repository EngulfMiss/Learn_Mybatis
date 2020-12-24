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

public class UserTest {
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

    /**
     * 测试查询所有账户(包含延迟加载技术)
     */
    @Test
    public void testFindAll(){
        List<User> users = UserDao.findAll();
//        for(User user:users){
//            System.out.println("----------每个用户的信息-----------");
//            System.out.println(user);
//            System.out.println(user.getAccounts());
//        }
    }

}
