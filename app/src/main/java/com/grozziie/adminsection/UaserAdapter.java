package com.grozziie.adminsection;



import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.List;

public class UaserAdapter extends RecyclerView.Adapter<UaserAdapter.myview> {
    public List<User> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    Context context;

    public UaserAdapter(List<User> data,Context context) {

        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.payment2,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, final int position) {
        holder.customer_name.setText("Username : "+data.get(position).getUsername());
        holder.customer_number.setText("Amount  : "+data.get(position).getAmmount());
        holder.customer_area.setText(data.get(position).getDate());
        holder.logout.setText(data.get(position).getStatus());
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        holder.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Done")
                        .document(data.get(position).getTime())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        String message="Username : "+task.getResult().getString("username")+"\n" +
                                                "Email : "+task.getResult().getString("email")+"\n" +
                                                "Payment Number : "+task.getResult().getString("paymentnumber")+"\n" +
                                                "Payment Methode : "+task.getResult().getString("paymentmethode")+"\n" +
                                                "Ammount : "+task.getResult().getString("ammount");
                                        AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                                        builder.setTitle("Information")
                                                .setMessage(message)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).create().show();

                                    }
                                }
                            }
                        });

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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout;
        CardView card_view8;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            customer_number=itemView.findViewById(R.id.customer_number);
            customer_area=itemView.findViewById(R.id.customer_area);
            logout=itemView.findViewById(R.id.logout);
            card_view8=itemView.findViewById(R.id.card_view8);
        }
    }
}
