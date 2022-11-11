// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

public class Model {
    private String sname;
    private String ssubject;
    private int id;
    // Constructor

    public Model(final String sname, final String ssubject) {
        this.sname = sname;
        this.ssubject = ssubject;
    }
    // getter and setter

    public String getSname() {
        return sname;
    }

    public void setSname(final String sname) {
        this.sname = sname;
    }

    public String getSsubject() {
        return ssubject;
    }

    public void setSsubject(final String ssubject) {
        this.ssubject = ssubject;
    }


    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
}

