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

# 动态SQL

动态SQL元素和JSTL或基于类似XML的文本处理器相似。

- if
- choose(when,otherwise)
- trim
- foreach

### if
```xml
<select id="selectBlogIf" parameterType="map" resultType="com.engulf.pojo.Blog">
    select * from blog where 1 = 1
    <if test="title != null">
        and title = #{title}
    </if>
    <if test="author != null">
        and author = #{author}
    </if>
</select>
```

### trim、where、set

**trim**
___
```xml
<trim prefix="(" prefixOverrides="OR" suffixOverrides="," suffix=")">子句</trim>
```

　　这里的子句会对其进行trim()处理，忽略掉换行、空格等字符，所以本文中的子句都是指trim()处理后的字符串。如果子句为空，那么整个<trim>块不起作用，相当于不存在。本<trim>块的作用就是：去掉子句首的OR和子句尾的逗号，并在子句前后分别加上(和)，比如，"orabc,"-->"(abc)"。

- prefixOverrides：子句首的命中词列表，以|分隔，忽略大小写。如果命中（轮询命中词，最多只命中一次），会删除子句首命中的词；没命中就算了。
- prefix：删除子句句首后，在子句最前边加上单个空格+prefix。
- suffixOverrides：子句尾的命中词列表，以|分隔，忽略大小写。如果命中（轮询命中词，最多只命中一次），会删除子句尾命中的词；没命中就算了。
- suffix：删除子句句尾后，在子句最后边加上单个空格+suffix。
    
___

**where 元素只会在子元素返回任何内容的情况下才插入 “WHERE” 子句。而且，若子句的开头为 “AND” 或 “OR”，where 元素也会将它们去除。**

```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG
  <where>
    <if test="state != null">
         state = #{state}
    </if>
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>
```

**用于动态更新语句的类似解决方案叫做 set。set 元素可以用于动态包含需要更新的列，忽略其它不更新的列。
这个例子中，set 元素会动态地在行首插入 SET 关键字，并会删掉额外的逗号（这些逗号是在使用条件语句给列赋值时引入的）。**
```xml
<update id="updateAuthorIfNecessary">
  update Author
    <set>
      <if test="username != null">username=#{username},</if>
      <if test="password != null">password=#{password},</if>
      <if test="email != null">email=#{email},</if>
      <if test="bio != null">bio=#{bio}</if>
    </set>
  where id=#{id}
</update>
```

### choose(when,otherwise)
    **有时候，我们不想使用所有的条件，而只是想从多个条件中选择一个使用。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。**
```xml
<select id="findActiveBlogLike"
 resultType="Blog">
    SELECT * FROM BLOG WHERE state = ‘ACTIVE’
    <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
    </choose>
</select>
```
