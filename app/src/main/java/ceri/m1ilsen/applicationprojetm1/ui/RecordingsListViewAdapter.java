package ceri.m1ilsen.applicationprojetm1.ui;

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
 * Created by Laurent on 01/03/2018.
 */

public class RecordingsListViewAdapter extends ArrayAdapter<String> {

    public List<String> dataSet;
    Context mContext;
    public int layout;

    public RecordingsListViewAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        dataSet = objects;
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        RecordingsListViewAdapter.ViewHolder mainViewholder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            RecordingsListViewAdapter.ViewHolder viewHolder = new RecordingsListViewAdapter.ViewHolder();

            viewHolder.recordingDescriptionHolder = (TextView) convertView.findViewById(R.id.recordingDescription);
            viewHolder.listenRecordingButtonHolder = (Button) convertView.findViewById(R.id.listenRecordingButton);
            viewHolder.deleteRecordingButtonHolder = (Button) convertView.findViewById(R.id.deleteRecordingButton);
            convertView.setTag(viewHolder);
        }
        mainViewholder = (RecordingsListViewAdapter.ViewHolder) convertView.getTag();
        mainViewholder.listenRecordingButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Listening Recording Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mainViewholder.deleteRecordingButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Deleting Recording Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mainViewholder.recordingDescriptionHolder.setText(getItem(position));

        return convertView;
    }



    public class ViewHolder {
        TextView recordingDescriptionHolder;
        Button listenRecordingButtonHolder;
        Button deleteRecordingButtonHolder;
    }
}