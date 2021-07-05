package com.engulf.dao;

import com.engulf.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    //插入数据
    int addBlog(Blog blog);

    //查询数据  if标签的使用
    List<Blog> selectBlogIf(Map map);

    //查询数据  choose标签使用
    List<Blog> selectBlogChoose(Map map);

    //更新数据  set标签使用
    int updateBlog(Blog blog);

    //查询数据  foreach标签使用
    List<Blog> selectBlogForeach(Map map);
}
