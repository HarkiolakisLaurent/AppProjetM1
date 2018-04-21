package ceri.m1ilsen.applicationprojetm1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;

/**
 * Created by merye on 11/02/2018.
 */

public class HomePageListViewAdapter extends ArrayAdapter<String> {

    public List<String> dataSet;
    Context mContext;
    public int layout;

    public HomePageListViewAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
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
            convertView.setTag(viewHolder);
        }
        mainViewholder = (ViewHolder) convertView.getTag();
        mainViewholder.descriptionHolder.setText(getItem(position));

        return convertView;
    }



    public class ViewHolder {
        TextView descriptionHolder;
    }
}