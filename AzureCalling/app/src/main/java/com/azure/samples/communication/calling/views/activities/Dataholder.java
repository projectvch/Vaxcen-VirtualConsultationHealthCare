// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.samples.communication.calling.views.activities;

public class Dataholder {
    String name, age, gender, phone, address, email, lastname, middlename, pimage, date, register, status,
        waitingdate, feaver, vaccine, allergic, hiv, immunoglob, pregnant;
    Dataholder() {

    }

    public Dataholder(final String register, final String age, final String name, final String phone,
                      final String address, final String email, final String lastname,
                      final String middlename, final String date, final String pimage,
                      final String status, final String waitingdate, final String feaver,
                      final String vaccine, final String allergic, final String hiv,
                      final String immunoglob, final String pregnant) {

        this.register = register;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.lastname = lastname;
        this.middlename = middlename;
        this.pimage = pimage;
        this.date = date;
        this.status = status;
        this.waitingdate = waitingdate;
        this.feaver = feaver;
        this.vaccine = vaccine;
        this.allergic = allergic;
        this.hiv = hiv;
        this.immunoglob = immunoglob;
        this.pregnant = pregnant;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(final String middlename) {
        this.middlename = middlename;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getWaitingdate() {
        return waitingdate;
    }

    public void setWaitingdate(final String waitingdate) {
        this.waitingdate = waitingdate;
    }
    //Added by Deepak
    public String getFeaver() {
        return feaver;
    }
    public void setFeaver(final String feaver) {
        this.feaver = feaver;
    }

    public String getVaccine() {
        return vaccine;
    }
    public void setVaccine(final String vaccine) {
        this.vaccine = vaccine;
    }

    public String getAllergic() {
        return allergic;
    }
    public void setAllergic(final String allergic) {
        this.allergic = allergic;
    }

    public String getHiv() {
        return hiv;
    }
    public void setHiv(final String hiv) {
        this.hiv = hiv;
    }

    public String getImmunoglob() {
        return immunoglob;
    }
    public void setImmunoglob(final String immunoglob) {
        this.immunoglob = immunoglob;
    }

    public String getPregnant() {
        return pregnant;
    }
    public void setPregnant(final String pregnant) {
        this.pregnant = pregnant;
    }

}
