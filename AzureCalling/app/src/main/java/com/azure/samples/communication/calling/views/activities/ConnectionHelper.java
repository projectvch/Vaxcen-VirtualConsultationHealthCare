// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.samples.communication.calling.views.activities;


import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection con;
    String uname, pass, ip, port, database;
    @SuppressLint("NewApi")

    public Connection connectionclass() {
        ip = "181.215.242.87";
        database = "virtualconsultationhealthcare";
        uname = "vch";
        pass = "Grape@123";
        port = "19984";

        final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionurl = null;
        try {

            final Class<?> cls = Class.forName("net.sourceforge.jtds.jdbc.Driver");

            connectionurl = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databasename="
                    + database + ";user=" + uname + ";password=" + pass + ";";
            connection = DriverManager.getConnection(connectionurl);
        } catch (Exception ex) {
            Log.e("Error ", ex.getMessage());
        }
        return connection;


    }


}

