// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;


public class DataholderSignup {
    String roll;
    String name, email, password, phone;

    public  DataholderSignup(final String name, final String email, final String password,
                             final String phone, final String roll) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.roll = roll;
    }

    public static void setSpinner() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(final String roll) {
        this.roll = roll;
    }
}

