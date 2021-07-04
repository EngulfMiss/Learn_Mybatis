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

### sql片段
**使用sql标签提取公共的sql语句进行复用，使用时用include标签refid属性找到对应的sql id**

**注意事项**
- 最好基于单表来定义SQL片段
- 不要存在where标签

```xml
<sql id="baseSql">
    select * from blog
</sql>
<select id="selectBlogIf" parameterType="map" resultType="com.engulf.pojo.Blog">
    <include refid="baseSql"></include>
    <where>
        <if test="title != null">
            title = #{title}
        </if>
        <if test="author != null">
            and author = #{author}
        </if>
    </where>
</select>
```

### foreach
- **动态 SQL 的另一个常见使用场景是对集合进行遍历（尤其是在构建 IN 条件语句的时候）。**  
- **foreach 元素的功能非常强大，它允许你指定一个集合，声明可以在元素体内使用的集合项（item）和索引（index）变量。它也允许你指定开头与结尾的字符串以及集合项迭代之间的分隔符。这个元素也不会错误地添加多余的分隔符**  
- **你可以将任何可迭代对象（如 List、Set 等）、Map 对象或者数组对象作为集合参数传递给 foreach。当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。**
```xml
<!-- 
collection:遍历的
index:下标
item:遍历出来的每一项
open:开头
close:结尾
separator:分隔符
-->
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  WHERE ID in
  <foreach item="it" index="index" collection="list"
      open="(" separator="," close=")">
        #{it}
  </foreach>
</select>
    
<select id="selectBlogForeach" parameterType="map" resultType="com.engulf.pojo.Blog">
    select * from blog
    <where>
        <!--
            注意：想要where标签帮助消除and open属性中的 and (  要有空格
            否则 Preparing: select * from blog WHERE and( author = ? or author = ? )
         -->
        <foreach collection="Authors" item="author" open="and (" close=")" separator="or">
             author = #{author}
        </foreach>
    </where>
</select>
```

# 缓存
[https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#cache](https://mybatis.org/mybatis-3/zh/sqlmap-xml.html#cache/) Mybatis官网缓存介绍.
1.什么是缓存[Cache]?
- 存在内存中的临时数据
- 将用户经常查询的数据存放在缓存(内存)中，用户去查询数据就不用从磁盘上查询，而可以从缓存中查询，从而提高查询效率，解决了高并发系统性能问题

2.为什么使用缓存?
- 减少和数据库的交互次数，减少系统开销，提高系统效率

3.什么样的数据能使用缓存?
- 经常查询并且不经常改变的数据

## Mybatis缓存
- Mybatis包含一个非常强大的查询缓存特性，它可以非常方便地定制和配置缓存，缓存可以极大的提升查询效率
- Mybatis系统中默认定义了两级缓存：一级缓存和二级缓存
    - 默认情况下，只有一级缓存开启(sqlSession级别的缓存，也称本地缓存)
    - 二级缓存需要手动开启和配置，他是基于namespace级别的缓存
    - 为了提高扩展性，Mybatis定义了缓存接口Cacha，我们可以通过实现Cache接口来自定义二级缓存

### 一级缓存
- 一级缓存也叫本地缓存：sqlSession
    - 与数据库同一次会话期间查询到的数据会放在本地缓存中
    - 以后如果需要获取相同的数据，直接从缓存中拿，没必要再查询数据库

缓存失效情况：
- 增删改会刷新缓存
- 手动清理缓存
- 查询不同的数据
- 查询不同的Mapper

### 二级缓存
- 二级缓存也叫全局缓存
- 基于namespace级别的缓存，一个名称空间，对应一个二级缓存

**使用步骤**
* 1.开启全局缓存 核心配置文件的settting中设置 当然默认也为true
```xml
<settings>
    <!-- 显示的开启全局缓存 -->
    <setting name="cacheEnabled" value="true"/>
</settings>
```
* 2.在Mapper中开启缓存</cache>
属性可以通过 cache 元素的属性来修改。比如：
```xml
<!-- 如果只使<cache/>要注意序列化 -->
<cache
  eviction="FIFO"
  flushInterval="60000"
  size="512"
  readOnly="true"/>
```
这个更高级的配置创建了一个 FIFO 缓存，每隔 60 秒刷新，最多可以存储结果对象或列表的 512 个引用，而且返回的对象被认为是只读的，因此对它们进行修改可能会在不同线程中的调用者产生冲突。

可用的清除策略有：

LRU – 最近最少使用：移除最长时间不被使用的对象。
FIFO – 先进先出：按对象进入缓存的顺序来移除它们。
SOFT – 软引用：基于垃圾回收器状态和软引用规则移除对象。
WEAK – 弱引用：更积极地基于垃圾收集器状态和弱引用规则移除对象。
默认的清除策略是 LRU。

flushInterval（刷新间隔）属性可以被设置为任意的正整数，设置的值应该是一个以毫秒为单位的合理时间量。 默认情况是不设置，也就是没有刷新间隔，缓存仅仅会在调用语句时刷新。

size（引用数目）属性可以被设置为任意正整数，要注意欲缓存对象的大小和运行环境中可用的内存资源。默认值是 1024。

readOnly（只读）属性可以被设置为 true 或 false。只读的缓存会给所有调用者返回缓存对象的相同实例。 因此这些对象不能被修改。这就提供了可观的性能提升。而可读写的缓存会（通过序列化）返回缓存对象的拷贝。 速度上会慢一些，但是更安全，因此默认值是 false。

二级缓存总结：
- 开启了二级缓存，在同一个namespace(Mapper)下有效
- 所有数据都会先放在一级缓存中
- 只有当会话提交或者关闭时才会提交到二级缓存中
