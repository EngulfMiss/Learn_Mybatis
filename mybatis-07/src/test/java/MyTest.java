import com.engulf.dao.UserMapper;
import com.engulf.pojo.Champion;
import com.engulf.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MyTest {
    @Test
    //一级缓存测试
    public void test(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Champion champion = mapper.selectChampionById(1);
        System.out.println(champion);
        Map map = new HashMap();
        /*map.put("local_id",1);
        map.put("id",3);
        mapper.updateChampion(map);*/
        //手动清理缓存
//        sqlSession.clearCache();
        System.out.println("================");
        Champion champion1 = mapper.selectChampionById(1);
        System.out.println(champion1);
        System.out.println(champion == champion1);
        sqlSession.close();
    }

    //二级缓存测试
    @Test
    public void test2(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        SqlSession sqlSession2 = MybatisUtil.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);

        Champion champion = mapper.selectChampionById(1);
        System.out.println(champion);
        sqlSession.close();


        System.out.println("================");
        Champion champion1 = mapper2.selectChampionById(1);
        System.out.println(champion1);
        System.out.println(champion == champion1);
        sqlSession2.close();
    }
}
