package com.ass3.axue2.posapplication.network;

import android.content.Context;

import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;
import com.ass3.axue2.posapplication.models.saxpos.Poqapa;
import com.ass3.axue2.posapplication.models.saxpos.Poqapd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PoqapdDAO {
    private Connection connection;
    private Statement statement;

    private String mIP;
    private String mDB;
    private String mUser;
    private String mPassword;

    public PoqapdDAO(Context context) {
        ConfigurationDatabaseHelper CDBHelper = new ConfigurationDatabaseHelper(context);
        NetworkSetting networkSetting = CDBHelper.GetNetworkSetting(1);
        mIP = networkSetting.getsIPAddress();
        mDB = networkSetting.getsDBName();
        mUser = networkSetting.getsUsername();
        mPassword = networkSetting.getsPassword();
    }

    public List<Poqapd> getPoqapds() throws SQLException {
        String query = "SELECT * FROM " + Poqapd.TABLE_NAME;
        List<Poqapd> list = new ArrayList<Poqapd>();
        Poqapd poqapd = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                poqapd = new Poqapd();
                poqapd.setsInvoiceNo(rs.getString(Poqapd.COLUMN_INVOICE_NO));
                poqapd.setnDetailNo(rs.getInt(Poqapd.COLUMN_DETAIL_NO));
                poqapd.setsCID(rs.getString(Poqapd.COLUMN_CID));
                poqapd.setsItemID(rs.getString(Poqapd.COLUMN_ITEM_ID));
                poqapd.setnPrice(rs.getDouble(Poqapd.COLUMN_PRICE));
                poqapd.setnInvoiceQty(rs.getInt(Poqapd.COLUMN_INVOICE_QTY));
                poqapd.setnInvoiceAmt(rs.getDouble(Poqapd.COLUMN_INVOICE_AMT));
                poqapd.setsItemName(rs.getString(Poqapd.COLUMN_NEW_ITEM_ID));
                list.add(poqapd);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public void insertPoqapd(Poqapd poqapd) throws SQLException{
        String query = "INSERT INTO " + Poqapd.TABLE_NAME + "(" +
                Poqapd.COLUMN_INVOICE_NO + ", " + Poqapd.COLUMN_DETAIL_NO + ", " +
                Poqapd.COLUMN_CID + ", " + Poqapd.COLUMN_ITEM_ID + ", " +
                Poqapd.COLUMN_PRICE + ", " + Poqapd.COLUMN_INVOICE_QTY + ", " +
                Poqapd.COLUMN_INVOICE_AMT + ", " + Poqapd.COLUMN_NEW_ITEM_ID + ", " +
                Poqapd.COLUMN_DEPARTMENT_ID + ")" +
                " VALUES ('" + poqapd.getsInvoiceNo() + "' , " + poqapd.getnDetailNo() + " , '" +
                poqapd.getsCID() + "' , '" + poqapd.getsItemID() + "' , " +
                poqapd.getnPrice() + " , " + poqapd.getnInvoiceQty() + ", " +
                poqapd.getnInvoiceAmt() + " , '" + poqapd.getsItemName() + "' , " +
                "'0003'" + ")";
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    public void updatePoqapd(Poqapd poqapd) throws SQLException{
        String query = "UPDATE " + Poqapd.TABLE_NAME + " SET " +
                Poqapd.COLUMN_CID + " = '" + poqapd.getsCID() + "', "
                + Poqapd.COLUMN_ITEM_ID + " = '" + poqapd.getsItemID() + "', "
                + Poqapd.COLUMN_PRICE + " = '" + poqapd.getnPrice() + "', "
                + Poqapd.COLUMN_INVOICE_QTY + " = '" + poqapd.getnInvoiceQty() + "', "
                + Poqapd.COLUMN_INVOICE_AMT + " = '" + poqapd.getnInvoiceAmt() + "', "
                + Poqapd.COLUMN_NEW_ITEM_ID + " = '" + poqapd.getsItemName()
                + "' WHERE " + Poqapd.COLUMN_INVOICE_NO + " = " + poqapd.getsInvoiceNo()
                + " AND " + Poqapd.COLUMN_DETAIL_NO + " = " + poqapd.getnDetailNo()
                + " AND " + Poqapd.COLUMN_DEPARTMENT_ID + " = " + "'0003'";
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            statement.executeUpdate(query);

        }finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
    }

    public void deletePoqapd(Poqapd poqapd) throws SQLException{
        String query = "DELETE FROM " + Poqapd.TABLE_NAME + " WHERE " +
                Poqapd.COLUMN_INVOICE_NO + " = " + poqapd.getsInvoiceNo() +
                " AND " + Poqapd.COLUMN_DETAIL_NO + " = " + poqapd.getnDetailNo() +
                " AND " + Poqapd.COLUMN_DEPARTMENT_ID + " = " + "'0003'";
        System.out.println(query);

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
