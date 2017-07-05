package com.ass3.axue2.posapplication.network;

import android.content.Context;

import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;
import com.ass3.axue2.posapplication.models.operational.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {
    private Connection connection;
    private Statement statement;

    private String mIP;
    private String mDB;
    private String mUser;
    private String mPassword;

    public TableDAO(Context context) {
        ConfigurationDatabaseHelper CDBHelper = new ConfigurationDatabaseHelper(context);
        NetworkSetting networkSetting = CDBHelper.GetNetworkSetting(1);
        mIP = networkSetting.getsIPAddress();
        mDB = networkSetting.getsDBName();
        mUser = networkSetting.getsUsername();
        mPassword = networkSetting.getsPassword();
    }

    public List<Table> getTables() throws SQLException {
        String query = "SELECT * FROM " + Table.TABLE_NAME;
        List<Table> list = new ArrayList<Table>();
        Table table = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                table = new Table();
				/*Retrieve one table details
				and store it in table object*/
                table.setnTableID(rs.getInt(Table.COLUMN_ID));
                table.setsTableName(rs.getString(Table.COLUMN_NAME));
                table.setnGuests(rs.getInt(Table.COLUMN_GUESTS));
                table.setnOrderID(rs.getInt(Table.COLUMN_ORDER_ID));
                table.setnInvSum(rs.getDouble(Table.COLUMN_TOTAL));
                table.setsStatus(rs.getString(Table.COLUMN_STATUS));

                //add each table to the list
                list.add(table);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public Table getTable(long id) throws SQLException{
        String query = "SELECT * FROM " + Table.TABLE_NAME + " WHERE " + Table.COLUMN_ID + " = " + id;
        ResultSet rs = null;
        Table table = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()){
                table = new Table();
                table.setnTableID(rs.getInt(Table.COLUMN_ID));
                table.setsTableName(rs.getString(Table.COLUMN_NAME));
                table.setnGuests(rs.getInt(Table.COLUMN_GUESTS));
                table.setnOrderID(rs.getInt(Table.COLUMN_ORDER_ID));
                table.setnInvSum(rs.getDouble(Table.COLUMN_TOTAL));
                table.setsStatus(rs.getString(Table.COLUMN_STATUS));
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return table;
    }

    public void insertTable(Table table) throws SQLException{
        String query = "INSERT INTO " + Table.TABLE_NAME + "(" +
                Table.COLUMN_ID + ", " + Table.COLUMN_NAME + ", " +
                Table.COLUMN_GUESTS + ", " + Table.COLUMN_ORDER_ID + ", " +
                Table.COLUMN_TOTAL + ", " + Table.COLUMN_STATUS + ")" +
                " VALUES (default, '" + table.getsTableName() + "' , '" +
                table.getnGuests() + "' , '" + table.getnOrderID() + "' , '" +
                table.getnInvSum() + "' , '" + table.getsStatus() + "')";
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    public void updateTable(Table table) throws SQLException{
        String query = "UPDATE " + Table.TABLE_NAME + " SET " +
                Table.COLUMN_NAME + " = '" + table.getsTableName() + "', "
                + Table.COLUMN_GUESTS + " = '" + table.getnGuests() + "', "
                + Table.COLUMN_ORDER_ID + " = '" + table.getnOrderID() + "', "
                + Table.COLUMN_TOTAL + " = '" + table.getnInvSum() + "', "
                + Table.COLUMN_STATUS + " = '" + table.getsStatus() + "'"
                + " WHERE " + Table.COLUMN_ID + " = " + table.getnTableID();

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
        String query = "SELECT 1 FROM " + Table.TABLE_NAME;
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