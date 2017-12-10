package com.ass3.axue2.posapplication.network;

import android.content.Context;

import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;
import com.ass3.axue2.posapplication.models.saxpos.Ab5ctl;
import com.ass3.axue2.posapplication.models.saxpos.Poqapa;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonyxue on 6/07/2017.
 */

public class Ab5ctlDAO {
    private Connection connection;
    private Statement statement;

    private String mIP;
    private String mDB;
    private String mUser;
    private String mPassword;

    public Ab5ctlDAO(Context context) {
        ConfigurationDatabaseHelper CDBHelper = new ConfigurationDatabaseHelper(context);
        NetworkSetting networkSetting = CDBHelper.GetNetworkSetting(1);
        mIP = networkSetting.getsIPAddress();
        mDB = networkSetting.getsDBName();
        mUser = networkSetting.getsUsername();
        mPassword = networkSetting.getsPassword();
    }

    // Gets the next invoice number to be used
    public BigDecimal getItemNo() throws SQLException{
        String query = "SELECT * FROM " + Ab5ctl.TABLE_NAME + " WHERE " + Ab5ctl.COLUMN_ID +
                " = " + "'AA'";
        ResultSet rs = null;
        BigDecimal itemNo = new BigDecimal(0);
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.next()){
                itemNo = rs.getBigDecimal(Ab5ctl.COLUMN_NEXT_INVOICE_NO);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        // increase item number by 1 to avoid conflict
        updateItemNo(itemNo);

        return itemNo;
    }

    public void updateItemNo(BigDecimal itemNo) throws SQLException{
        System.out.println("UPDATEITEMNO: " + itemNo);
        itemNo = itemNo.add(new BigDecimal(1));
        System.out.println("UPDATEITEMNO: " + itemNo);
        String query = "UPDATE " + Ab5ctl.TABLE_NAME + " SET "
                + Ab5ctl.COLUMN_NEXT_INVOICE_NO + " = " + itemNo +
                " WHERE " + Ab5ctl.COLUMN_ID + " = " + "'AA'";
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
