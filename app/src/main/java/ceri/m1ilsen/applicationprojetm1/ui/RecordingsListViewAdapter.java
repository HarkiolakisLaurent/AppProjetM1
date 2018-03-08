package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
    public static MediaPlayer mediaPlayer;

    public RecordingsListViewAdapter(Context context, int resource, List<String> objects, File objectsPath) {
        super(context, resource, objects);
        dataSet = objects;
        dataSetPath = objectsPath;
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        RecordingsListViewAdapter.ViewHolder mainViewholder = null;
        //String stringPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/sonnerie.wav";
        final String stringPath = dataSetPath+"/"+dataSet.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            RecordingsListViewAdapter.ViewHolder viewHolder = new RecordingsListViewAdapter.ViewHolder();

            viewHolder.recordingDescriptionHolder = (TextView) convertView.findViewById(R.id.recordingDescription);
            viewHolder.playRecordingButtonHolder = (ImageButton) convertView.findViewById(R.id.playRecordingButton);
            viewHolder.stopRecordingButtonHolder = (ImageButton) convertView.findViewById(R.id.stopRecordingButton);
            viewHolder.deleteRecordingButtonHolder = (ImageButton) convertView.findViewById(R.id.deleteRecordingButton);
            convertView.setTag(viewHolder);
        }
        mainViewholder = (RecordingsListViewAdapter.ViewHolder) convertView.getTag();
        mainViewholder.playRecordingButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(getContext(),Uri.parse(stringPath));
                mediaPlayer.start();

            }
        });
        mainViewholder.stopRecordingButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
            }
        });
        mainViewholder.deleteRecordingButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        ImageButton playRecordingButtonHolder;
        ImageButton stopRecordingButtonHolder;
        ImageButton deleteRecordingButtonHolder;
    }
}