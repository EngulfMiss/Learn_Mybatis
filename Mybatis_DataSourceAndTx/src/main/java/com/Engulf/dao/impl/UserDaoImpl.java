package com.Engulf.dao.impl;

import com.Engulf.dao.IUserDao;
import com.Engulf.domain.User;
import com.Engulf.domain.User2;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDaoImpl implements IUserDao {

    private SqlSessionFactory factory;

    public UserDaoImpl(SqlSessionFactory factory){
        this.factory = factory;
    }

    @Override
    public List<User> findAll() {
        //1.根据factory获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //2.调用SqlSession中的方法，实现查询列表
        List<User> users = sqlSession.selectList("com.Engulf.dao.IUserDao.findAll");//参数就是能获取配置信息的key
        sqlSession.close();
        return users;
    }

    @Override
    public void saveUser(User user) {
        //1.根据factory获取SqlSession对象
        SqlSession sqlSession = factory.openSession(true);
        //2.调用方法完成保存
        sqlSession.insert("com.Engulf.dao.IUserDao.saveUser",user);
        //3.提交事务
        //sqlSession.commit();
        //4.释放资源
        sqlSession.close();

    }

    @Override
    public void updateUser(User user) {
        //1.根据factory获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //2.调用方法完成更新
        sqlSession.update("com.Engulf.dao.IUserDao.updateUser",user);
        //3.提交事务
        sqlSession.commit();
        //4.释放资源
        sqlSession.close();
    }

    @Override
    public void deleteUser(Integer id) {
        //1.根据factory获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //2.调用方法完成更新
        sqlSession.update("com.Engulf.dao.IUserDao.deleteUser",id);
        //3.提交事务
        sqlSession.commit();
        //4.释放资源
        sqlSession.close();
    }

    @Override
    public User findByName(String name) {
        //1.根据factory获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //2.调用SqlSession中的方法，实现查询一个
        User user = sqlSession.selectOne("com.Engulf.dao.IUserDao.findByName",name);//参数就是能获取配置信息的key
        sqlSession.close();
        return user;
    }

    @Override
    public List<User> findByVagueName(String VagueName) {
        //1.根据factory获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //2.调用SqlSession中的方法，实现查询一个
        List<User> users = sqlSession.selectList("com.Engulf.dao.IUserDao.findByVagueName",VagueName);//参数就是能获取配置信息的key
        sqlSession.close();
        return users;
    }

    @Override
    public int findTotal() {
        //1.根据factory获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //2.调用SqlSession中的方法，实现查询一个
        Integer count = sqlSession.selectOne("com.Engulf.dao.IUserDao.findTotal");//参数就是能获取配置信息的key
        sqlSession.close();
        return count;
    }
}
