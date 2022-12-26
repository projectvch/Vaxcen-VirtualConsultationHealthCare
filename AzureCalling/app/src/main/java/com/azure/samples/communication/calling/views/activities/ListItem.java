// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.


package com.azure.samples.communication.calling.views.activities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListItem {
    Connection connect;
    String connectionresult = "";
    Boolean issuccess = false;
    final String loccordname = VCHLoginActivity.regby;

    public List<Map<String, String>>getlist() {
        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();
        try {
            final ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connectionclass();
            if (connect != null) {
                final String query = "select * from dbo.tokenstorage where coordinatorname = '" + loccordname
                       +  "'  order by srlno desc";
                final Statement statement = connect.createStatement();
                final ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    final Map<String, String> dtname = new HashMap<String, String>();
                    dtname.put("cordinatorname", resultSet.getString("coordinatorname"));
                    dtname.put("patientname", resultSet.getString("patientname"));
                    data.add(dtname);
                }
                connectionresult = "Success";
                issuccess = true;
                connect.close();
            } else {
                connectionresult = "Failed";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }


}
