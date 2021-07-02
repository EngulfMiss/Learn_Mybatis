package com.engulf.dao;

import com.engulf.pojo.Champion;

import java.util.List;

public interface UserMapper {
    public List<Champion> selectChampions();
}
