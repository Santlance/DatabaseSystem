package com.DataBase;

import com.Utils.ConstantUtils;

import java.sql.*;

public class SqlUser {
    private Connection connection = null;
    public static final int ROOT_TYPE = 0;
    public static final int MANAGER_TYPE = 1;
    public static final int USER_TYPE = 2;


    public static SqlUser newSqlUser(int type) {
        return new SqlUser(type);
    }

    /**
     * 0 means administer 1 means manager 2 means student and teacher
     **/
    private SqlUser(int type) {
        if (type == 0) {
            if (connection == null) {
                try {
                    Class.forName(ConstantUtils.DRIVER_NAME);
                    System.out.println("数据库驱动加载成功");
                    this.connection = DriverManager.getConnection(ConstantUtils.DBURL, ConstantUtils.ROOT_NAME, ConstantUtils.PWD);
                    System.out.println("数据库连接成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("数据库连接失败");
                }
            }
        } else if (type == 1) {
            if (connection == null) {
                try {
                    Class.forName(ConstantUtils.DRIVER_NAME);
                    System.out.println("数据库驱动加载成功");
                    this.connection = DriverManager.getConnection(ConstantUtils.DBURL, ConstantUtils.MANAGER_NAME, ConstantUtils.PWD);
                    System.out.println("数据库连接成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("数据库连接失败");
                }
            }
        } else if (type == 2) {
            if (connection == null) {
                try {
                    Class.forName(ConstantUtils.DRIVER_NAME);
                    System.out.println("数据库驱动加载成功");
                    this.connection = DriverManager.getConnection(ConstantUtils.DBURL, ConstantUtils.USER_NAME, ConstantUtils.PWD);
                    System.out.println("数据库连接成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("数据库连接失败");
                }
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }


}
