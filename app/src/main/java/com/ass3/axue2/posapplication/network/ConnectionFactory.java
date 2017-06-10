package com.ass3.axue2.posapplication.network;

import android.app.Application;
import android.content.Context;

import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Connection factory from : https://ibytecode.com/blog/jdbc-examples-introduction/

public class ConnectionFactory extends Application {

    private static ConnectionFactory instance = new ConnectionFactory();

    public String DRIVER_CLASS = "com.mysql.jdbc.Driver";

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection createConnection(String url, String user, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }

    public static Connection getConnection(String ip, String db, String user, String password) {
        String url = "jdbc:mysql://" + ip + "/" + db;
        System.out.println(url);
        return instance.createConnection(url, user, password);
    }
}