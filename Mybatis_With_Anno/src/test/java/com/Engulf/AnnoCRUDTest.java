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
     * 保存用户
     */
    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("Tian");
        user.setAddress("居无定所");
        userDao.saveUser(user);
    }

    /**
     * 更新用户
     */
    @Test
    public void updateUser(){
        User user = new User();
        user.setId(58);
        user.setUsername("Tian");
        user.setAddress("居无定所");
        user.setSex("雌");
        user.setBirthday(new Date());
        userDao.updateUser(user);
    }

    /**
     * 删除用户
     */
    @Test
    public void deleteTest(){
        userDao.deleteUser(57);
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

    /**
     * 查询总数测试
     */
    @Test
    public void findTotalTest(){
        System.out.println("查询总数为:" + userDao.findTotal());
    }
}
