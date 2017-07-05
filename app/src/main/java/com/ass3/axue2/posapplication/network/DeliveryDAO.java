package com.ass3.axue2.posapplication.network;


import android.content.Context;

import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;
import com.ass3.axue2.posapplication.models.operational.Delivery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {
    private Connection connection;
    private Statement statement;

    private String mIP;
    private String mDB;
    private String mUser;
    private String mPassword;

    public DeliveryDAO(Context context) {
        ConfigurationDatabaseHelper CDBHelper = new ConfigurationDatabaseHelper(context);
        NetworkSetting networkSetting = CDBHelper.GetNetworkSetting(1);
        mIP = networkSetting.getsIPAddress();
        mDB = networkSetting.getsDBName();
        mUser = networkSetting.getsUsername();
        mPassword = networkSetting.getsPassword();
    }

    public List<Delivery> getDeliveries() throws SQLException {
        String query = "SELECT * FROM " + Delivery.TABLE_NAME;
        List<Delivery> list = new ArrayList<Delivery>();
        Delivery delivery = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                delivery = new Delivery();
				/*Retrieve one delivery details
				and store it in delivery object*/
                delivery.setnDeliveryID(rs.getLong(Delivery.COLUMN_ID));
                delivery.setnOrderID(rs.getLong(Delivery.COLUMN_ORDER_ID));
                delivery.setnDriverID(rs.getLong(Delivery.COLUMN_DRIVER_ID));
                delivery.setnCustomerID(rs.getLong(Delivery.COLUMN_CUSTOMER_ID));
                delivery.setsStatus(rs.getString(Delivery.COLUMN_STATUS));
                delivery.setnDeliveryFee(rs.getDouble(Delivery.COLUMN_DELIVERY_FEE));

                //add each delivery to the list
                list.add(delivery);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public Delivery getDelivery(long id) throws SQLException{
        String query = "SELECT * FROM " + Delivery.TABLE_NAME + " WHERE " + Delivery.COLUMN_ID + " = " + id;
        ResultSet rs = null;
        Delivery delivery = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()){
                delivery = new Delivery();
                delivery.setnDeliveryID(rs.getLong(Delivery.COLUMN_ID));
                delivery.setnOrderID(rs.getLong(Delivery.COLUMN_ORDER_ID));
                delivery.setnDriverID(rs.getLong(Delivery.COLUMN_DRIVER_ID));
                delivery.setnCustomerID(rs.getLong(Delivery.COLUMN_CUSTOMER_ID));
                delivery.setsStatus(rs.getString(Delivery.COLUMN_STATUS));
                delivery.setnDeliveryFee(rs.getDouble(Delivery.COLUMN_DELIVERY_FEE));
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return delivery;
    }

    public void insertDelivery(Delivery delivery) throws SQLException{
        String query = "INSERT INTO " + Delivery.TABLE_NAME + "(" +
                Delivery.COLUMN_ID + ", " + Delivery.COLUMN_ORDER_ID + ", " +
                Delivery.COLUMN_DRIVER_ID + ", " + Delivery.COLUMN_CUSTOMER_ID + ", " +
                Delivery.COLUMN_STATUS + ", " + Delivery.COLUMN_DELIVERY_FEE + ")" +
                " VALUES (default, '" + delivery.getnOrderID() + "' , '" +
                delivery.getnDriverID() + "' , '" + delivery.getnCustomerID() + "' , '" +
                delivery.getsStatus() + "' , '" + delivery.getnDeliveryFee() + "')";
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    public void updateDelivery(Delivery delivery) throws SQLException{
        String query = "UPDATE " + Delivery.TABLE_NAME + " SET " +
                Delivery.COLUMN_ORDER_ID + " = '" + delivery.getnOrderID() + "', "
                + Delivery.COLUMN_DRIVER_ID + " = '" + delivery.getnDriverID() + "', "
                + Delivery.COLUMN_CUSTOMER_ID + " = '" + delivery.getnCustomerID() + "', "
                + Delivery.COLUMN_STATUS + " = '" + delivery.getsStatus() + "', "
                + Delivery.COLUMN_DELIVERY_FEE + " = '" + delivery.getnDeliveryFee() + "'"
                + " WHERE " + Delivery.COLUMN_ID + " = " + delivery.getnDeliveryID();
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }
    public boolean testConnection(){
        String query = "SELECT 1 FROM " + Delivery.TABLE_NAME;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            if (connection == null){
                return false;
            }
            statement = connection.createStatement();
            statement.setQueryTimeout(100);
            rs = statement.executeQuery(query);
            return rs.next();
        }
        catch (NullPointerException n){
            return false;
        }
        catch (SQLException e){
            return false;
        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }
}