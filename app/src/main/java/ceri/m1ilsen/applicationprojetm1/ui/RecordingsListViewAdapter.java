package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;

/**
 * Created by Laurent on 01/03/2018.
 */

public class RecordingsListViewAdapter extends ArrayAdapter<String> {

    public List<String> dataSet;
    public File dataSetPath;
    Context mContext;
    public int layout;

    public RecordingsListViewAdapter(Context context, int resource, List<String> objects, File objectsPath) {
        super(context, resource, objects);
        dataSet = objects;
        dataSetPath = objectsPath;
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

                //String stringPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/sonnerie.wav";
                String stringPath = dataSetPath+"/"+dataSet.get(position);
                MediaPlayer mediaPlayer = MediaPlayer.create(getContext(),Uri.parse(stringPath));
                if (mediaPlayer != null)
                    mediaPlayer.start();
            }
        });
        mainViewholder.deleteRecordingButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringPath = dataSetPath+"/"+dataSet.get(position);
                File file = new File(stringPath);
                if(file.exists()){
                    System.out.println("Fichier trouvé");
                }else{
                    System.out.println("Fichier non trouvé");
                }
                if(file.delete()){
                    System.out.println(file.getName() + " is deleted!");
                }else{
                    System.out.println("Delete operation is failed.");
                }
                dataSet.remove(position);
                notifyDataSetChanged();
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