// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.azure.samples.communication.calling.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class MyAdapter3 extends FirebaseRecyclerAdapter< Dataholder, MyAdapter3.Myviewholder> {

    public MyAdapter3(@NonNull final FirebaseRecyclerOptions<Dataholder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final Myviewholder holder, final int position,
                                    @NonNull final Dataholder model) {
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.lastname.setText(model.getLastname());
        holder.middlename.setText(model.getMiddlename());
        holder.phone.setText(model.getPhone());
        holder.age.setText(model.getAge() + " years");
        holder.address.setText(model.getAddress());
        holder.date.setText(model.getDate());
        holder.register.setText(model.getRegister());
        holder.feaver.setText(model.getFeaver());
        holder.vaccine.setText(model.getVaccine());
        holder.hiv.setText(model.getHiv());
        holder.allergic.setText(model.getAllergic());
        holder.immunoglob.setText(model.getImmunoglob());
        holder.pregnant.setText(model.getPregnant());
        Glide.with(holder.img.getContext()).load(model.getPimage()).into(holder.img);

    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singledata3, parent, false);
        return new Myviewholder(view);
    }

    class Myviewholder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView edit;
        CheckBox deleteicon;
        TextView name, phone, email, lastname, middlename, age, address, date, register,
                feaver, vaccine, allergic, hiv, immunoglob, pregnant;
        Myviewholder(@NonNull final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nametext);
            phone = (TextView) itemView.findViewById(R.id.phonetext);
            email = (TextView) itemView.findViewById(R.id.emailtext);
            age = (TextView) itemView.findViewById(R.id.agetext);
            lastname = (TextView) itemView.findViewById(R.id.lnametext);
            middlename = (TextView) itemView.findViewById(R.id.mnametext);
            address = (TextView) itemView.findViewById(R.id.addresstext);
            date = (TextView) itemView.findViewById(R.id.datetext);
            register = (TextView) itemView.findViewById(R.id.registertext);
            feaver = (TextView) itemView.findViewById(R.id.feavertext);
            vaccine = (TextView) itemView.findViewById(R.id.vaccinetext);
            allergic = (TextView) itemView.findViewById(R.id.allergictext);
            hiv = (TextView) itemView.findViewById(R.id.hivtext);
            immunoglob = (TextView) itemView.findViewById(R.id.immunoglobttext);
            pregnant = (TextView) itemView.findViewById(R.id.pregnanttext);
        }
    }
}
