package com.ass3.axue2.posapplication.network;

import com.ass3.axue2.posapplication.models.operational.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection connection;
    private Statement statement;

    public OrderDAO() { }

    public List<Order> getOrders() throws SQLException {
        String query = "SELECT * FROM " + Order.TABLE_NAME;
        List<Order> list = new ArrayList<Order>();
        Order order = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                order = new Order();
				/*Retrieve one order details
				and store it in order object*/
                order.setnOrderID(rs.getInt(Order.COLUMN_ID));
                order.setnTableID(rs.getInt(Order.COLUMN_TABLE_ID));
                order.setsType(rs.getString(Order.COLUMN_TYPE));
                order.setsStatus(rs.getString(Order.COLUMN_STATUS));
                order.setnTotal(rs.getInt(Order.COLUMN_TOTAL));

                //add each order to the list
                list.add(order);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public Order getOrder(long id) throws SQLException{
        String query = "SELECT * FROM " + Order.TABLE_NAME + " WHERE " + Order.COLUMN_ID + " = " + id;
        ResultSet rs = null;
        Order order = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()){
                order = new Order();
                order.setnOrderID(rs.getInt(Order.COLUMN_ID));
                order.setnTableID(rs.getInt(Order.COLUMN_TABLE_ID));
                order.setsType(rs.getString(Order.COLUMN_TYPE));
                order.setsStatus(rs.getString(Order.COLUMN_STATUS));
                order.setnTotal(rs.getInt(Order.COLUMN_TOTAL));
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return order;
    }

    public long insertOrder(Order order) throws SQLException{
        long id = 0;
        String query = "INSERT INTO " + Order.TABLE_NAME + "(" +
                Order.COLUMN_ID + ", " + Order.COLUMN_TABLE_ID + ", " +
                Order.COLUMN_TYPE + ", " + Order.COLUMN_STATUS + ", " +
                Order.COLUMN_TOTAL + ")" +
                " VALUES (default, '" + order.getnTableID() + "' , '" +
                order.getsType() + "' , '" + order.getsStatus() + "' , '" +
                order.getnTotal() + "')";
        try {
            connection = ConnectionFactory.getConnection();
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

    public void updateOrder(Order order) throws SQLException{
/*        String query = "UPDATE " + Order.TABLE_NAME + " SET " +
                Order.COLUMN_TABLE_ID + " = " + order.getnTableID() + ", "
                + Order.COLUMN_TYPE + " = '" + order.getsType() + "', "
                + Order.COLUMN_STATUS + " = '" + order.getsStatus() + "', "
                + Order.COLUMN_TOTAL + " = " + String.valueOf(order.getnTotal())
                + " WHERE " + Order.COLUMN_ID + " = " + String.valueOf(order.getnOrderID());*/

        String query = "UPDATE " + Order.TABLE_NAME + " SET "
                + Order.COLUMN_TABLE_ID + " = '" + order.getnTableID()
                + "', " + Order.COLUMN_TYPE + " = '" + order.getsType()
                + "', " + Order.COLUMN_STATUS + " = '" + order.getsStatus()
                + "', " + Order.COLUMN_TOTAL + " = '" + order.getnTotal() + "'" +
                " WHERE " + Order.COLUMN_ID + " = " + order.getnOrderID();
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