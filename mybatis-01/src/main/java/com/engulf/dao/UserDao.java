package com.engulf.dao;

import com.engulf.pojo.Champion;

import java.util.List;

public interface UserDao {
    public List<Champion> selectChampions();

    public Champion selectById(Integer id);

    public int addChampion(Champion champion);

    public void updateChampion(Champion champion);

    public List<Champion> selectLike(String str);
}
