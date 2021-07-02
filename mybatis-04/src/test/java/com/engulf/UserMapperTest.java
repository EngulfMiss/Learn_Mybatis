package com.engulf;

import com.engulf.dao.UserMapper;
import com.engulf.pojo.Champion;
import com.engulf.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserMapperTest {
    @Test
    public void testAnno(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<Champion> champions = mapper.getChampions();
        for (Champion champion : champions) {
            System.out.println(champion);
        }
        sqlSession.close();
    }

    @Test
    public void testAddAnno(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Champion champion = new Champion(8,"哈哈",1);
        mapper.addChampion(champion);
        sqlSession.close();
    }

    @Test
    public void testUpdateAnno(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Champion champion = new Champion(3,"KindRed",1);
        mapper.updateC(champion);
        sqlSession.close();
    }

    @Test
    public void testDelAnno(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Champion champion = new Champion(3,"KindRed",1);
        mapper.delC(8);
        sqlSession.close();
    }
}
