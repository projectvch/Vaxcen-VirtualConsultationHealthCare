// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.azure.samples.communication.calling.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter4 extends FirebaseRecyclerAdapter<Dataholder, MyAdapter4.Myviewholder> {

    public MyAdapter4(@NonNull final FirebaseRecyclerOptions<Dataholder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final Myviewholder holder, final int position,
                                    @NonNull final Dataholder model) {
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.disease.setText(model.getDisease());
        holder.phone.setText(model.getPhone());
        holder.symptoms.setText(model.getSymptoms());
        holder.address.setText(model.getAddress());
        holder.date.setText(model.getDate());
        holder.register.setText(model.getRegister());
        Glide.with(holder.img.getContext()).load(model.getPimage()).into(holder.img);


        final Map<String, Object> map = new HashMap<>();
        final Map<String, Object> map1 = new HashMap<>();

    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singledata4, parent, false);
        return new Myviewholder(view);
    }

    class Myviewholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, phone, email, disease, symptoms, address, date, register;
        Myviewholder(@NonNull final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nametext);
            phone = (TextView) itemView.findViewById(R.id.phonetext);
            email = (TextView) itemView.findViewById(R.id.emailtext);
            symptoms = (TextView) itemView.findViewById(R.id.symptomstext);
            disease = (TextView) itemView.findViewById(R.id.diseasetext);
            address = (TextView) itemView.findViewById(R.id.address);
            date = (TextView) itemView.findViewById(R.id.date);
            register = (TextView) itemView.findViewById(R.id.register);
        }
    }
}
