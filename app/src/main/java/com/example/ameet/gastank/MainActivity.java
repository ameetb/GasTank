package com.example.ameet.gastank;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    //creates a listview of possible cars from memory
    //passes the arraylist of cars to fuelgaugeactivity
    //allows a button  to create a new car
    //allows a button for settings/instructions

    //saves arraylist of cars when destroyed
    Button newCarButton;
    CarDataHelper mCarDataHelper;
    ListView savedCarsListview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mCarDataHelper=new CarDataHelper(this);
        newCarButton=(Button)findViewById(R.id.newCarButton);

        final Cursor savedCarsCursor=mCarDataHelper.getAllRecords();
        savedCarsCursor.moveToFirst();
        CarListArrayAdapter mCarListArrayAdapter=new CarListArrayAdapter(this,savedCarsCursor);
        savedCarsListview=(ListView)findViewById(R.id.savedCarsListView);
        savedCarsListview.setAdapter(mCarListArrayAdapter);
        savedCarsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                savedCarsCursor.moveToFirst();
                while (savedCarsCursor.getPosition()!=position){
                    savedCarsCursor.moveToNext();
                }
                final String name=savedCarsCursor.getString(savedCarsCursor.getColumnIndex(CarDataHelper.NAME));
                final double mpg=Double.parseDouble(savedCarsCursor.getString(savedCarsCursor.getColumnIndex(CarDataHelper.MPG)));
                final double gasRemaining=Double.parseDouble(savedCarsCursor.getString(savedCarsCursor.getColumnIndex(CarDataHelper.GASREMAINING)));
                final int tankSize=Integer.parseInt(savedCarsCursor.getString(savedCarsCursor.getColumnIndex(CarDataHelper.TANKSIZE)));
                Log.e("gasremainingmain",String.valueOf(gasRemaining));

                Intent intent= new Intent(MainActivity.this.getApplicationContext(), FuelGaugeActivity.class);
                final Car car=new Car(mpg,name,tankSize,gasRemaining,position);
//                intent.putExtra("Car",car);
                Bundle bundle = new Bundle();
                bundle.putSerializable("car", car);
                intent.putExtras(bundle);
                MainActivity.this.startActivity(intent);

            }
        });

        newCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, NewCarActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
