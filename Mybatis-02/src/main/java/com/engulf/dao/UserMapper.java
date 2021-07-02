package com.engulf.dao;

import com.engulf.pojo.Champion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    public List<Champion> selectChampions();

    public Champion selectById(@Param("id") Integer id);

    public int addChampion(Champion champion);

    public void updateChampion(Champion champion);

    public void delChampion(@Param("id") int id);
}
