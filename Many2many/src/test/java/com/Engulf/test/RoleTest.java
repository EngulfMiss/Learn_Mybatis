package com.Engulf.test;

import com.Engulf.dao.IRoleDao;
import com.Engulf.domain.Role;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class RoleTest {
    private InputStream in = null;
    private SqlSession sqlSession = null;
    private IRoleDao RoleDao = null;

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
        RoleDao = sqlSession.getMapper(IRoleDao.class);
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
     * 测试查询所有角色
     */
    @Test
    public void testFindAllRoles(){
        List<Role> roles = RoleDao.findAll();
        for(Role role:roles){
            System.out.println("-------角色信息--------");
            System.out.println(role);
            System.out.println(role.getUsers());
        }
    }


}
