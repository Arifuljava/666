package com.grozziie.adminsection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class PackageInAdapter extends RecyclerView.Adapter<PackageInAdapter.myview> {
    public List<Package> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public PackageInAdapter(List<Package> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.papa, parent, false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, final int position) {
        firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();

        holder.customer_name.setText("Username : " + data.get(position).getUsername());
        holder.customer_number.setText("Amount  : " + data.get(position).getPackage_price());
        holder.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Are You  Want To Delete This Request")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        final KProgressHUD progressDialog=  KProgressHUD.create(view.getContext())
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setLabel("Delete")
                                .setCancellable(false)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.5f)
                                .show();
                        firebaseFirestore.collection("Admin_PackageRequest__1")
                                .document(data.get(position).getUuid())
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toasty.success(view.getContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                        }
                                    }
                                });

                    }
                });
            }
        });
        holder.customer_area.setText(data.get(position).getDate());
        holder.card_view8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Balance")
                        .setMessage("Are you want to give balance this user")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final KProgressHUD progressDialog=  KProgressHUD.create(view.getContext())
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setLabel("Please wait")
                                .setCancellable(false)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.5f)
                                .show();
                        firebaseFirestore.collection("Users")
                                .document(data.get(position).getUser_uuid())
                                .collection("Main_Balance")
                                .document(data.get(position).getUseremail())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().exists()) {
                                                String purches_balance=task.getResult().getString("main_balance");
                                                String giving_balance=task.getResult().getString("giving_balance");
                                                String balance=data.get(position).getPackage_price();
                                                int total_p=Integer.parseInt(purches_balance)+Integer.parseInt(balance);

                                                int total_g=Integer.parseInt(giving_balance)+Integer.parseInt(balance);
                                                firebaseFirestore.collection("Users")
                                                        .document(data.get(position).getUser_uuid())
                                                        .collection("Main_Balance")
                                                        .document(data.get(position).getUseremail())
                                                        .update("main_balance",String.valueOf(total_p),
                                                                "giving_balance",String.valueOf(total_g))
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    firebaseFirestore.collection("MyPackage")
                                                                            .document(data.get(position).getUseremail())
                                                                            .collection("List")
                                                                            .document(data.get(position).getUuid())
                                                                            .update("status","Active")
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        firebaseFirestore.collection("Subadmin")
                                                                                                .document("Packages")
                                                                                                .collection("101")
                                                                                                .document(data.get(position).getUuid())
                                                                                                .delete()
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        if (task.isSuccessful()) {
                                                                                                            Calendar calendar = Calendar.getInstance();
                                                                                                            String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                                                                            String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                                                                            Package_user package_user=new Package_user(data.get(position).getUsername(),
                                                                                                                    data.get(position).getPackage_name(),current1,"Active");
                                                                                                            firebaseFirestore.collection("Package_User")
                                                                                                                    .add(package_user)
                                                                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                progressDialog.dismiss();
                                                                                                                                Toasty.success(view.getContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                                                                                                                view.getContext().startActivity(new Intent(view.getContext(),HomeActivity.class));
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });

                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });

                                            }
                                        }
                                    }
                                });


                    }
                }).create().show();
                 */
                String[] option={"Transcation Details","Active Account","Delete Request"};
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Options");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i==0) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                            String message=data.get(position).getTransacation()+""+data.get(position).getUsernumber()+""+data.get(position)
                                    .getPackage_price();
                            String aaa="Package Name : "+data.get(position)
                                    .getPackage_name()+"\nTranscation balance  : "+data.get(position)
                                    .getPackage_price()+"\nTranscation Method : "+data.get(position).getPayment_methode()+"\nPayment Number : "+
                                    data.get(position).getUsernumber()+"\nTranscation ID : "+data.get(position).getTransacation();
                            builder.setTitle("Transcation Details")
                                    .setMessage(aaa)
                                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).create().show();
                        }
                        else if (i==1) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                            builder.setTitle("Balance")
                                    .setMessage("Are you want to give balance this user")
                                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String[] optionsss={"Package Balance"};

                                    AlertDialog.Builder builder_testing=new AlertDialog.Builder(view.getContext());
                                    builder_testing.setItems(optionsss, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            if (which==0) {
                                                final KProgressHUD progressDialog=  KProgressHUD.create(view.getContext())
                                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                        .setLabel("Please wait")
                                                        .setCancellable(false)
                                                        .setAnimationSpeed(2)
                                                        .setDimAmount(0.5f)
                                                        .show();
                                                firebaseFirestore.collection("Users")
                                                        .document(data.get(position).getUser_uuid())
                                                        .collection("Main_Balance")
                                                        .document(data.get(position).getUseremail())
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (task.isSuccessful()) {
                                                                    if (task.getResult().exists()) {
                                                                        String purches_balance=task.getResult().getString("purches_balance");
                                                                        String giving_balance=task.getResult().getString("giving_balance");
                                                                        String balance=data.get(position).getPackage_price();
                                                                        double total_p=Double.parseDouble(purches_balance)+Double.parseDouble(balance);

                                                                        double total_g=Double.parseDouble(giving_balance)+Double.parseDouble(balance);
                                                                        firebaseFirestore.collection("Users")
                                                                                .document(data.get(position).getUser_uuid())
                                                                                .collection("Main_Balance")
                                                                                .document(data.get(position).getUseremail())
                                                                                .update("purches_balance",String.valueOf(total_p)
                                                                                )
                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            firebaseFirestore.collection("MyPackage")
                                                                                                    .document(data.get(position).getUseremail())
                                                                                                    .collection("List")
                                                                                                    .document(data.get(position).getUuid())
                                                                                                    .update("status","Active")
                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                            if (task.isSuccessful()) {
                                                                                                                firebaseFirestore.collection("Admin_PackageRequest__1")
                                                                                                                        .document(data.get(position).getUuid())
                                                                                                                        .delete()
                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                if (task.isSuccessful()) {
                                                                                                                                    Calendar calendar = Calendar.getInstance();
                                                                                                                                    String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                                                                                                    String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                                                                                                    Package_user package_user=new Package_user(data.get(position).getUsername(),
                                                                                                                                            data.get(position).getPackage_name(),current1,"Active");
                                                                                                                                    firebaseFirestore.collection("Package_User")
                                                                                                                                            .add(package_user)
                                                                                                                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                                                                                @Override
                                                                                                                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                                                                    if (task.isSuccessful()) {
                                                                                                                                                        progressDialog.dismiss();
                                                                                                                                                        Toasty.success(view.getContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                                                                                                                                        view.getContext().startActivity(new Intent(view.getContext(),HomeActivity.class));
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            });

                                                                                                                                }
                                                                                                                            }
                                                                                                                        });
                                                                                                            }
                                                                                                        }
                                                                                                    });
                                                                                        }
                                                                                    }
                                                                                });

                                                                    }
                                                                }
                                                            }
                                                        });

                                            }

                                        }
                                    }).create().show();


                                }
                            }).create().show();
                        }
                        else if (i==2) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                            builder.setTitle("Are You  Want To Delete This Request")
                                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    final KProgressHUD progressDialog=  KProgressHUD.create(view.getContext())
                                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                            .setLabel("Delete")
                                            .setCancellable(false)
                                            .setAnimationSpeed(2)
                                            .setDimAmount(0.5f)
                                            .show();
                                    firebaseFirestore.collection("Admin_PackageRequest__1")
                                            .document(data.get(position).getUuid())
                                            .delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        progressDialog.dismiss();
                                                        Toasty.success(view.getContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                                    }
                                                }
                                            });

                                }
                            });
                            builder.create().show();
                        }

                    }
                }).create().show();
            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                String message=data.get(position).getTransacation()+""+data.get(position).getUsernumber()+""+data.get(position)
                        .getPackage_price();
                String aaa="Package Name : "+data.get(position)
                        .getPackage_name()+"\nTranscation balance  : "+data.get(position)
                        .getPackage_price()+"\nTranscation Method : "+data.get(position).getPayment_methode()+"\nPayment Number : "+
                        data.get(position).getUsernumber()+"\nTranscation ID : "+data.get(position).getTransacation();
                builder.setTitle("Transcation Details")
                        .setMessage(aaa)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder {
        TextView customer_name, customer_number, customer_area, logout;
        CardView card_view8;
        ImageView image;

        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name = itemView.findViewById(R.id.customer_name);
            customer_number = itemView.findViewById(R.id.customer_number);
            customer_area = itemView.findViewById(R.id.customer_area);
            logout = itemView.findViewById(R.id.logout);
            card_view8=itemView.findViewById(R.id.card_view8);
            image=itemView.findViewById(R.id.image);

        }
    }
}