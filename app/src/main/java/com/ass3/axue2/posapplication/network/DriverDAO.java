package com.ass3.axue2.posapplication.network;

import com.ass3.axue2.posapplication.models.operational.Driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DriverDAO {
    private Connection connection;
    private Statement statement;

    public DriverDAO() { }

    public List<Driver> getDrivers() throws SQLException {
        String query = "SELECT * FROM " + Driver.TABLE_NAME;
        List<Driver> list = new ArrayList<Driver>();
        Driver driver = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                driver = new Driver();
				/*Retrieve one driver details
				and store it in driver object*/
                driver.setnDriverID(rs.getLong(Driver.COLUMN_ID));
                driver.setnFirstName(rs.getString(Driver.COLUMN_FIRST_NAME));
                driver.setnLastName(rs.getString(Driver.COLUMN_LAST_NAME));
                //add each driver to the list
                list.add(driver);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public Driver getDriver(long id) throws SQLException{
        String query = "SELECT * FROM " + Driver.TABLE_NAME + " WHERE " + Driver.COLUMN_ID + " = " + id;
        ResultSet rs = null;
        Driver driver = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()){
                driver = new Driver();
                driver.setnDriverID(rs.getLong(Driver.COLUMN_ID));
                driver.setnFirstName(rs.getString(Driver.COLUMN_FIRST_NAME));
                driver.setnLastName(rs.getString(Driver.COLUMN_LAST_NAME));
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return driver;
    }

    public void insertDriver(Driver driver) throws SQLException{
        String query = "INSERT INTO " + Driver.TABLE_NAME + "(" +
                Driver.COLUMN_ID + ", " + Driver.COLUMN_FIRST_NAME + ", " +
                Driver.COLUMN_LAST_NAME + ")" +
                " VALUES (default, '" + driver.getnFirstName() + "' , '" +
                driver.getnLastName() + "')";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    public void updateDriver(Driver driver) throws SQLException{
        String query = "UPDATE " + Driver.TABLE_NAME + " SET " +
                Driver.COLUMN_FIRST_NAME + " = '" + driver.getnFirstName() + "', "
                + Driver.COLUMN_LAST_NAME + " = '" + driver.getnLastName() + "', "
                + " WHERE " + Driver.COLUMN_ID + " = " + driver.getnDriverID();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

}