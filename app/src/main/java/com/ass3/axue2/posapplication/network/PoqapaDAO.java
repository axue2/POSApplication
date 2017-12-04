package com.ass3.axue2.posapplication.network;

import android.content.Context;

import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;
import com.ass3.axue2.posapplication.models.saxpos.Ab5ctl;
import com.ass3.axue2.posapplication.models.saxpos.Poqapa;
import com.ass3.axue2.posapplication.models.saxpos.Poqapd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonyxue on 6/07/2017.
 */

public class PoqapaDAO {
    private Connection connection;
    private Statement statement;

    private String mIP;
    private String mDB;
    private String mUser;
    private String mPassword;

    public PoqapaDAO(Context context) {
        ConfigurationDatabaseHelper CDBHelper = new ConfigurationDatabaseHelper(context);
        NetworkSetting networkSetting = CDBHelper.GetNetworkSetting(1);
        mIP = networkSetting.getsIPAddress();
        mDB = networkSetting.getsDBName();
        mUser = networkSetting.getsUsername();
        mPassword = networkSetting.getsPassword();
    }

    public List<Poqapa> getPoqapas() throws SQLException {
        String query = "SELECT * FROM " + Poqapa.TABLE_NAME;
        List<Poqapa> list = new ArrayList<Poqapa>();
        Poqapa poqapa = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                poqapa = new Poqapa();
                poqapa.setsID(rs.getString(Poqapa.COLUMN_ID));
                poqapa.setsOrderNo(rs.getString(Poqapa.COLUMN_ORDER_NO));
                poqapa.setsInvoiceDate(rs.getString(Poqapa.COLUMN_INVOICE_DATE));
                poqapa.setsTotInvoiceAmt(rs.getDouble(Poqapa.COLUMN_TOT_INVOICE_AMT));
                poqapa.setsTotInvoiceBal(rs.getString(Poqapa.COLUMN_TOT_INVOICE_BAL));
                poqapa.setsThisPayStatus(rs.getString(Poqapa.COLUMN_THIS_PAY_STATUS));
                poqapa.setsAuthorisor(rs.getString(Poqapa.COLUMN_AUTHORISOR));
                poqapa.setsAuthorisorDate(rs.getString(Poqapa.COLUMN_AUTHORISOR_DATE));
                list.add(poqapa);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public List<Poqapa> getEatInPoqapas() throws SQLException {
        String query = "SELECT * FROM " + Poqapa.TABLE_NAME +
                " WHERE " + Poqapa.COLUMN_THIS_PAY_STATUS + " = '1'";
        List<Poqapa> list = new ArrayList<Poqapa>();
        Poqapa poqapa = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                poqapa = new Poqapa();
                poqapa.setsID(rs.getString(Poqapa.COLUMN_ID));
                poqapa.setsOrderNo(rs.getString(Poqapa.COLUMN_ORDER_NO));
                poqapa.setsInvoiceDate(rs.getString(Poqapa.COLUMN_INVOICE_DATE));
                poqapa.setsTotInvoiceAmt(rs.getDouble(Poqapa.COLUMN_TOT_INVOICE_AMT));
                poqapa.setsTotInvoiceBal(rs.getString(Poqapa.COLUMN_TOT_INVOICE_BAL));
                poqapa.setsThisPayStatus(rs.getString(Poqapa.COLUMN_THIS_PAY_STATUS));
                poqapa.setsAuthorisor(rs.getString(Poqapa.COLUMN_AUTHORISOR));
                poqapa.setsAuthorisorDate(rs.getString(Poqapa.COLUMN_AUTHORISOR_DATE));
                list.add(poqapa);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

    public Poqapa getPoqapa(String id) throws SQLException{
        String query = "SELECT * FROM " + Poqapa.TABLE_NAME + " WHERE " + Poqapa.COLUMN_ID + " = '" + id
                + "' AND " + Poqapa.COLUMN_DEPARTMENT_ID + " = '0003'";
        System.out.println(query);
        ResultSet rs = null;
        Poqapa poqapa = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()){
                poqapa = new Poqapa();
                poqapa.setsID(rs.getString(Poqapa.COLUMN_ID));
                poqapa.setsOrderNo(rs.getString(Poqapa.COLUMN_ORDER_NO));
                poqapa.setsInvoiceDate(rs.getString(Poqapa.COLUMN_INVOICE_DATE));
                poqapa.setsTotInvoiceAmt(rs.getDouble(Poqapa.COLUMN_TOT_INVOICE_AMT));
                poqapa.setsTotInvoiceBal(rs.getString(Poqapa.COLUMN_TOT_INVOICE_BAL));
                poqapa.setsThisPayStatus(rs.getString(Poqapa.COLUMN_THIS_PAY_STATUS));
                poqapa.setsAuthorisor(rs.getString(Poqapa.COLUMN_AUTHORISOR));
                poqapa.setsAuthorisorDate(rs.getString(Poqapa.COLUMN_AUTHORISOR_DATE));
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return poqapa;
    }

    public long insertPoqapa(Poqapa poqapa) throws SQLException{
        long id = 0;

        String query = "INSERT INTO " + Poqapa.TABLE_NAME + "(" +
                Poqapa.COLUMN_ID + ", " + Poqapa.COLUMN_ORDER_NO + ", " +
                Poqapa.COLUMN_INVOICE_DATE + ", " + Poqapa.COLUMN_TOT_INVOICE_AMT + ", " +
                Poqapa.COLUMN_TOT_INVOICE_BAL + ", " + Poqapa.COLUMN_THIS_PAY_STATUS + ", " +
                Poqapa.COLUMN_AUTHORISOR + ", " + Poqapa.COLUMN_DEPARTMENT_ID + ", " +
                Poqapa.COLUMN_CUSTOMER_ID + ", " + Poqapa.COLUMN_CID_LINE + ")" +
                " VALUES ('" + poqapa.getsID() + "' , '" + poqapa.getsOrderNo() + "' , '" +
                poqapa.getsInvoiceDate() + "' , '" + poqapa.getsTotInvoiceAmt() + "' , '" +
                poqapa.getsTotInvoiceBal() + "' , '" + poqapa.getsThisPayStatus() + "' , '" +
                poqapa.getsAuthorisor() + "' , '" + poqapa.getsDepartmentID() + "' , '" +
                poqapa.getsCID() + "' , '" + poqapa.getsCIDLine() + "')";
        System.out.println(query);
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

    public void updatePoqapa(Poqapa poqapa) throws SQLException{

        String query = "UPDATE " + Poqapa.TABLE_NAME + " SET "
                + Poqapa.COLUMN_ORDER_NO + " = '" + poqapa.getsOrderNo()
                + "', " + Poqapa.COLUMN_INVOICE_DATE + " = '" + poqapa.getsInvoiceDate()
                + "', " + Poqapa.COLUMN_TOT_INVOICE_AMT + " = '" + poqapa.getsTotInvoiceAmt()
                + "', " + Poqapa.COLUMN_TOT_INVOICE_BAL + " = '" + poqapa.getsTotInvoiceBal()
                + "', " + Poqapa.COLUMN_THIS_PAY_STATUS + " = '" + poqapa.getsThisPayStatus()
                + "', " + Poqapa.COLUMN_AUTHORISOR + " = '" + poqapa.getsAuthorisor() + "'" +
                " WHERE " + Poqapa.COLUMN_ID + " = " + poqapa.getsID();
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
        String query = "SELECT 1 FROM " + Poqapa.TABLE_NAME;
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
