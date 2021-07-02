## 复杂查询
多个学生对应一个老师
- 对于学生而言， 多个学生**关联**一个老师 【多对一】 （关联，association）
- 对于老师而言，一个老师有很多学生 【一对多】 (集合，collection)

## 按照嵌套查询处理(子查询)
```xml
<resultMap id="MC" type="com.engulf.pojo.MyChampion">
    <id property="id" column="id"></id>
    <result property="name" column="name"></result>
    <!-- 复杂属性(对象:association) (集合:collection) -->
    <!-- 通过第一个查询出来的结果，作为另一个查询的条件查询出对象进行封装(嵌套查询，子查询)
        javaType 封装的类型 
        select 另一个查询的方法
        需要使用resultMap
    -->
    <association property="master" column="mid" javaType="com.engulf.pojo.Master" select="selectMaster"></association>
</resultMap>

<select id="selectMyChampion" resultMap="MC">
    select * from champion;
</select>

<select id="selectMaster" resultType="com.engulf.pojo.Master">
    select * from my_master where id = #{mid}
</select>
```

## 按照查询结果处理(连接查询)
```xml
<resultMap id="MCC" type="com.engulf.pojo.MyChampion">
    <id property="id" column="cid"></id>
    <result property="name" column="cname"></result>
    <association property="master" javaType="com.engulf.pojo.Master">
        <!-- 对内部对象进行映射配置 -->
        <result property="name" column="mname"></result>
    </association>
</resultMap>
<select id="selectMyChampion2" resultMap="MCC">
    select c.id cid,c.name cname,m.name mname from champion c,my_master m where c.mid = m.id;
</select>
```
