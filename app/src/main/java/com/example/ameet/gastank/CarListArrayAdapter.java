package com.example.ameet.gastank;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ameet on 3/18/2016.
 */
public class CarListArrayAdapter extends CursorAdapter {

    Context context;
    Cursor cursor;

    public CarListArrayAdapter(Context context,Cursor cursor)
    {
        super(context,cursor);
        this.context=context;
        this.cursor =cursor;

    }

    @Override
    public void bindView(View view, final Context context,Cursor cursor)
    {
        final String name=cursor.getString(cursor.getColumnIndex(CarDataHelper.NAME));
        TextView nameText=(TextView)view.findViewById(R.id.nameText);
        nameText.setTextColor(Color.WHITE);
        nameText.setText(name);

    }

    @Override
    public View newView(Context context,Cursor cursor, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.list_item, parent, false);
        bindView(v, context, cursor);
        return v;

    }


}