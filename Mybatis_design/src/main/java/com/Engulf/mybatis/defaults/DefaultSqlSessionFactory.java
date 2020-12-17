package com.Engulf.mybatis.defaults;

import com.Engulf.mybatis.cfg.Configuration;
import com.Engulf.mybatis.sqlsession.SqlSession;
import com.Engulf.mybatis.sqlsession.SqlSessionFactory;

import java.io.InputStream;

/**
 * SqlSessionFactory的实现类
 */

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration config;

    public DefaultSqlSessionFactory(Configuration config){
        this.config = config;
    }
    /**
     * 用于创建一个新的操作数据库对象
     * @return
     */
    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(config);
    }
}
