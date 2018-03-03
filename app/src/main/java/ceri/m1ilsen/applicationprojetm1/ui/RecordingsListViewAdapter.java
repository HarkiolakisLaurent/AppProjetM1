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
                //Uri myUri = Uri.parse("http://www.logz.org/fichiers/_mobile_34484_Going-Blind-Court.mp3");
                Uri myUri = Uri.parse("file:///storage/emulated/0/Download/klaxon.wav");
                String stringPath = "/storage/emulated/0/Download/klaxon.wav";
                stringPath = android.net.Uri.parse("file://" + stringPath).getPath();
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    //mediaPlayer.setDataSource(getContext(), myUri);
                    mediaPlayer.setDataSource(stringPath);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getContext(), "IllegalArgumentException You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                    Toast.makeText(getContext(), "SecurityException You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                    Toast.makeText(getContext(), "IllegalStateException You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IllegalStateException e) {
                    Toast.makeText(getContext(), "IllegalStateException You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getContext(), "IOException You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                }
                //MediaPlayer mediaPlayer = MediaPlayer.create(getContext(),Uri.parse("file:///storage/emulated/0/Download/231.wav"));
                mediaPlayer.start();
                //Toast.makeText(getContext(), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), "Listening Recording Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mainViewholder.deleteRecordingButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Deleting Recording Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
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