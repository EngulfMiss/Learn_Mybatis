import com.engulf.dao.BlogMapper;
import com.engulf.pojo.Blog;
import com.engulf.utils.IDUtils;
import com.engulf.utils.MybatisUtil;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest {
    @Test
    public void addInitBlog(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        Blog blog = new Blog();

        blog.setId(IDUtils.getUUID());
        blog.setTitle("Mybatis动态sql");
        blog.setAuthor("Engulf迷失");
        blog.setCreateTime(new Date());
        blog.setViews(666);
        mapper.addBlog(blog);

        blog.setId(IDUtils.getUUID());
        blog.setTitle("我爱Java");
        mapper.addBlog(blog);

        blog.setId(IDUtils.getUUID());
        blog.setTitle("SpringBoot永远的神");
        mapper.addBlog(blog);

        sqlSession.close();
    }

    @Test
    public void testIf(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map map = new HashMap();
//        map.put("title","我爱Java");
//        map.put("author","Engulf迷失");
        List<Blog> blogs = mapper.selectBlogIf(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }

    @Test
    public void testChoose(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map map = new HashMap();
        map.put("title","我爱Java");
//        map.put("author","Engulf迷失");
        map.put("views",666);
        List<Blog> blogs = mapper.selectBlogChoose(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }

    @Test
    public void testSet(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = new Blog();

        blog.setId("566bad70-443d-4164-a651-28cf739f6db1");
//        blog.setTitle("Mybatis动态sql");
        blog.setAuthor("WildWolf");
        mapper.updateBlog(blog);

        sqlSession.close();
    }

    @Test
    public void testForeach(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map map = new HashMap();
        String[] authors = {"MildLamb","WildWolf"};
        map.put("Authors",authors);
        List<Blog> blogs = mapper.selectBlogForeach(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
}
