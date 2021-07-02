package com.engulf.dao;

import com.engulf.pojo.Champion;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    //@Results最基础的使用方式(不考虑一对多，多对一等等)
    @Select("select * from student")
    @Results(id = "RM",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "name",property = "name"),
            @Result(column = "local_id",property = "localNum")
    })
    //后续其他可以通过@ResultMap("idName")来重复使用，但必须先用一次好像才能使用@ResultMap
    //否则好像会报错
    public abstract List<Champion> getChampions();

    @Insert("insert into student(id,name,local_id) values(#{id},#{name},#{localNum})")
    int addChampion(Champion champion);

    @Update("update student set name = #{name} where id = #{id}")
    int updateC(Champion champion);

    @Delete("delete from student where id = #{uid}")
    int delC(@Param("uid") int id);
}
