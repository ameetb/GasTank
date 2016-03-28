package com.example.ameet.gastank;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ameet on 2/4/2016.
 */
public class LocationChangeListener implements LocationListener {


    TextView milesRemainingText;
    TextView gallonsRemainingText;
    TextView milesTraveledText;
    TextView averageMpgText;
    Context context;
    Car car;
    final int duration = Toast.LENGTH_SHORT;
    final CharSequence availableText = "GPS not Available";
    final CharSequence notAvailableText = "GPS Active";
    Location previousLocation;


    public LocationChangeListener(Context context, Car car, TextView milesRemainingText,
                                  TextView gallonsRemainingText,
                                  TextView milesTraveledText,
                                  TextView averageMpgText) {
        this.context = context;
        this.milesRemainingText = milesRemainingText;
        this.gallonsRemainingText = gallonsRemainingText;
        this.milesTraveledText = milesTraveledText;
        this.averageMpgText = averageMpgText;
        this.car = car;
        previousLocation = null;
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location == null) {
            Toast.makeText(context, "Searching For GPS Signal", Toast.LENGTH_LONG).show();
        }
        if (previousLocation == null) {
            previousLocation = location;
        }
        if (previousLocation != null && location != null) {
            double distanceInMiles = location.distanceTo(previousLocation) * 0.000621371;
            double gasUsed = distanceInMiles / car.getMpg();
            car.removeGas(gasUsed);
            milesRemainingText.setText(car.getMilesRemaining());
            milesTraveledText.setText(car.getMilesTraveled());
            gallonsRemainingText.setText(car.getGasRemaining());
            averageMpgText.setText(car.getMpgString());
            previousLocation = location;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        if (status != LocationProvider.AVAILABLE) {
            Toast.makeText(context, notAvailableText, duration).show();

        } else {
            Toast.makeText(context, availableText, duration).show();
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(context, availableText, duration).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(context, notAvailableText, duration).show();
    }
}
