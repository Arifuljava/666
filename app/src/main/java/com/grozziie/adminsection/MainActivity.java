package com.grozziie.adminsection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private TextView appname;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseUser user;
    private String userID;
    int totall;
    int main_total;
    String ts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiDex.install(this);
        // new CheckInternetConnection(this).checkConnection();
        Long tsLong = System.currentTimeMillis()/1000;
        ts = tsLong.toString();
        appname = findViewById(R.id.appname);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseFirestore = FirebaseFirestore.getInstance();





        //Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        // appname.setTypeface(typeface);

        YoYo.with(Techniques.Bounce)
                .duration(7000)
                .playOn(findViewById(R.id.logo));

        YoYo.with(Techniques.FadeInUp)
                .duration(5000)
                .playOn(findViewById(R.id.appname));
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ////startActivity(new Intent(getApplicationContext(), HomeActivity.class));


                final FlatDialog flatDialog1 = new FlatDialog(MainActivity.this);
                flatDialog1.setTitle("Admin Panel Login")
                        .setSubtitle("Please enter username and password for admin panel login.")
                        .setFirstTextFieldHint("Username")
                        .setSecondTextFieldHint("Password")
                        .setFirstButtonText("Ok")
                        .setSecondButtonText("Cancel")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String first = flatDialog1.getFirstTextField().toLowerCase().toString();
                                String second = flatDialog1.getSecondTextField().toLowerCase().toString();
                                if (TextUtils.isEmpty(first) || TextUtils.isEmpty(second)) {
                                    Toast.makeText(MainActivity.this, "Enter all information", Toast.LENGTH_SHORT).show();
                                } else {
                                    final KProgressHUD progressDialog = KProgressHUD.create(MainActivity.this)
                                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                            .setLabel("Checking........")
                                            .setCancellable(false)
                                            .setAnimationSpeed(2)
                                            .setDimAmount(0.5f)
                                            .show();
                                    firebaseFirestore.collection("AdminLogin1")
                                            .document("abc" + "@gmail.com")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().exists()) {

                                                            String username = task.getResult().getString("username");
                                                            String password = task.getResult().getString("password");
                                                            if (username.toLowerCase().toString().equals(first) && password.toLowerCase().toString().equals(second)) {
                                                                firebaseAuth.signInWithEmailAndPassword("sadia@gmail.com", "123456")
                                                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    progressDialog.dismiss();
                                                                                    flatDialog1.dismiss();
                                                                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                                                                }
                                                                            }
                                                                        });
                                                            } else {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(MainActivity.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    }
                                                }
                                            });
                                }


                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog1.dismiss();
                            }
                        })
                        .show();



            }
        },0);
    }
}