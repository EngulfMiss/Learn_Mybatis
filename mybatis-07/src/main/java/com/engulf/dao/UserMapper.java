package com.engulf.dao;

import com.engulf.pojo.Champion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper{
    Champion selectChampionById(@Param("id") Integer id);

    int updateChampion(Map map);
}
