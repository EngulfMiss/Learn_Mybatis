package com.engulf.dao;

import com.engulf.page.PageMapper;
import com.engulf.pojo.Champion;
import com.engulf.utils.MybatisUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class UserDaoTest {

    static Logger logger = Logger.getLogger(UserDaoTest.class);

    @Test
    //查询全部
    public void test(){

        SqlSession sqlSession = null;
            sqlSession = MybatisUtil.getSqlSession();
            UserMapper dao = sqlSession.getMapper(UserMapper.class);
            List<Champion> champions = dao.selectChampions();
            for (Champion champion : champions) {
                System.out.println(champion);
            }
            sqlSession.close();
    }

    @Test
    public void testLog4j(){
        logger.info("info:进入了testLog4j方法");
        logger.debug("debug:进入了testLog4j的debug方法");
        logger.error("error:进入了testLog4j的error方法");
    }

    @Test
    public void testLimit(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        PageMapper mapper = sqlSession.getMapper(PageMapper.class);
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("pageIndex",0);
        map.put("pageSize",2);
        List<Champion> championLimit = mapper.getChampionLimit(map);
        for (Champion champion : championLimit) {
            System.out.println(champion);
        }
        sqlSession.close();
    }

    @Test
    public void getChampionRowBounds(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();

        //RowBounds实现
        RowBounds rowBounds = new RowBounds(0, 2);


        //通过java代码层面实现分页
        List<Champion> selectList = sqlSession.selectList("com.engulf.page.PageMapper.getChampionByRowBounds",null,rowBounds);
        for (Champion champion : selectList) {
            System.out.println(champion);
        }
        sqlSession.close();
    }
}
