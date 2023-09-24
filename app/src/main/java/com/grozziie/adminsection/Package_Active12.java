package com.grozziie.adminsection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;

public class Package_Active12 extends AppCompatActivity {

    String key, key2, key3;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    PackageInAdapter getDataAdapter1;
    List<Package> getList;
    String url;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    FloatingActionButton fab_plus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package__active12);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Deposit Request");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseFirestore = FirebaseFirestore.getInstance();
        getList = new ArrayList<>();
        getDataAdapter1 = new PackageInAdapter(getList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("Admin_PackageRequest__1").document();
        recyclerView = findViewById(R.id.blog_list_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(getDataAdapter1);

        reciveData();
        fab_plus=findViewById(R.id.fab_plus);
        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(Package_Active12.this);
                builder.setTitle("Confirmation")
                        .setMessage("Are you want to approved all request?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        firebaseFirestore.collection("Admin_PackageRequest__1")
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        getList.clear();
                                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                                            String dta=documentSnapshot.getString("username");
                                            String getUser_uuid=documentSnapshot.getString("user_uuid");
                                            String getUseremail=documentSnapshot.getString("useremail");
                                            String getPackage_price=documentSnapshot.getString("package_price");
                                            String getUuid=documentSnapshot.getString("uuid");
                                            String getPackage_name=documentSnapshot.getString("package_name");
                                            final KProgressHUD progressDialog=  KProgressHUD.create(Package_Active12.this)
                                                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                    .setLabel("Please wait")
                                                    .setCancellable(false)
                                                    .setAnimationSpeed(2)
                                                    .setDimAmount(0.5f)
                                                    .show();
                                            firebaseFirestore.collection("Users")
                                                    .document(getUser_uuid)
                                                    .collection("Main_Balance")
                                                    .document(getUseremail)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                if (task.getResult().exists()) {
                                                                    String purches_balance=task.getResult().getString("purches_balance");
                                                                    String giving_balance=task.getResult().getString("giving_balance");
                                                                    String balance=getPackage_price;
                                                                    double total_p=Double.parseDouble(purches_balance)+Double.parseDouble(balance);

                                                                    double total_g=Double.parseDouble(giving_balance)+Double.parseDouble(balance);
                                                                    firebaseFirestore.collection("Users")
                                                                            .document(getUser_uuid)
                                                                            .collection("Main_Balance")
                                                                            .document(getUseremail)
                                                                            .update("purches_balance",String.valueOf(total_p)
                                                                            )
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        firebaseFirestore.collection("MyPackage")
                                                                                                .document(getUseremail)
                                                                                                .collection("List")
                                                                                                .document(getUuid)
                                                                                                .update("status","Active")
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        if (task.isSuccessful()) {
                                                                                                            firebaseFirestore.collection("Admin_PackageRequest__1")
                                                                                                                    .document(getUuid)
                                                                                                                    .delete()
                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                Calendar calendar = Calendar.getInstance();
                                                                                                                                String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                                                                                                String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                                                                                                Package_user package_user=new Package_user(dta,
                                                                                                                                        getPackage_name,current1,"Active");
                                                                                                                                firebaseFirestore.collection("Package_User")
                                                                                                                                        .add(package_user)
                                                                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                                                                            @Override
                                                                                                                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                                                                if (task.isSuccessful()) {

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
                                            progressDialog.dismiss();
                                            Toast.makeText(Package_Active12.this, "Done", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }).create();
                builder.show();
            }
        });
    }

    private void reciveData() {

        firebaseFirestore.collection("Admin_PackageRequest__1")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                            if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                                Package get = ds.getDocument().toObject(Package.class);
                                getList.add(get);
                                getDataAdapter1.notifyDataSetChanged();
                            }

                        }
                    }
                });


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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
             /*   if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }*/
                //fullsearch(query);

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAllUser(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchAllUser(String newText) {
        firebaseFirestore.collection("Subadmin")
                .document("Packages").collection("101")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        getList.clear();
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            String dta=documentSnapshot.getString("username");
                            if (dta.toLowerCase().contains(newText.toLowerCase())) {
                                Package adding_model=new Package(documentSnapshot.getString("useremail"),
                                        documentSnapshot.getString("package_name"),
                                        documentSnapshot.getString("package_price"),
                                        documentSnapshot.getString("payment_methode"),
                                        documentSnapshot.getString("usernumber"),
                                        documentSnapshot.getString("transacation"),
                                        documentSnapshot.getString("uuid"),
                                        documentSnapshot.getString("user_uuid"),
                                        documentSnapshot.getString("status"),
                                        documentSnapshot.getString("username"),
                                        documentSnapshot.getString("date"),
                                        documentSnapshot.getString("package_type"),
                                        documentSnapshot.getString("time")


                                );
                                getList.add(adding_model);

                            }

                            getDataAdapter1 = new PackageInAdapter(getList);
                            recyclerView.setAdapter(getDataAdapter1);
                        }
                    }
                });
    }

}