package com.engulf.dao;

import com.engulf.pojo.Champion;
import com.engulf.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {
    @Test
    //查询全部
    public void test(){

        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtil.getSqlSession();
            UserDao dao = sqlSession.getMapper(UserDao.class);
            List<Champion> champions = dao.selectChampions();
            for (Champion champion : champions) {
                System.out.println(champion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
    }

    @Test
    //查询一个
    public void test2(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        Champion champion = mapper.selectById(1);
        System.out.println(champion);
        sqlSession.close();
    }

    @Test
    //添加
    //增删改需要提交事务
    public void test3(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        Champion champion = new Champion(7,"泰斯特",2);
        int i = mapper.addChampion(champion);
        if (i != 0){
            System.out.println("添加成功");
            sqlSession.commit();
        }
        sqlSession.close();
    }

    @Test
    //修改
    public void test4(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        Champion champion = mapper.selectById(3);
        champion.setLocal_id(3);
        mapper.updateChampion(champion);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test5(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<Champion> champions = mapper.selectLike("%n%");
        for (Champion champion : champions) {
            System.out.println(champion);
        }
    }
}
