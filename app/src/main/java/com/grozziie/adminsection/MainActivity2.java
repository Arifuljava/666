package com.grozziie.adminsection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private String[] names = {"Item 1", "Item 2", "Item 3"};
    private int[] icons = {R.drawable.wage, R.drawable.wage, R.drawable.wage};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        spinner = findViewById(R.id.spinner);

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, names, icons);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    // Implement the item selection callback methods
    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        // Handle item selection here
        String selectedName = names[position];
        Toast.makeText(this, "Selected: " + selectedName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parentView) {
        // Handle no item selected if needed
    }
}