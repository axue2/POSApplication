package com.ass3.axue2.posapplication.network;

import android.content.Context;

import com.ass3.axue2.posapplication.models.configuration.ConfigurationDatabaseHelper;
import com.ass3.axue2.posapplication.models.configuration.NetworkSetting;
import com.ass3.axue2.posapplication.models.saxpos.Stkcat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthonyxue on 6/07/2017.
 * Data Access Object for Stkcat
 */

public class StkcatDAO {
    private Connection connection;
    private Statement statement;

    private String mIP;
    private String mDB;
    private String mUser;
    private String mPassword;

    public StkcatDAO(Context context) {
        ConfigurationDatabaseHelper CDBHelper = new ConfigurationDatabaseHelper(context);
        NetworkSetting networkSetting = CDBHelper.GetNetworkSetting(1);
        mIP = networkSetting.getsIPAddress();
        mDB = networkSetting.getsDBName();
        mUser = networkSetting.getsUsername();
        mPassword = networkSetting.getsPassword();
    }

    public List<Stkcat> getStkcats() throws SQLException {
        String query = "SELECT * FROM " + Stkcat.TABLE_NAME;
        List<Stkcat> list = new ArrayList<Stkcat>();
        Stkcat stkcat = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection(mIP, mDB, mUser, mPassword);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                stkcat = new Stkcat();

                stkcat.setsID(rs.getString(Stkcat.COLUMN_ID));
                stkcat.setsName(rs.getString(Stkcat.COLUMN_NAME));
                stkcat.setsStatus(rs.getString(Stkcat.COLUMN_STATUS));

                list.add(stkcat);
            }
        } finally {
            DbUtil.close(rs);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        return list;
    }

}
