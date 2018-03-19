package ceri.m1ilsen.applicationprojetm1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;

/**
 * Created by Laurent on 19/03/2018.
 */

public class MonitorPatientsListViewAdapter extends ArrayAdapter<String> {

    public List<String> dataSet;
    Context mContext;
    public int layout;

    public MonitorPatientsListViewAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        dataSet = objects;
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MonitorPatientsListViewAdapter.ViewHolder mainViewholder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            MonitorPatientsListViewAdapter.ViewHolder viewHolder = new MonitorPatientsListViewAdapter.ViewHolder();

            viewHolder.patientDescriptionHolder = (TextView) convertView.findViewById(R.id.patientDescription);
            viewHolder.deletePatientHolder = (ImageButton) convertView.findViewById(R.id.deletePatientButton);
            viewHolder.loginAsPatientButtonHolder = (Button) convertView.findViewById(R.id.loginAsPatientButton);
            convertView.setTag(viewHolder);
        }
        mainViewholder = (MonitorPatientsListViewAdapter.ViewHolder) convertView.getTag();
        mainViewholder.deletePatientHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // suppression de patient

            }
        });
        mainViewholder.loginAsPatientButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // connexion en tant que patient
            }
        });
        mainViewholder.patientDescriptionHolder.setText(getItem(position));

        return convertView;
    }

    public class ViewHolder {
        TextView patientDescriptionHolder;
        ImageButton deletePatientHolder;
        Button loginAsPatientButtonHolder;
    }
}
