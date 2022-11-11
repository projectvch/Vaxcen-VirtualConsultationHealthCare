// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.samples.communication.calling.views.activities;

public class Dataholder {
    String name, age, gender, phone, address, email, disease, symptoms, pimage, date, register;
    Dataholder() {

    }

    public Dataholder(final String register, final String name, final String phone,
                      final String address, final String email, final String disease,
                      final String symptoms, final String date, final String pimage) {

        this.register = register;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.disease = disease;
        this.symptoms = symptoms;
        this.pimage = pimage;
        this.date = date;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(final String register) {
        this.register = register;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(final String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(final String disease) {
        this.disease = disease;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(final String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(final String pimage) {
        this.pimage = pimage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

}
