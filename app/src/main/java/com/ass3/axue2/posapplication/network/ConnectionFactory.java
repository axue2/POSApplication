package com.ass3.axue2.posapplication.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Connection factory from : https://ibytecode.com/blog/jdbc-examples-introduction/

public class ConnectionFactory {

    // connection information
    // TODO: allow user to dymanically change connection information
    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mysql://192.168.56.1/posdb";
    public static final String USER = "root";
    public static final String PASSWORD = "123";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";


    private ConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }

    public static Connection getConnection() {
        return instance.createConnection();
    }
}