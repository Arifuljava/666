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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import es.dmoral.toasty.Toasty;

public class Bkash_Activity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private Spinner spinnerTextSize,spinnerTextSize1,spinnerTextSize2;
    EditText Email_Log;
    String valueFromSpinner;
    String valueFromSpinner1;
    String valueFromSpinner2;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    TextView no_of_items,total_amount,spinner4;
    String package_name,package_price,packing;
    EditText spinner1,spinner2;
    Button upgrade;
    KProgressHUD kProgressHUD;
    String getee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bkash_);
        spinner1=findViewById(R.id.name);

        upgrade=findViewById(R.id.login_btn);
        kProgressHUD=KProgressHUD.create(Bkash_Activity.this);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Numbers");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        spinnerTextSize=findViewById(R.id.area);
        spinnerTextSize.setOnItemSelectedListener(this);

        String[] textSizes = getResources().getStringArray(R.array.payment);
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.spinner_row, textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize.setAdapter(adapter);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(valueFromSpinner)||TextUtils.isEmpty(spinner1.getText().toString())) {
                    Toasty.error(getApplicationContext(),"Error", Toast.LENGTH_SHORT,true).show();
                    return;

                }
                else {
                    if (valueFromSpinner.contains("Select Payment Method")) {
                        Toasty.info(getApplicationContext(), "Select Your Payment Method", Toast.LENGTH_SHORT,true).show();
                        return;
                    }
                    else {
                        progress_check();
                        Numbers numbers=new Numbers(spinner1.getText().toString(),valueFromSpinner);
                        firebaseFirestore.collection("Payment")
                                .document("abc@gmail.com")
                                .collection(getee)
                                .document("abc@gmail.com")
                                .set(numbers)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            kProgressHUD.dismiss();
                                            Toasty.success(getApplicationContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                        }
                                    }
                                });
                    }
                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.area) {
            valueFromSpinner=adapterView.getItemAtPosition(i).toString();
            if (valueFromSpinner.equals("Bkash")) {
                getee="1";
            }
            else  if (valueFromSpinner.equals("Rocket")) {
                getee="3";
            }
            else  if (valueFromSpinner.equals("Nagad")) {
                getee="2";
            }
            else if (valueFromSpinner.equals("Paypal")) {
                getee="4";
            }
            else if (valueFromSpinner.equals("USDT-TRON-TRC20")) {
                getee="5";
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void progress_check() {
        kProgressHUD = KProgressHUD.create(Bkash_Activity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

    }
}