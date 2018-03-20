package ceri.m1ilsen.applicationprojetm1.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.ui.PatientActivity;

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
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create(); //Use context
                alertDialog.setTitle("Suppression du patient");
                alertDialog.setMessage("Le patient ainsi que ses informations seront définitivement supprimés");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Annuler",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Continuer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // suppression de patient
                                dataSet.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                alertDialog.show();

            }
        });
        mainViewholder.loginAsPatientButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // connexion en tant que patient
                Intent loginAsPatient = new Intent(getContext(), PatientActivity.class);
                getContext().startActivity(loginAsPatient);
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
