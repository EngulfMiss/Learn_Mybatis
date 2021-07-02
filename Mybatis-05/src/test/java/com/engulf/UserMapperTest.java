package com.engulf;

import com.engulf.dao.ChampionMapper;
import com.engulf.dao.MasterMapper;
import com.engulf.pojo.Master;
import com.engulf.pojo.MyChampion;
import com.engulf.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserMapperTest {

    //多对一查询测试
    @Test
    public void test2(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        ChampionMapper mapper = sqlSession.getMapper(ChampionMapper.class);
        List<MyChampion> myChampions = mapper.selectMyChampion();
        for (MyChampion myChampion : myChampions) {
            System.out.println(myChampion);
        }
        sqlSession.close();
    }

    @Test
    public void test3(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        ChampionMapper mapper = sqlSession.getMapper(ChampionMapper.class);
        List<MyChampion> myChampions = mapper.selectMyChampion2();
        for (MyChampion myChampion : myChampions) {
            System.out.println(myChampion);
        }
        sqlSession.close();
    }

    //一对多查询测试
    @Test
    public void test4(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        MasterMapper mapper = sqlSession.getMapper(MasterMapper.class);
        Master master = mapper.selectMaster(1);
        System.out.println(master);
        sqlSession.close();
    }

    //一对多查询测试
    @Test
    public void test5(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        MasterMapper mapper = sqlSession.getMapper(MasterMapper.class);
        Master master = mapper.selectMaster2(1);
        System.out.println(master);
        sqlSession.close();
    }
}
