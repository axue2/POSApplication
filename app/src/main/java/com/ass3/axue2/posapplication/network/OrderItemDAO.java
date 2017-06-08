package com.ass3.axue2.posapplication.network;

import com.ass3.axue2.posapplication.models.operational.Order;
import com.ass3.axue2.posapplication.models.operational.OrderItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    private Connection connection;
    private Statement statement;

    public OrderItemDAO() { }

    public List<OrderItem> getOrderItems() throws SQLException {
        String query = "SELECT * FROM " + OrderItem.TABLE_NAME;
        List<OrderItem> list = new ArrayList<OrderItem>();
        OrderItem orderItem = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                orderItem = new OrderItem();
				/*Retrieve one orderItem details
				and store it in orderItem object*/
                orderItem.setnOrderItemID(rs.getInt(OrderItem.COLUMN_ID));
                orderItem.setnOrderID(rs.getInt(OrderItem.COLUMN_ORDER_ID));
                orderItem.setnTableID(rs.getInt(OrderItem.COLUMN_TABLE_ID));
                orderItem.setnProductID(rs.getInt(OrderItem.COLUMN_PRODUCT_ID));
                orderItem.setsProductName(rs.getString(OrderItem.COLUMN_PRODUCT_NAME));
                orderItem.setnPrice(rs.getDouble(OrderItem.COLUMN_PRODUCT_PRICE));
                orderItem.setnQuantity(rs.getInt(OrderItem.COLUMN_QUANTITY));

                //add each orderItem to the list
                list.add(orderItem);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public List<OrderItem> getOrderItemsByOrder(long id) throws SQLException {
        String query = "SELECT * FROM " + OrderItem.TABLE_NAME + " WHERE "
                + OrderItem.COLUMN_ORDER_ID + " = " + id;
        List<OrderItem> list = new ArrayList<OrderItem>();
        OrderItem orderItem = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                orderItem = new OrderItem();
				/*Retrieve one orderItem details
				and store it in orderItem object*/
                orderItem.setnOrderItemID(rs.getInt(OrderItem.COLUMN_ID));
                orderItem.setnOrderID(rs.getInt(OrderItem.COLUMN_ORDER_ID));
                orderItem.setnTableID(rs.getInt(OrderItem.COLUMN_TABLE_ID));
                orderItem.setnProductID(rs.getInt(OrderItem.COLUMN_PRODUCT_ID));
                orderItem.setsProductName(rs.getString(OrderItem.COLUMN_PRODUCT_NAME));
                orderItem.setnPrice(rs.getDouble(OrderItem.COLUMN_PRODUCT_PRICE));
                orderItem.setnQuantity(rs.getInt(OrderItem.COLUMN_QUANTITY));

                //add each orderItem to the list
                list.add(orderItem);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public OrderItem getOrderItem(long id) throws SQLException{
        String query = "SELECT * FROM " + OrderItem.TABLE_NAME + " WHERE " + OrderItem.COLUMN_ID + " = " + id;
        ResultSet rs = null;
        OrderItem orderItem = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()){
                orderItem = new OrderItem();
                orderItem.setnOrderItemID(rs.getInt(OrderItem.COLUMN_ID));
                orderItem.setnOrderID(rs.getInt(OrderItem.COLUMN_ORDER_ID));
                orderItem.setnTableID(rs.getInt(OrderItem.COLUMN_TABLE_ID));
                orderItem.setnProductID(rs.getInt(OrderItem.COLUMN_PRODUCT_ID));
                orderItem.setsProductName(rs.getString(OrderItem.COLUMN_PRODUCT_NAME));
                orderItem.setnPrice(rs.getDouble(OrderItem.COLUMN_PRODUCT_PRICE));
                orderItem.setnQuantity(rs.getInt(OrderItem.COLUMN_QUANTITY));
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return orderItem;
    }

    public void insertOrderItem(OrderItem orderItem) throws SQLException{
        String query = "INSERT INTO " + OrderItem.TABLE_NAME + "(" +
                OrderItem.COLUMN_ID + ", " + OrderItem.COLUMN_ORDER_ID + ", " +
                OrderItem.COLUMN_TABLE_ID + " , " +
                OrderItem.COLUMN_PRODUCT_ID + ", " + OrderItem.COLUMN_PRODUCT_NAME + ", " +
                OrderItem.COLUMN_PRODUCT_PRICE + ", " + OrderItem.COLUMN_QUANTITY + ")" +
                " VALUES (default, " + orderItem.getnOrderID() + " , " +
                orderItem.getnTableID() + " , " +
                orderItem.getnProductID() + " , '" + orderItem.getsProductName() + "' , " +
                orderItem.getnPrice() + " , " + orderItem.getnQuantity() + ")";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    public void updateOrderItem(OrderItem orderItem) throws SQLException{
        String query = "UPDATE " + OrderItem.TABLE_NAME + " SET " +
                OrderItem.COLUMN_ORDER_ID + " = '" + orderItem.getnOrderID() + "', "
                + OrderItem.COLUMN_TABLE_ID + " = '" + orderItem.getnTableID() + "', "
                + OrderItem.COLUMN_PRODUCT_ID + " = '" + orderItem.getnProductID() + "', "
                + OrderItem.COLUMN_PRODUCT_NAME + " = '" + orderItem.getsProductName() + "', "
                + OrderItem.COLUMN_QUANTITY + " = '" + orderItem.getnQuantity()
                + "' WHERE " + OrderItem.COLUMN_ID + " = " + String.valueOf(orderItem.getnOrderItemID());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    public void deleteOrderItem(OrderItem orderItem) throws SQLException{
        String query = "DELETE FROM " + OrderItem.TABLE_NAME + " WHERE " +
                OrderItem.COLUMN_ID + " = " + orderItem.getnOrderItemID();
        System.out.println(query);

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