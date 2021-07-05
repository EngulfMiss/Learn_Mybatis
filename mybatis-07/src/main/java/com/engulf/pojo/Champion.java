package com.engulf.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Champion implements Serializable {
    private Integer id;
    private String name;
    private Integer local_id;
}
