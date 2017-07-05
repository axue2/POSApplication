package com.ass3.axue2.posapplication.network;

import android.content.Context;

import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;
import com.ass3.axue2.posapplication.models.operational.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Connection connection;
    private Statement statement;

    private String mIP;
    private String mDB;
    private String mUser;
    private String mPassword;

    public ProductDAO(Context context) {
        ConfigurationDatabaseHelper CDBHelper = new ConfigurationDatabaseHelper(context);
        NetworkSetting networkSetting = CDBHelper.GetNetworkSetting(1);
        mIP = networkSetting.getsIPAddress();
        mDB = networkSetting.getsDBName();
        mUser = networkSetting.getsUsername();
        mPassword = networkSetting.getsPassword();
    }

    public List<Product> getProducts() throws SQLException {
        String query = "SELECT * FROM " + Product.TABLE_NAME;
        List<Product> list = new ArrayList<Product>();
        Product product = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                product = new Product();
				/*Retrieve one product details
				and store it in product object*/
                product.setnProductID(rs.getInt(Product.COLUMN_ID));
                product.setnGroupID(rs.getInt(Product.COLUMN_GROUPID));
                product.setsProductName(rs.getString(Product.COLUMN_NAME));
                product.setnPrice(rs.getDouble(Product.COLUMN_PRICE));

                //add each product to the list
                list.add(product);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public Product getProduct(long id) throws SQLException{
        String query = "SELECT * FROM " + Product.TABLE_NAME + " WHERE " + Product.COLUMN_ID + " = " + id;
        ResultSet rs = null;
        Product product = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()){
                product = new Product();

                product.setnProductID(rs.getInt(Product.COLUMN_ID));
                product.setnGroupID(rs.getInt(Product.COLUMN_GROUPID));
                product.setsProductName(rs.getString(Product.COLUMN_NAME));
                product.setnPrice(rs.getDouble(Product.COLUMN_PRICE));
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return product;
    }

    public void insertProduct(Product product) throws SQLException{
        String query = "INSERT INTO " + Product.TABLE_NAME + "(" +
                Product.COLUMN_ID + ", " + Product.COLUMN_GROUPID + ", " +
                Product.COLUMN_NAME + ", " + Product.COLUMN_PRICE + ")" +
                " VALUES (default, '" + product.getnGroupID() + "' , '" +
                product.getsProductName() + "' , '" + product.getnPrice() + "')";
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    public void updateProduct(Product product) throws SQLException{
        String query = "UPDATE " + Product.TABLE_NAME + " SET "
                + Product.COLUMN_GROUPID + " = " + String.valueOf(product.getnGroupID()) + ", "
                + Product.COLUMN_NAME + " = " + String.valueOf(product.getsProductName()) + ", "
                + Product.COLUMN_PRICE + " = " + String.valueOf(product.getnPrice()) + ", "
                + "' WHERE " + Product.COLUMN_ID + " = " + String.valueOf(product.getnProductID());

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
        String query = "SELECT 1 FROM " + Product.TABLE_NAME;
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