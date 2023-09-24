package com.grozziie.adminsection;


import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.myview> {
    public List<Model_admin> data;
    FirebaseFirestore firebaseFirestore;

    public ImageAdapter(List<Model_admin> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image2,parent,false);
        return new myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myview holder, final int position) {
        String image=data.get(position).getImgUrl();
        try {
            Picasso.get().load(image)
                    .placeholder(R.drawable.loading)
                    .into(holder.imageView);
        }catch ( Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(v.getContext())
                        .setBackgroundColor(R.color.colorAccent)
                        .setimageResource(R.drawable.trash)
                        .setTextTitle("Delete")
                        .setCancelable(true)
                        .setTextSubTitle("Are you want to delete this order?")
                        .setPositiveButtonText("Cancel")
                        .setPositiveColor(R.color.colorPrimaryDark)
                        .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();



                            }
                        }).setNegativeColor(R.color.toolbar)
                        .setNegativeButtonText("Delete")
                        .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                            @Override
                            public void OnClick(View view, Dialog dialog) {
                                dialog.dismiss();

                                firebaseFirestore= FirebaseFirestore.getInstance();
                                firebaseFirestore.collection("Post_Slider")
                                        .document(data.get(position).getUuid())
                                        .delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toasty.success(v.getContext(),"Done",Toast.LENGTH_SHORT,true).show();
                                                    v.getContext().startActivity(new Intent(v.getContext(),HomeActivity.class));
                                                }
                                            }
                                        });





                            }
                        })
                        .setBodyGravity(FancyAlertDialog.TextGravity.CENTER)
                        .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                        .setSubtitleGravity(FancyAlertDialog.TextGravity.CENTER)
                        .setCancelable(false)
                        .build();
                alert.show();

             /*
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Warning")
                        .setMessage("Are you want to ")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseFirestore=FirebaseFirestore.getInstance();
                                firebaseFirestore.collection("Post_Slider")
                                        .document(data.get(position).getImgUrl())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().exists()) {
                                                        firebaseFirestore.collection("Post_Slider")
                                                                .document(data.get(position).getImgUrl())
                                                                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Intent intent1=new Intent(v.getContext(), Main_Dash.class);

                                                                    v.getContext().startActivity(intent1);
                                                                    Toast.makeText(v.getContext(), "Done", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                }


                                            }
                                        });

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                builder.create().show();
              */

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        ImageView imageView;
        public myview(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}