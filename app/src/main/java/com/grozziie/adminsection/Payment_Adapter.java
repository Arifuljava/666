package com.grozziie.adminsection;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
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
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class Payment_Adapter  extends RecyclerView.Adapter<Payment_Adapter.myview> {
    public List<Payment_request> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    Context context;

    public Payment_Adapter(List<Payment_request> data,Context context) {
        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.payment,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, final int position) {
        if (TextUtils.isEmpty(data.get(position).getDetector())) {
            firebaseAuth= FirebaseAuth.getInstance();
            firebaseFirestore= FirebaseFirestore.getInstance();
            holder.customer_name.setText("Username : "+data.get(position).getUsername());
            holder.customer_number.setText("Amount  : "+data.get(position).getAmmount());
            holder.customer_area.setText("Date : "+data.get(position).getDate());
            holder.customer_area3.setText("Payment Method : "+data.get(position).getPaymentmethode());
            holder.customer_area8.setText("Number : "+data.get(position).getPaymentnumber()+"\n");
            holder.logout.setText(data.get(position).getStatus());
            firebaseAuth=FirebaseAuth.getInstance();
            firebaseFirestore=FirebaseFirestore.getInstance();
            String names=data.get(position).getUsername();

            firebaseFirestore.collection("MarkList")
                    .document(names+"@gmail.com")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    holder.card_view8.setBackgroundColor(Color.MAGENTA);
                                }
                                else {
                                    holder.card_view8.setBackgroundColor(Color.WHITE);
                                }
                            }
                        }
                    });
            holder.card_view8.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("copyed", data.get(position).getPaymentnumber().toLowerCase().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, "Number Copied", Toast.LENGTH_SHORT).show();

                    return false;
                }
            });
            holder.card_view8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String op[]={"Copy Number","Give Payment"};
                    AlertDialog.Builder my=new AlertDialog.Builder(view.getContext());
                    my.setTitle("Select Options")
                            .setItems(op, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(which==0) {
                                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("copyed", data.get(position).getPaymentnumber().toLowerCase().toString());
                                        clipboard.setPrimaryClip(clip);
                                        Toast.makeText(context, "Number Copied", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (which==1) {
                                        AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                                        builder.setTitle("Payment")
                                                .setMessage("Are you  want to give payment?")
                                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                        firebaseFirestore.collection("Admin_paymentRequest")
                                                                .document(data.get(position).getUuid())
                                                                .delete()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {


                                                                            Toasty.success(view.getContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                                                            view.getContext().startActivity(new Intent(view.getContext(),HomeActivity.class));
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                final KProgressHUD progressDialog=  KProgressHUD.create(view.getContext())
                                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                        .setLabel("Please wait")
                                                        .setCancellable(false)
                                                        .setAnimationSpeed(2)
                                                        .setDimAmount(0.5f)
                                                        .show();
                                                Calendar calendar = Calendar.getInstance();
                                                String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                firebaseFirestore.collection("Payment_Request")
                                                        .document(data.get(position).getUseremail())
                                                        .collection("List")
                                                        .document(data.get(position).getUuid())
                                                        .update("status","Send")
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Long tsLong = System.currentTimeMillis()/1000;
                                                                    String ts = tsLong.toString();
                                                                    User user=new User(data.get(position).getUsername(),data.get(position).getUseremail(),
                                                                            data.get(position).getAmmount(),"Send",current1,ts,data.get(position).getPaymentmethode(),
                                                                            data.get(position).getPaymentnumber());
                                                                    firebaseFirestore.collection("Done")
                                                                            .document(ts)
                                                                            .set(user)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        firebaseFirestore.collection("Admin_paymentRequest")
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
                                                                                }
                                                                            });
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
        else {
            firebaseAuth= FirebaseAuth.getInstance();
            firebaseFirestore= FirebaseFirestore.getInstance();
            holder.customer_name.setText("Username : "+data.get(position).getUsername());
            holder.customer_number.setText("Amount  : "+data.get(position).getAmmount());
            holder.customer_area.setText("Date : "+data.get(position).getDate());
            holder.customer_area3.setText("Payment Method : "+data.get(position).getPaymentmethode());
            holder.customer_area8.setText("Number : "+data.get(position).getPaymentnumber()+"\n" +
                    "Balance Category : "+data.get(position).getDetector());
            holder.logout.setText(data.get(position).getStatus());
            firebaseAuth=FirebaseAuth.getInstance();
            firebaseFirestore=FirebaseFirestore.getInstance();
            String names=data.get(position).getUsername();

            firebaseFirestore.collection("MarkList")
                    .document(names+"@gmail.com")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    holder.card_view8.setBackgroundColor(Color.MAGENTA);
                                }
                                else {
                                    holder.card_view8.setBackgroundColor(Color.WHITE);
                                }
                            }
                        }
                    });
            holder.card_view8.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("copyed", data.get(position).getPaymentnumber().toLowerCase().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, "Number Copied", Toast.LENGTH_SHORT).show();

                    return false;
                }
            });
            holder.card_view8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String op[]={"Copy Number","Give Payment"};
                    AlertDialog.Builder my=new AlertDialog.Builder(view.getContext());
                    my.setTitle("Select Options")
                            .setItems(op, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(which==0) {
                                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("copyed", data.get(position).getPaymentnumber().toLowerCase().toString());
                                        clipboard.setPrimaryClip(clip);
                                        Toast.makeText(context, "Number Copied", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (which==1) {
                                        AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                                        builder.setTitle("Payment")
                                                .setMessage("Are you  want to give payment?")
                                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                        firebaseFirestore.collection("Admin_paymentRequest")
                                                                .document(data.get(position).getUuid())
                                                                .delete()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {


                                                                            Toasty.success(view.getContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                                                            view.getContext().startActivity(new Intent(view.getContext(),HomeActivity.class));
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                final KProgressHUD progressDialog=  KProgressHUD.create(view.getContext())
                                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                        .setLabel("Please wait")
                                                        .setCancellable(false)
                                                        .setAnimationSpeed(2)
                                                        .setDimAmount(0.5f)
                                                        .show();
                                                Calendar calendar = Calendar.getInstance();
                                                String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                firebaseFirestore.collection("Payment_Request")
                                                        .document(data.get(position).getUseremail())
                                                        .collection("List")
                                                        .document(data.get(position).getUuid())
                                                        .update("status","Send")
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Long tsLong = System.currentTimeMillis()/1000;
                                                                    String ts = tsLong.toString();
                                                                    User user=new User(data.get(position).getUsername(),data.get(position).getUseremail(),
                                                                            data.get(position).getAmmount(),"Send",current1,ts,data.get(position).getPaymentmethode(),
                                                                            data.get(position).getPaymentnumber());
                                                                    firebaseFirestore.collection("Done")
                                                                            .document(ts)
                                                                            .set(user)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        firebaseFirestore.collection("Admin_paymentRequest")
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
                                                                                }
                                                                            });
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
            customer_area=itemView.findViewById(R.id.customer_area);
            logout=itemView.findViewById(R.id.logout);
            customer_area3=itemView.findViewById(R.id.customer_area3);
            customer_area8=itemView.findViewById(R.id.customer_area8);
            card_view8=itemView.findViewById(R.id.card_view8);
        }
    }
}
