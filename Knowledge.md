## 复杂查询
多个学生对应一个老师
- 对于学生而言， 多个学生**关联**一个老师 【多对一】 （关联，association）
- 对于老师而言，一个老师有很多学生 【一对多】 (集合，collection)

# 注意

在使用连接查询时，如果在你连接查询的sql语句中为字段起了别名，那么在resultMap中的colunm属性就应该映射的是别名的名称；但是，sql语句中的条件字段还是应该使用 数据库.字段名的形式

javaType和ofType
- javaType：用来指定实体类中属性的类型
- ofType：用来指定映射到List或其他集合中的类型，差不多就是泛型类型的约束

例如：Teacher对象中有student对象
```java
    public class Teacher{
    //  List就是javaType 而 List中的Student就是ofType
        private List<Student> students;
    }
```

# 对一的情况

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

# 对多的情况

## 按照查询结果处理(连接查询)
```xml
<select id="selectMaster" parameterType="int" resultMap="MasterC">
    select m.id masterId,m.name mname,c.id cid,c.name cname from my_master m left outer join champion c on m.id = c.mid where mid = #{masterId};
</select>

<resultMap id="MasterC" type="com.engulf.pojo.Master">
    <id property="id" column="masterId"></id>
    <result property="name" column="mname"></result>
    <!--
        javaType="" 指定属性的类型
        集合的泛型信息，我们使用ofType获取
    -->
    <collection property="champions" ofType="com.engulf.pojo.Champion">
        <id property="id" column="cid"></id>
        <result property="name" column="cname"></result>
        <result property="mid" column="masterId"></result>
    </collection>
</resultMap>
```

## 按照嵌套查询处理(子查询)
```xml
<resultMap id="MasterZ" type="com.engulf.pojo.Master">
    <id property="id" column="id"></id>
    <result property="name" column="name"></result>
    <collection property="champions" column="id" ofType="com.engulf.pojo.Champion" select="findMyChampion"></collection>
</resultMap>

<select id="selectMaster2" parameterType="int" resultMap="MasterZ">
    select * from my_master where id = #{masterId};
</select>

<select id="findMyChampion" resultType="com.engulf.pojo.Champion">
    select * from champion where mid = #{id};
</select>
```
