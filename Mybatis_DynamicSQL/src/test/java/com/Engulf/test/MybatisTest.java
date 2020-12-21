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
import java.util.ArrayList;
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


    /**
     * 测试动态sql
     * where ， if标签使用
     */
    @Test
    public void testFindByCondition(){
        /**
         * 测试查询所有
         */
        User u = new User();
        u.setUsername("Gnar");
        u.setSex("雄");
        //6.执行查询所有方法
        List<User> users = UserDao.findUserByCondition(u);
        for (User user:users){
            System.out.println(user);
        }
    }


    /**
     * 测试动态sql
     * foreach标签使用
     */
    @Test
    public void testFindInIds(){
        /**
         * 测试查询所有
         */
        QueryVo vo = new QueryVo();
        List<Integer> list = new ArrayList<>();
        list.add(48);
        list.add(50);
        list.add(53);
        vo.setNums(list);
        //6.执行查询所有方法
        List<User> users = UserDao.findUserInIds(vo);
        for (User user:users){
            System.out.println(user);
        }
    }


}
