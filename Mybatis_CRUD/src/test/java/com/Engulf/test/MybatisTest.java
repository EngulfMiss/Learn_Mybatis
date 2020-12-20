package com.Engulf.test;

import com.Engulf.dao.IUserDao;
import com.Engulf.domain.QueryVo;
import com.Engulf.domain.User;
import com.Engulf.domain.User2;
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

    @Test
    public void testFindAll2(){
        /**
         * 测试查询所有
         */

        //6.执行查询所有方法
        List<User2> users = UserDao.findAll2();
        for (User2 user:users){
            System.out.println(user);
        }
    }



    /**
     * 测试保存操作
     */
    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("QSJ");
        user.setAddress("地球");
        user.setSex("男");
        user.setBirthday(new Date());
        System.out.println("保存操作前的user："+ user);
        //6.执行保存方法
        UserDao.saveUser(user);

        System.out.println("保存操作后的user："+ user);
    }


    /**
     * 测试更新操作
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(48);
        user.setUsername("Neeko");
        user.setAddress("不知道");
        user.setSex("雌");
        user.setBirthday(new Date());
        //6.执行更新方法
        UserDao.updateUser(user);

    }


    /**
     * 测试删除方法
     */
    @Test
    public void testDelete(){
        //6.执行删除方法
        UserDao.deleteUser(54);
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


    /**
     * 测试模糊查询的方法
     */
    @Test
    public void testfindByVagueName(){
        //6.执行模糊查询方法,方式一
        //执行语句为 Preparing: select * from user where username like ?
        List<User> users = UserDao.findByVagueName("%e%");
        //6.执行模糊查询方法,方式二
        //执行语句为 Preparing: select * from user where username like '%e%'
//        List<User> users = UserDao.findByVagueName("e");
        for(User user : users){
            System.out.println(user);
        }
    }

    /**
     * 测试查询总记录数的方法
     */
    @Test
    public void testfindTotal(){
        System.out.println("记录总数为:" + UserDao.findTotal());
    }



    /**
     * 测试模糊查询的方法
     */
    @Test
    public void testfindByVo(){
        QueryVo queryVo = new QueryVo();
        User user = new User();
        user.setUsername("%e%");
        queryVo.setUser(user);
        List<User> users = UserDao.findUserByVo(queryVo);
        for(User u : users){
            System.out.println(u);
        }
    }




}
