package com.grozziie.adminsection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends  AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    CardView card_view2;
    KProgressHUD progressHUD;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String  email;
    int count = 0,count1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Admin Panel");
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        //fragment
        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
        progressHUD = KProgressHUD.create(HomeActivity.this);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        card_view2=findViewById(R.id.card_view2);
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Package_Active.class));

            }
        });
        CardView card_view5=findViewById(R.id.card_view5);
        card_view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
            }
        });
        CardView card_view4=findViewById(R.id.card_view4);
        card_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Report.class));
                // Toast.makeText(HomeActivity.this, "gfhgfhgf", Toast.LENGTH_SHORT).show();
            }
        });
        CardView card_view1=findViewById(R.id.card_view1);
        card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Taking_Information_to_User.class));
            }
        });
        CardView card_view6=findViewById(R.id.card_view6);
        card_view6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), HelpLine.class));
                startActivity(new Intent(getApplicationContext(),HelpLineActivity.class));
            }
        });
        //7
        CardView card_view7=findViewById(R.id.card_view7);
        card_view7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), HelpLine.class));
                startActivity(new Intent(getApplicationContext(),ListActivity.class));
            }
        });

        //
        //7
        CardView card_view9=findViewById(R.id.card_view9);
        card_view9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), HelpLine.class));
                startActivity(new Intent(getApplicationContext(),RatingActivity.class));
            }
        });
//notification count
        CardView card_view11=findViewById(R.id.card_view11);
        card_view11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), HelpLine.class));
                startActivity(new Intent(getApplicationContext(),BlocklIstActivity.class));
            }
        });
        CardView card_view2capp=findViewById(R.id.card_view2capp);
        card_view2capp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), HelpLine.class));
                startActivity(new Intent(getApplicationContext(),Package_Active12.class));
            }
        });
        firebaseFirestore.collection("Admin_paymentRequest")

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            firebaseFirestore.collection("Notification_count")
                                    .document("abc@gmail.com")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                if (task.getResult().exists()) {
                                                    String tas=task.getResult().getString("counter");
                                                    if (Integer.parseInt(tas)==count) {
                                                    }
                                                    else {
                                                        int find=count-Integer.parseInt(tas);
                                                        AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
                                                        builder.setTitle("Notification")
                                                                .setMessage("New  Payment Request : "+find)
                                                                .setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        dialogInterface.dismiss();
                                                                    }
                                                                }).create();
                                                        builder.show();
                                                    }
                                                }
                                            }
                                        }
                                    });
                        }

                    }
                });
        firebaseAuth=FirebaseAuth.getInstance();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.add_web_site:
String List[]={"First","Second","Third","Fourth","Fifth","Sixh"};
                final FlatDialog flatDialog3_website = new FlatDialog(HomeActivity.this);
                flatDialog3_website.setTitle("Add Link")
                        .setSubtitle("Are you want to add link?")
                        .setFirstTextFieldHint("Link")
                        .setFirstButtonText("Ok")
                        .setSecondButtonText("Cancel")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog3_website.dismiss();
                                final KProgressHUD progressDialog=  KProgressHUD.create(HomeActivity.this)
                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                        .setLabel("Please wait")
                                        .setCancellable(false)
                                        .setAnimationSpeed(2)
                                        .setDimAmount(0.5f)
                                        .show();
                                String name=flatDialog3_website.getFirstTextField().toLowerCase().toString();
                                if (TextUtils.isEmpty(name)) {
                                    progressDialog.dismiss();

                                    Toasty.error(getApplicationContext(),"Error",Toasty.LENGTH_SHORT,true).show();
                                }
                                else {
                                    flatDialog3_website.dismiss();
                                    AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
                                    builder.setTitle("Select a option")
                                            .setItems(List, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if (which==0)
                                                    {
                                                        firebaseFirestore.collection("Link")
                                                                .document("1")
                                                                .update("link",""+name)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            Toast.makeText(HomeActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                    //2
                                                    else    if (which==1)
                                                    {
                                                        firebaseFirestore.collection("Link")
                                                                .document("2")
                                                                .update("link",""+name)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            Toast.makeText(HomeActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                    //3
                                                    else    if (which==2)
                                                    {
                                                        firebaseFirestore.collection("Link")
                                                                .document("3")
                                                                .update("link",""+name)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            Toast.makeText(HomeActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                    //4
                                                    else    if (which==3)
                                                    {
                                                        firebaseFirestore.collection("Link")
                                                                .document("4")
                                                                .update("link",""+name)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            Toast.makeText(HomeActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                    //5
                                                    else    if (which==4)
                                                    {
                                                        firebaseFirestore.collection("Link")
                                                                .document("5")
                                                                .update("link",""+name)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            Toast.makeText(HomeActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                    //6
                                                    else    if (which==5)
                                                    {
                                                        firebaseFirestore.collection("Link")
                                                                .document("6")
                                                                .update("link",""+name)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful())
                                                                        {
                                                                            Toast.makeText(HomeActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    }

                                                }
                                            }).create();
                                    builder.show();
                                }

                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog3_website.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.logout:
                AlertDialog.Builder warning = new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Logout")
                        .setMessage("Do you want to logout?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();



                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ToDO: delete all the notes created by the Anon user


                                firebaseAuth.signOut();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();


                            }
                        });

                warning.show();
                break;


            case R.id.add_a:
                startActivity(new Intent(getApplicationContext(),Add_bannar.class));

                return true;







            case R.id.list___ofslider:
                startActivity(new Intent(getApplicationContext(),Show_Bannar.class));
                break;

            case R.id.password:


                break;


            case R.id.block:
                final FlatDialog flatDialog3 = new FlatDialog(HomeActivity.this);
                flatDialog3.setTitle("Block a member")
                        .setSubtitle("Are you want to block a member?")
                        .setFirstTextFieldHint("username")
                        .setFirstButtonText("Ok")
                        .setSecondButtonText("Cancel")
                        .withFirstButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog3.dismiss();
                                final KProgressHUD progressDialog=  KProgressHUD.create(HomeActivity.this)
                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                        .setLabel("Please wait")
                                        .setCancellable(false)
                                        .setAnimationSpeed(2)
                                        .setDimAmount(0.5f)
                                        .show();
                                String name=flatDialog3.getFirstTextField().toLowerCase().toString();
                                if (TextUtils.isEmpty(name)) {
                                    progressDialog.dismiss();

                                    Toasty.error(getApplicationContext(),"Error",Toasty.LENGTH_SHORT,true).show();
                                }
                                else {
                                    BloackModel bloackModel = new BloackModel(name, name + "@gmail.com");
                                    firebaseFirestore.collection("BlockList")
                                            .document(name + "@gmail.com")
                                            .set(bloackModel)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        progressDialog.dismiss();
                                                        Toasty.success(getApplicationContext(), "Done", Toasty.LENGTH_SHORT, true).show();
                                                    }
                                                }
                                            });
                                }

                            }
                        })
                        .withSecondButtonListner(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                flatDialog3.dismiss();
                            }
                        })
                        .show();
                break;




            case R.id.shareapp1:
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));

                break;
            case R.id.addNote:
                Toasty.success(getApplicationContext(),"You  are home now", Toast.LENGTH_SHORT,true).show();
                break;
            case R.id.notification:
                startActivity(new Intent(getApplicationContext(),NotificationActivity.class));

                break;
            case R.id.shareapp:
                final KProgressHUD progressDial2og=  KProgressHUD.create(HomeActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Please wait")
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
                firebaseFirestore.collection("Admin_paymentRequest")

                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int count = 0;
                                    for (DocumentSnapshot document : task.getResult()) {
                                        count++;
                                    }
                                    progressDial2og.dismiss();
                                    startActivity(new Intent(getApplicationContext(),PaymentActivity.class));
                                    /*
                                    Toast.makeText(HomeActivity.this, "fghg", Toast.LENGTH_SHORT).show();
                                    Map<String, String> userMap1 = new HashMap<>();

                                    userMap1.put("counter",String.valueOf(count));
                                    firebaseFirestore.collection("Notification_count")
                                            .document("abc@gmail.com")
                                            .update("counter",""+count)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(HomeActivity.this, "fgfgg", Toast.LENGTH_SHORT).show();
                                                        progressDial2og.dismiss();;
                                                        startActivity(new Intent(getApplicationContext(),PaymentActivity.class));

                                                    }
                                                }
                                            });
                                     */
                                }

                            }
                        });
                //startActivity(new Intent(getApplicationContext(),PaymentActivity.class));

                break;
            case R.id.add_a_a :
                startActivity(new Intent(getApplicationContext(),Bkash_Activity.class));
                break;
            case R.id.depositlist :
                startActivity(new Intent(getApplicationContext(),Package_Active12.class));
                break;







        }
        return false;
    }
    int count11 = 0;
    int total=0;
    int secondd=0;
    String message="\n";
    @Override
    public void onBackPressed()   {
        AlertDialog.Builder warning = new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you want to exit?")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                }).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ToDO: delete all the notes created by the Anon user


                        dialog.dismiss();
                        finishAffinity();

                    }
                });

        warning.show();
    }
}