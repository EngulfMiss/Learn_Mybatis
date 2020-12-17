package com.Engulf.mybatis.defaults;

import com.Engulf.mybatis.cfg.Configuration;
import com.Engulf.mybatis.sqlsession.SqlSession;
import com.Engulf.mybatis.sqlsession.proxy.MapperProxy;
import com.Engulf.mybatis.utils.DataSourceUtil;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

public class DefaultSqlSession implements SqlSession {

    private Configuration config;
    private Connection connection;

    public DefaultSqlSession(Configuration config){
        this.config = config;
        connection = DataSourceUtil.getConnection(config);
    }

    /**
     * 创建代理对象
     * @param daoInterfaceClass dao的接口字节码
     * @param <T>
     * @return
     */
    @Override
    public <T> T getMapper(Class<T> daoInterfaceClass) {
        return (T)Proxy.newProxyInstance(daoInterfaceClass.getClassLoader(),new Class[]{daoInterfaceClass},new MapperProxy(config.getMappers(),connection));
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        if(connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
