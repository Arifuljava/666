package com.grozziie.adminsection;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class HelpLineAdapter extends RecyclerView.Adapter<HelpLineAdapter.myview> {
    public List<Help_Model> data;
    FirebaseFirestore firebaseFirestore;

    public HelpLineAdapter(List<Help_Model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.calling,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, final int position) {
        holder.customer_name.setText(data.get(position).getName());
        holder.customer_number.setText(data.get(position).getPhone());
        holder.customer_area.setText("Any Area");
        firebaseFirestore= FirebaseFirestore.getInstance();
        holder.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String option[]={"Calling","Delete"};
                AlertDialog.Builder warning=new AlertDialog.Builder(v.getContext());

                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Calling")
                        .setMessage("Are you want to call ?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String s="tel:"+data.get(position).getPhone();
                        Intent intent33=new Intent(Intent.ACTION_DIAL);
                        intent33.setData(Uri.parse(s));
                        v.getContext().startActivity(intent33);

                    }
                }).create().show();
            }
        });

        holder.reay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Calling")
                        .setMessage("Are you want to call ?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String s="tel:"+data.get(position).getPhone();
                        Intent intent33=new Intent(Intent.ACTION_DIAL);
                        intent33.setData(Uri.parse(s));
                        view.getContext().startActivity(intent33);

                    }
                }).create().show();
            }
        });

        holder.reay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Delete")
                        .setMessage("Are you want to delete ?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        final KProgressHUD progressDialog=  KProgressHUD.create(view.getContext())
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setLabel("Please wait")
                                .setCancellable(false)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.5f)
                                .show();
                        firebaseFirestore.collection("ALl_Subadmin")
                                .document(data.get(position).getUuid())
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toasty.success(view.getContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                            view.getContext().startActivity(new Intent(view.getContext(),HomeActivity.class));
                                        }
                                    }
                                });

                    }
                }).create().show();
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout;
        CardView reay;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            customer_number=itemView.findViewById(R.id.customer_number);
            customer_area=itemView.findViewById(R.id.customer_area);
            logout=itemView.findViewById(R.id.logout);
            reay=itemView.findViewById(R.id.reay);
        }
    }
}