package ceri.m1ilsen.applicationprojetm1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;

/**
 * Created by Laurent on 19/03/2018.
 */

public class PatientsListViewAdapter extends ArrayAdapter<String> {

    public List<String> dataSet;
    Context mContext;
    public int layout;
    private AdapterListener mListener;

    // define listener
    public interface AdapterListener {
        void onClick(String name);
    }

    // set the listener. Must be called from the fragment
    public void setListener(AdapterListener listener) {
        this.mListener = listener;
    }

    public PatientsListViewAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        dataSet = objects;
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PatientsListViewAdapter.ViewHolder mainViewholder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            PatientsListViewAdapter.ViewHolder viewHolder = new PatientsListViewAdapter.ViewHolder();

            viewHolder.selectPatientHolder = (Button) convertView.findViewById(R.id.selectPatientButton);
            viewHolder.selectPatientHolder.setText(dataSet.get(position));
            viewHolder.selectPatientHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // get the name based on the position and tell the fragment via listener
                    mListener.onClick(getItem(position));
                }
            });
            convertView.setTag(viewHolder);
        }
        mainViewholder = (PatientsListViewAdapter.ViewHolder) convertView.getTag();

        return convertView;
    }

    public class ViewHolder {
        Button selectPatientHolder;
    }
}
