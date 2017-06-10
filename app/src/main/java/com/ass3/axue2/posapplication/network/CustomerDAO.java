package com.ass3.axue2.posapplication.network;


import android.content.Context;

import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;
import com.ass3.axue2.posapplication.models.operational.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private Connection connection;
    private Statement statement;

    private String mIP;
    private String mDB;
    private String mUser;
    private String mPassword;

    public CustomerDAO(Context context) {
        ConfigurationDatabaseHelper CDBHelper = new ConfigurationDatabaseHelper(context);
        NetworkSetting networkSetting = CDBHelper.GetNetworkSetting(1);
        mIP = networkSetting.getsIPAddress();
        mDB = networkSetting.getsDBName();
        mUser = networkSetting.getsUsername();
        mPassword = networkSetting.getsPassword();
    }

    public List<Customer> getCustomers() throws SQLException {
        String query = "SELECT * FROM " + Customer.TABLE_NAME;
        List<Customer> list = new ArrayList<Customer>();
        Customer customer = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                customer = new Customer();
				/*Retrieve one customer details
				and store it in customer object*/
                customer.setnCustomerID(rs.getLong(Customer.COLUMN_ID));
                customer.setsFirstName(rs.getString(Customer.COLUMN_FIRST_NAME));
                customer.setsLastName(rs.getString(Customer.COLUMN_LAST_NAME));
                customer.setsAddressLine1(rs.getString(Customer.COLUMN_ADDRESS_LINE_1));
                customer.setsAddressLine2(rs.getString(Customer.COLUMN_ADDRESS_LINE_2));
                customer.setsAddressLine3(rs.getString(Customer.COLUMN_ADDRESS_LINE_3));
                customer.setnPostCode(rs.getInt(Customer.COLUMN_POST_CODE));
                customer.setnPhone(rs.getInt(Customer.COLUMN_PHONE));

                //add each customer to the list
                list.add(customer);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public Customer getCustomer(long id) throws SQLException{
        String query = "SELECT * FROM " + Customer.TABLE_NAME + " WHERE " + Customer.COLUMN_ID + " = " + id;
        ResultSet rs = null;
        Customer customer = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()){
                customer = new Customer();
                customer.setnCustomerID(rs.getLong(Customer.COLUMN_ID));
                customer.setsFirstName(rs.getString(Customer.COLUMN_FIRST_NAME));
                customer.setsLastName(rs.getString(Customer.COLUMN_LAST_NAME));
                customer.setsAddressLine1(rs.getString(Customer.COLUMN_ADDRESS_LINE_1));
                customer.setsAddressLine2(rs.getString(Customer.COLUMN_ADDRESS_LINE_2));
                customer.setsAddressLine3(rs.getString(Customer.COLUMN_ADDRESS_LINE_3));
                customer.setnPostCode(rs.getInt(Customer.COLUMN_POST_CODE));
                customer.setnPhone(rs.getInt(Customer.COLUMN_PHONE));
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return customer;
    }

    public long insertCustomer(Customer customer) throws SQLException{
        long id = 0;
        String query = "INSERT INTO " + Customer.TABLE_NAME + "(" +
                Customer.COLUMN_ID + ", " + Customer.COLUMN_FIRST_NAME + ", " +
                Customer.COLUMN_LAST_NAME + ", " + Customer.COLUMN_ADDRESS_LINE_1 + ", " +
                Customer.COLUMN_ADDRESS_LINE_2 + ", " + Customer.COLUMN_ADDRESS_LINE_3 + ", " +
                Customer.COLUMN_POST_CODE + ", " + Customer.COLUMN_PHONE + ")" +
                " VALUES (default, '" + customer.getsFirstName() + "' , '" +
                customer.getsLastName() + "' , '" + customer.getsAddressLine1() + "' , '" +
                customer.getsAddressLine2() + "' , '" + customer.getsAddressLine3() + "' , '" +
                customer.getnPostCode() + "' , '" + customer.getnPhone() + "')";
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            try (ResultSet generatedKeys = statement.getGeneratedKeys()){
                if (generatedKeys.next()){
                    id = generatedKeys.getLong(1);
                }
            }

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return id;
    }

    public void updateCustomer(Customer customer) throws SQLException{
        String query = "UPDATE " + Customer.TABLE_NAME + " SET " +
                Customer.COLUMN_FIRST_NAME + " = '" + customer.getsFirstName() + "', "
                + Customer.COLUMN_LAST_NAME + " = '" + customer.getsLastName() + "', "
                + Customer.COLUMN_ADDRESS_LINE_1 + " = '" + customer.getsAddressLine1() + "', "
                + Customer.COLUMN_ADDRESS_LINE_2 + " = '" + customer.getsAddressLine2() + "', "
                + Customer.COLUMN_ADDRESS_LINE_3 + " = '" + customer.getsAddressLine3() + "', "
                + Customer.COLUMN_POST_CODE + " = '" + customer.getnPostCode() + "', "
                + Customer.COLUMN_PHONE + " = '" + customer.getnPhone() + "'"
                + " WHERE " + Customer.COLUMN_ID + " = " + customer.getnCustomerID();

        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

}