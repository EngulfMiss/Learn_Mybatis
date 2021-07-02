package com.engulf.dao;

import com.engulf.pojo.Master;
import org.apache.ibatis.annotations.Param;

public interface MasterMapper {
    //获取指定召唤师下的所有英雄和召唤师信息
    //连接查询方式
    Master selectMaster(@Param("masterId") Integer id);

    //子查询方式
    Master selectMaster2(@Param("masterId") Integer id);
}
