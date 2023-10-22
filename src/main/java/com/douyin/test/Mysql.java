package com.douyin.test;

import java.sql.*;

public class Mysql {


    public static void main(String[] args) {

        //保存到数据库   **********************
        // 连接到 MySQL 数据库并执行查询
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // 注册 MySQL JDBC 驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 连接到数据库
            String url = "jdbc:mysql://localhost:3306/e3hui8997";

            String username = "username";
            String password = "password";
            connection = DriverManager.getConnection(url, username, password);

            // 创建 SQL 语句
            String sql = "SELECT * FROM e3hui8997";
            statement = connection.createStatement();

            // 执行查询
            resultSet = statement.executeQuery(sql);

            // 处理结果集
            while (resultSet.next()) {
                // 获取数据
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                // 输出数据
                System.out.println("ID: " + id + ", Name: " + name);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
