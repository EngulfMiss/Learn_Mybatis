package com.engulf.pojo;

import lombok.Data;

//一对多的查询对象

@Data
public class Champion {
    private Integer id;
    private String name;
    private Integer mid;
}
