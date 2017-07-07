package com.ass3.axue2.posapplication.network;

import android.content.Context;

import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;
import com.ass3.axue2.posapplication.models.saxpos.Stkite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonyxue on 6/07/2017.
 *
 */

public class StkiteDAO {
    private Connection connection;
    private Statement statement;

    private String mIP;
    private String mDB;
    private String mUser;
    private String mPassword;

    public StkiteDAO(Context context) {
        ConfigurationDatabaseHelper CDBHelper = new ConfigurationDatabaseHelper(context);
        NetworkSetting networkSetting = CDBHelper.GetNetworkSetting(1);
        mIP = networkSetting.getsIPAddress();
        mDB = networkSetting.getsDBName();
        mUser = networkSetting.getsUsername();
        mPassword = networkSetting.getsPassword();
    }

    public List<Stkite> getStkites() throws SQLException {
        String query = "SELECT * FROM " + Stkite.TABLE_NAME;
        List<Stkite> list = new ArrayList<Stkite>();
        Stkite stkite = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                stkite = new Stkite();
                stkite.setsID(rs.getString(Stkite.COLUMN_ID));
                stkite.setsName(rs.getString(Stkite.COLUMN_NAME));
                stkite.setsStatus(rs.getString(Stkite.COLUMN_STATUS));
                stkite.setsGroupBy(rs.getString(Stkite.COLUMN_GROUP_BY));
                stkite.setsSalesPrice(rs.getDouble(Stkite.COLUMN_SALES_PRICE));
                list.add(stkite);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

}
