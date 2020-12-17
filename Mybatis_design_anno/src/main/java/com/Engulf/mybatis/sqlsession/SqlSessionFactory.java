package com.Engulf.mybatis.sqlsession;

public interface SqlSessionFactory {
    /**
     * 用于打开一个新的SqlSession对象
     * @return
     */
    public abstract SqlSession openSession();
}
