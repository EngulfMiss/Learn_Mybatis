package com.engulf.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Master {
    private Integer id;
    private String name;

    //一个召唤师有多个英雄
    private List<Champion> champions;
}
