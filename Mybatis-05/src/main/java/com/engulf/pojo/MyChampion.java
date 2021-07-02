package com.engulf.pojo;

import lombok.Data;

//多对一的查询对象

@Data
public class MyChampion {
    private Integer id;
    private String name;

    //每个英雄都有一个召唤师
    private Master master;
}
