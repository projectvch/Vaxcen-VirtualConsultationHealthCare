// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;

public class MyAdapter2 extends FirebaseRecyclerAdapter<Dataholder, MyAdapter2.Myviewholder> {

    static  String cordname;
    static  String patname;
    int maxid = 0;

    public MyAdapter2(@NonNull final FirebaseRecyclerOptions<Dataholder> options) {
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

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder ab = new AlertDialog.Builder(view.getContext());
                //Added by Deepak
                cordname = model.register;
                patname  = model.name;
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_intro_view))
                        .setExpanded(true, 1600)
                        .create();
                Log.d("value", String.valueOf(cordname));
                Log.d("value", String.valueOf(patname));
                ab.setMessage("Initiate Call");
                ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {

                        final Intent in = new Intent(view.getContext(), IntroViewActivity.class);
                        view.getContext().startActivity(in);
                    }
                });
                ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {

                    }
                });
                ab.create().show();

            }
        });


        holder.deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want mark this patient as completed ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {


                        //final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        //final String completecalldate = String.valueOf(timestamp.getTime());
                        FirebaseDatabase.getInstance()
                                .getReference().child("completeusers")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull final DataSnapshot snapshot) {
                                        maxid = (int) snapshot.getChildrenCount();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull final DatabaseError error) {

                                    }
                                });
                        FirebaseDatabase.getInstance().getReference().child("newcompleteusers")
                                .child(String.valueOf(maxid + 1)).child("date").setValue(model.date);

                        FirebaseDatabase.getInstance().getReference().child("newcompleteusers")
                                .child(String.valueOf(maxid + 1)).child("img").setValue(model.pimage);

                        FirebaseDatabase.getInstance().getReference().child("newcompleteusers")
                                .child(String.valueOf(maxid + 1)).child("address").setValue(model.address);

                        FirebaseDatabase.getInstance().getReference().child("newcompleteusers")
                                .child(String.valueOf(maxid + 1)).child("email").setValue(model.email);

                        FirebaseDatabase.getInstance().getReference().child("newcompleteusers")
                                .child(String.valueOf(maxid + 1)).child("disease").setValue(model.disease);

                        FirebaseDatabase.getInstance().getReference().child("newcompleteusers")
                                .child(String.valueOf(maxid + 1)).child("symptoms").setValue(model.symptoms);

                        FirebaseDatabase.getInstance().getReference().child("newcompleteusers")
                                .child(String.valueOf(maxid + 1)).child("register").setValue(model.register);

                        FirebaseDatabase.getInstance().getReference().child("newcompleteusers")
                                .child(String.valueOf(maxid + 1)).child("phone").setValue(model.phone);

                        FirebaseDatabase.getInstance().getReference().child("newcompleteusers")
                                .child(String.valueOf(maxid + 1)).child("name").setValue(model.name)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(final Void aVoid) {
                                        FirebaseDatabase.getInstance().getReference().child("newcomuser")
                                                .child(getRef(position).getKey()).removeValue();
                                    }
                                });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {

                    }
                });

                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singledata2, parent, false);
        return new Myviewholder(view);
    }

    class Myviewholder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView edit;
        ImageView deleteicon;
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
            edit = (ImageView) itemView.findViewById(R.id.editicon);
            deleteicon = (ImageView) itemView.findViewById(R.id.deleteicon);
        }
    }
}
