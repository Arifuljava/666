package com.grozziie.adminsection;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class BlocklIstAdapter extends RecyclerView.Adapter<BlocklIstAdapter.myView> {
    private List<BloackModel> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    public BlocklIstAdapter(List<BloackModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.block, parent, false);
        return new myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myView holder, final int position) {
        holder.customer_name.setText(data.get(position).getUsername());
        firebaseFirestore= FirebaseFirestore.getInstance();
        holder.card_view8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Delete")
                        .setMessage("Are you want to delete")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        firebaseFirestore.collection("BlockList")
                                .document(data.get(position).getEmail())
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
                }).create().show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder {
        TextView customer_name;
        CardView card_view8;

        public myView(@NonNull View itemView) {

            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            card_view8=itemView.findViewById(R.id.card_view8);

        }
    }
}