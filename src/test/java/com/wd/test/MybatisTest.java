package com.wd.test;

import com.wd.entity.QueryVo;
import com.wd.entity.User;
import com.wd.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * mybatis的入门案例
 */

public class MybatisTest {

    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession session;
    private UserMapper userMapper;

    @Before //用于在测试方法执行之前执行
    public void init() throws IOException {
        //1.读取配置文件,生成读取字节流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产SqlSession对象
        session = factory.openSession();
        //4.使用SqlSession创建Mapper代理的接口对象
        userMapper = session.getMapper(UserMapper.class);
    }

    /**
     * 释放资源
     * @throws IOException
     */
    @After //用于在测试方法执行之后执行
    public void destroy() throws IOException {
        //提交事务
        session.commit();
        session.close();
        in.close();
    }


    /**
     * 测试查询所有
     * @throws Exception
     */
    @Test
    public void testFindAll() throws Exception{
        //5.执行查询所有方法
        List<User> users = userMapper.findAll();
        for (User user:users){
            System.out.println(user);
        }

    }


    /**
     * 测试保存操作
     */
    @Test
    public void testSave(){
        User user = new User();
        user.setUsername("mybatisSaveUser last insert");
        user.setAddress("北京");
        user.setSex("m");
        user.setBirthday(new Date());
        System.out.println("保存之前" + user);

        //5.执行保存方法
        userMapper.saveUser(user);

        System.out.println("保存之后" + user);

    }


    /**
     * 测试更新操作
     */
    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(50);
        user.setUsername("mybatisUpdateUser");
        user.setAddress("www");
        user.setSex("m");
        user.setBirthday(new Date());

        //5.执行保存方法
        userMapper.updateUser(user);

    }

    /**
     * 测试删除方法
     */
    @Test
    public void testDelete(){
        //执行删除方法
        userMapper.deleteUser(46);
    }

    /**
     * 测试根据id查询用户信息
     */
    @Test
    public void testFindById(){
        User user = userMapper.findById(50);
        System.out.println(user);
    }

    /**
     * 测试模糊查询操作
     */
    @Test
    public void testFindByName(){
        //5.执行查询一个方法
        List<User> users = userMapper.findByName("m");
        for(User user : users){
            System. out.println(user);
        }
    }

    /**
     * 测试查询总记录条数
     */
    @Test
    public void testFindTotal(){
        int count = userMapper.findTotal();
        System.out.println(count);
    }


    /**
     * 测试使用QueryVo作为查询条件
     */
    @Test
    public void testFindByVo(){
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUsername("%my%");
        vo.setUser(user);
        List<User> users = userMapper.findUserByVo(vo);
        for(User u : users){
            System.out.println(u);
        }
    }



}


