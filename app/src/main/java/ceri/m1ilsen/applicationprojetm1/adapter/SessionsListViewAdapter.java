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
 * Created by Laurent on 20/03/2018.
 */

public class SessionsListViewAdapter extends ArrayAdapter<String> {

    public List<String> dataSet;
    Context mContext;
    public int layout;

    public SessionsListViewAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        dataSet = objects;
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SessionsListViewAdapter.ViewHolder mainViewholder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            SessionsListViewAdapter.ViewHolder viewHolder = new SessionsListViewAdapter.ViewHolder();

            viewHolder.sessionDescriptionHolder = (TextView) convertView.findViewById(R.id.sessionDescription);
            viewHolder.sessionDetailsButtonHolder = (Button) convertView.findViewById(R.id.sessionDetailsButton);
            viewHolder.sessionDetailsButtonHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Détails de la session",Toast.LENGTH_LONG);
                }
            });
            convertView.setTag(viewHolder);
        }
        mainViewholder = (SessionsListViewAdapter.ViewHolder) convertView.getTag();
        mainViewholder.sessionDescriptionHolder.setText(getItem(position));

        return convertView;
    }

    public class ViewHolder {
        TextView sessionDescriptionHolder;
        Button sessionDetailsButtonHolder;
    }
}
