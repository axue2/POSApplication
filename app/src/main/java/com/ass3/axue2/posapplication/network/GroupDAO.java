package com.ass3.axue2.posapplication.network;

import android.content.Context;

import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;
import com.ass3.axue2.posapplication.models.operational.Group;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    private Connection connection;
    private Statement statement;

    private String mIP;
    private String mDB;
    private String mUser;
    private String mPassword;

    public GroupDAO(Context context) {
        ConfigurationDatabaseHelper CDBHelper = new ConfigurationDatabaseHelper(context);
        NetworkSetting networkSetting = CDBHelper.GetNetworkSetting(1);
        mIP = networkSetting.getsIPAddress();
        mDB = networkSetting.getsDBName();
        mUser = networkSetting.getsUsername();
        mPassword = networkSetting.getsPassword();
    }

    public List<Group> getGroups() throws SQLException {
        String query = "SELECT * FROM " + Group.TABLE_NAME;
        List<Group> list = new ArrayList<Group>();
        Group group = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                group = new Group();
				/*Retrieve one group details
				and store it in group object*/
                group.setnGroupID(rs.getInt(Group.COLUMN_ID));
                group.setsGroupName(rs.getString(Group.COLUMN_NAME));

                //add each group to the list
                list.add(group);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public Group getGroup(long id) throws SQLException{
        String query = "SELECT * FROM " + Group.TABLE_NAME + " WHERE " + Group.COLUMN_ID + " = " + id;
        ResultSet rs = null;
        Group group = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()){
                group = new Group();
                group.setnGroupID(rs.getInt(Group.COLUMN_ID));
                group.setsGroupName(rs.getString(Group.COLUMN_NAME));
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return group;
    }

    public void insertGroup(Group group) throws SQLException{
        String query = "INSERT INTO " + Group.TABLE_NAME + "(" +
                Group.COLUMN_ID + ", " + Group.COLUMN_NAME + ")" +
                " VALUES (default, '" + group.getsGroupName() + "')";
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    public void updateGroup(Group group) throws SQLException{
        String query = "UPDATE " + Group.TABLE_NAME + " SET " + Group.COLUMN_NAME + " = '" +
                group.getsGroupName() + "' WHERE " + Group.COLUMN_ID + " = " + String.valueOf(group.getnGroupID());

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
        String query = "SELECT 1 FROM " + Group.TABLE_NAME;
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