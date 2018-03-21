package ceri.m1ilsen.applicationprojetm1.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;

/**
 * Created by merye on 11/02/2018.
 */

public class HomePageListViewAdapter extends ArrayAdapter<String> {

    private String currentUser;
    public List<String> dataSet;
    Context mContext;
    public int layout;

    public HomePageListViewAdapter(Context context, int resource, List<String> objects, String userPseudo) {
        super(context, resource, objects);
        currentUser = userPseudo;
        dataSet = objects;
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewholder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.descriptionHolder = (TextView) convertView.findViewById(R.id.description);
            viewHolder.buttonHolder = (Button) convertView.findViewById(R.id.btnDetails);
            convertView.setTag(viewHolder);
        }
        mainViewholder = (ViewHolder) convertView.getTag();
        mainViewholder.buttonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null)
                    Toast.makeText(getContext(), "Utilisateur connecté : " + currentUser, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mainViewholder.descriptionHolder.setText(getItem(position));

        return convertView;
    }



    public class ViewHolder {
        TextView descriptionHolder;
        Button buttonHolder;
    }
}