package com.engulf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Champion {
    private Integer id;
    private String name;
    private Integer localNum;

}
