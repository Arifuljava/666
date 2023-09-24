package com.grozziie.adminsection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class NotificationActivity extends AppCompatActivity {
    String url,uuid;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    TextView add_notes_title,blog_detail_desc;
    Button buttonSignIn;
    KProgressHUD kProgressHUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        kProgressHUD= KProgressHUD.create(NotificationActivity.this);

        toolbar.setTitle("Notification");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        add_notes_title=findViewById(R.id.add_notes_title);
        blog_detail_desc=findViewById(R.id.blog_detail_desc);
        buttonSignIn=findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(add_notes_title.getText().toString())||
                        TextUtils.isEmpty(blog_detail_desc.getText().toString())) {
                    Toasty.error(getApplicationContext(),"Error", Toast.LENGTH_SHORT,true).show();
                    return;
                }
                else {
                    progress_check();
                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();
                    Chat chat=new Chat(add_notes_title.getText().toString(),blog_detail_desc.getText().toString(),ts, UUID.randomUUID().toString());
                    firebaseFirestore.collection("Notificatios")
                            .document(ts)
                            .set(chat)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    kProgressHUD.dismiss();
                                    Toasty.success(view.getContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                    startActivity(new Intent(view.getContext(),HomeActivity.class));
                                }
                            });
                }
            }
        });
    }
    private void progress_check() {
        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        return true;
    }
}