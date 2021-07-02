package com.engulf.page;

import com.engulf.pojo.Champion;

import java.util.List;
import java.util.Map;

public interface PageMapper {
    //根据id查询用户
    Champion getChampionById(Integer id);

    //分页
    List<Champion> getChampionLimit(Map<String,Integer> map);

    //使用RowBounds分页
    List<Champion> getChampionByRowBounds();
}
