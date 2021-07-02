package com.engulf.dao;

import com.engulf.pojo.MyChampion;

import java.util.List;

public interface ChampionMapper {

    //查询所有英雄对应的信息以及对应召唤师的信息
    public List<MyChampion> selectMyChampion();

    public List<MyChampion> selectMyChampion2();

}
