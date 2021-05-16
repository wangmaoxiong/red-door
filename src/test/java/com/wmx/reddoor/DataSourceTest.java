package com.wmx.reddoor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据源测试，测试 spring.datasource.xx 配置是否正常，数据库是否能连接上等等
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/5/16 13:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTest {
    /**
     * Spring Boot 默认已经配置好了数据源，程序员可以直接 DI 注入然后使用即可
     */
    @Resource
    DataSource dataSource;

    @Test
    public void contextLoads() throws SQLException {
        System.out.println("数据源>>>>>>" + dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println("连接>>>>>>>>>" + connection);
        System.out.println("连接地址>>>>>" + connection.getMetaData().getURL());
        connection.close();
    }

//    控制台输出：
//    数据源>>>>>>class com.zaxxer.hikari.HikariDataSource
//    连接>>>>>>>>>HikariProxyConnection@519751097 wrapping com.mysql.cj.jdbc.ConnectionImpl@11a3a45f
//    连接地址>>>>>jdbc:mysql://127.0.0.1:3306/wang?characterEncoding=UTF-8&serverTimezone=UTC
}
