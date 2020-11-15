package com.itheima.test;

import com.dao.IUserDao;
import com.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * mybatis的入门案例
 */
public class MybatisTest {

    /**
     * 入门案例
     * @param args
     */
    public static void main(String[] args)throws Exception {
        //1.读取配置文件

        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");//读取配置文件 获取流对象
        //2.创建SqlSessionFactory工厂
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();//
        SqlSessionFactory factory = builder.build(in);//  使用内部封装好的方法去 读取XML配置文件  创建工厂对象
        //3.使用工厂对象生产SqlSession对象
        SqlSession session = factory.openSession();//批量生产 SqlSession对象
        //4.使用SqlSession创建Dao接口的代理对象
        IUserDao userDao = session.getMapper(IUserDao.class);  //动态代理 对框架里面的方法进行增强
        //5.使用代理对象执行方法
        List<User> users = userDao.findAll();

        List<User> users2 = userDao.findAll();
        System.out.println("测试结果"+(users==users2));
        for(User user : users){
            System.out.println(user);// 打印表里面的每一行
        }
        //6.释放资源
        session.close();
        in.close();
    }
    @Test
    private void a(){

    }
}
