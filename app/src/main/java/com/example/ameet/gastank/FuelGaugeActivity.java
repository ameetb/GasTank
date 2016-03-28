package com.example.ameet.gastank;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class FuelGaugeActivity extends AppCompatActivity {
    //displays miles left and other stuff by calculating from memory values
    //updates the cars amount of gas left to memory when destroyed
    //all calculations based on mpg value for car and gas left
    //updates and saves arraylist of cars to shared preferences when destroyed
    //when activity is started, the arraylist of cars will be passed to it

    private Button unRegisterButton;
    private Car car;
    TextView milesRemainingText;
    TextView gallonsRemainingText;
    TextView milesTraveledText;
    TextView averageMpgText;
    CarDataHelper mCarDataHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_gauge);
//        Intent i = getIntent();
//        Car car = (Car) i.getSerializableExtra("car");
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        Car car = (Car) bundle.getSerializable("car");
        this.car = car;

        mCarDataHelper = new CarDataHelper(this);
        Button tankFullButton = (Button) findViewById(R.id.tankFullButton);
        Button usingCarButton = (Button) findViewById(R.id.usingCarButton);
        this.milesRemainingText = (TextView) findViewById(R.id.milesRemainingText);
        this.gallonsRemainingText = (TextView) findViewById(R.id.gallonsRemainingText);
        this.milesTraveledText = (TextView) findViewById(R.id.milesTraveledText);
        this.averageMpgText = (TextView) findViewById(R.id.averageMpgText);
        milesRemainingText.setText(String.valueOf(car.getGasRemaining()));

        tankFullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tankFullButtonClick();
            }

        });
        usingCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usingCarButtonClick();
            }

        });

        updateUI();


    }

    public void usingCarButtonClick() {

        if (car.isTankReset()) {


            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            LocationChangeListener locationChangeListener = new LocationChangeListener(this,
                    car,
                    milesRemainingText,
                    gallonsRemainingText,
                    milesTraveledText,
                    averageMpgText
            );


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    ) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3500, 0, locationChangeListener);
            } else {
                Toast.makeText(this, "no permission", Toast.LENGTH_LONG).show();
            }

            locationChangeListener.onLocationChanged(null);
            boolean b = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } else {
            milesRemainingText.setTextSize(15f);
            milesRemainingText.setText("You must reset when the tank is full to begin.");
        }

    }

    public void tankFullButtonClick() {

        car.setGasRemaining(car.getTankSize());
        milesRemainingText.setTextSize(100f);
        updateUI();

    }

    public void updateUI() {
        milesRemainingText.setText(car.getMilesRemaining());
        milesTraveledText.setText(car.getMilesTraveled());
        gallonsRemainingText.setText(car.getGasRemaining());
        averageMpgText.setText(car.getMpgString());

    }


    @Override
    protected void onDestroy() {

        mCarDataHelper.updateRecords(car.getCursorPosition(),
                Double.parseDouble(car.getGasRemaining()));
        super.onDestroy();
    }

    @Override
    protected void onStop() {

        mCarDataHelper.updateRecords(car.getCursorPosition(),
                Double.parseDouble(car.getGasRemaining()));
        //stoplistening
        super.onStop();
    }

    @Override
    protected void onPause() {

        mCarDataHelper.updateRecords(car.getCursorPosition(),
                Double.parseDouble(car.getGasRemaining()));
        super.onPause();

    }

}







