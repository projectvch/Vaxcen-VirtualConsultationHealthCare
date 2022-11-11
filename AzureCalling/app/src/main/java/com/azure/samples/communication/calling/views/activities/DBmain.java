// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBmain extends SQLiteOpenHelper {
    public static final String DBNAME = "vch";
    public static final String TABLENAME = "patientinfo";
    public static final int VER = 1;
    public DBmain(@Nullable final Context context) {
        super(context, DBNAME, null, VER);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        final String query = "create table " + TABLENAME + "(id integer primary key, name text, subject text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        final String query = "drop table if exists " + TABLENAME + "";
        db.execSQL(query);

    }
}
