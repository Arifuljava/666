package com.grozziie.adminsection;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AdapterSub1 extends RecyclerView.Adapter<AdapterSub1.myview> {
    public List<Sublime> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public AdapterSub1(List<Sublime> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subbb,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, final int position) {
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        holder.customer_name.setText(data.get(position).getName());
        holder.customer_number.setText(data.get(position).getEmail());
        holder.logout.setText("Active Account");
        holder.card_view8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String list[]={"Balance","Total Refer","Delete User","User Details"};
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Options")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==3) {
                                    firebaseFirestore.collection("User2")
                                            .document(data.get(position).getUsername()+"@gmail.com")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if(task.getResult().exists()) {
                                                            String username=task.getResult().getString("username");
                                                            String name=task.getResult().getString("name");
                                                            String number=task.getResult().getString("number");
                                                            String pass=task.getResult().getString("pass");
                                                            String message="Username : "+username+"\nFull Name : "+name+"\n" +
                                                                    "Phone Number : "+number+"\nPassword : "+pass;
                                                            AlertDialog.Builder builder1=new AlertDialog.Builder(view.getContext());
                                                            builder1.setTitle("User details")
                                                                    .setMessage(message)
                                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                        }
                                                                    }).create().show();;
                                                        }
                                                    }
                                                }
                                            });
                                }
                                else if (which==0) {
                                    firebaseFirestore.collection("All_UserID")
                                            .document(data.get(position).getUsername()+"@gmail.com")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().exists()) {
                                                            firebaseFirestore.collection("Users")
                                                                    .document(task.getResult().getString("uuid"))
                                                                    .collection("Main_Balance")
                                                                    .document(data.get(position).getUsername()+"@gmail.com")
                                                                    .get()
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            if (task.isSuccessful()) {
                                                                                if (task.getResult().exists()) {
                                                                                    AlertDialog.Builder builder1=new AlertDialog.Builder(view.getContext());

                                                                                    builder1 .setTitle("Wallet")
                                                                                            .setMessage("Current Balance : "+task.getResult().getString("main_balance"))
                                                                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(DialogInterface dialog, int which) {
                                                                                                    dialog.dismiss();
                                                                                                }
                                                                                            }).create().show();
                                                                                }
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                }
                                            });
                                }
                                else if(which==1) {
                                    firebaseFirestore.collection("Persions")
                                            .document(data.get(position).getUsername()+"@gmail.com")
                                            .collection("List")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    int count = 0;
                                                    for (DocumentSnapshot document : task.getResult()) {
                                                        count++;
                                                    }
                                                    AlertDialog.Builder builder1=new AlertDialog.Builder(view.getContext());

                                                    builder1 .setTitle("Refer")
                                                            .setMessage("Total Refer : "+count)
                                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            }).create().show();
                                                }
                                            });
                                }
                                else if(which==2) {
                                    AlertDialog.Builder builder1=new AlertDialog.Builder(view.getContext());

                                    builder1 .setTitle("Confirmation")
                                            .setMessage("Are  you want to delete this user?")
                                            .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            firebaseFirestore.collection("User2")
                                                    .document(data.get(position).getUsername()+"@gmail.com")
                                                    .delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(view.getContext(), "Done", Toast.LENGTH_SHORT).show();
                                                                view.getContext().startActivity(new Intent(view.getContext(),HomeActivity.class));
                                                            }
                                                        }
                                                    });
                                        }
                                    }).create().show();
                                }
                            }
                        }).create().show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout,customer_area3,customer_area8;
        CardView card_view8;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            customer_number=itemView.findViewById(R.id.customer_number);
            logout=itemView.findViewById(R.id.logout);
            card_view8=itemView.findViewById(R.id.card_view8);
        }
    }
}

