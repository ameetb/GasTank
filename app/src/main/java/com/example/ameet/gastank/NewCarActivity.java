package com.example.ameet.gastank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewCarActivity extends AppCompatActivity {

    Button addCarButton;
    EditText carNameEdit;
    EditText mileageEdit;
    EditText gasTankSizeEdit;
    CarDataHelper mCarDataHelper;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        addCarButton =(Button)(findViewById(R.id.addCarButton));
        backButton =(Button)(findViewById(R.id.backButton));
        carNameEdit=(EditText)(findViewById(R.id.carNameEditText));
        mileageEdit=(EditText)(findViewById(R.id.mileageEditText));
        gasTankSizeEdit=(EditText)(findViewById(R.id.gasTankSizeEditText));
        mCarDataHelper=new CarDataHelper(this);
        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String carCreated = "New car " + addCar() + " successfully created!";

                Toast.makeText(NewCarActivity.this, carCreated, Toast.LENGTH_LONG).show();
                back();

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }
    public String addCar()
    {
        String name=carNameEdit.getText().toString().trim();
        String mileage= mileageEdit.getText().toString().trim();
        String gasTankSize= gasTankSizeEdit.getText().toString().trim();
        String gasRemaining="-1";
        mCarDataHelper.saveRecords(name, mileage, gasTankSize, gasRemaining);
        return name;
    }

    public void back()
    {
        Intent intent= new Intent(NewCarActivity.this, MainActivity.class);
        startActivity(intent);

    }

}
