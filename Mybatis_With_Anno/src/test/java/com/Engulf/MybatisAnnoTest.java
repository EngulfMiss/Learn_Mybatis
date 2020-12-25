package com.Engulf;

import com.Engulf.dao.IUserDao;
import com.Engulf.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class MybatisAnnoTest {
    /**
     * 测试基于注解的Mybatis使用
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //1.获取字节输入流
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.根据字节输入流创建SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.根据SqlSessionFactory生成SqlSession
        SqlSession sqlSession = factory.openSession();
        //4.根据SqlSession创建业务层接口的代理对象
        IUserDao UserDao = sqlSession.getMapper(IUserDao.class);
        //5.执行dao方法
        List<User> users = UserDao.findAll();
        for(User user:users){
            System.out.println(user);
        }
        //6.释放资源
        sqlSession.close();
        in.close();
    }
}
