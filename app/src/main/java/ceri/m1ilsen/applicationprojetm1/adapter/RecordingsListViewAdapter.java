package ceri.m1ilsen.applicationprojetm1.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.io.File;
import java.util.List;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.ui.DisplaySignalActivity;

/**
 * Created by Laurent on 01/03/2018.
 */

public class RecordingsListViewAdapter extends ArrayAdapter<String> {

    public List<String> dataSet;
    public File dataSetPath;
    Context mContext;
    public int layout;
    public static MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    public RecordingsListViewAdapter(Context context, int resource, List<String> objects, File objectsPath) {
        super(context, resource, objects);
        mContext = context;
        dataSet = objects;
        dataSetPath = objectsPath;
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //String stringPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/sonnerie.wav";
        final String stringPath = dataSetPath+"/"+dataSet.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            RecordingsListViewAdapter.ViewHolder viewHolder = new RecordingsListViewAdapter.ViewHolder();

            viewHolder.recordingDescriptionHolder = (TextView) convertView.findViewById(R.id.recordingDescription);
            viewHolder.displaySignalButtonHolder = (ImageButton) convertView.findViewById(R.id.displaySignalButton);
            viewHolder.playRecordingButtonHolder = (ImageButton) convertView.findViewById(R.id.playRecordingButton);
            viewHolder.stopRecordingButtonHolder = (ImageButton) convertView.findViewById(R.id.stopRecordingButton);
            viewHolder.deleteRecordingButtonHolder = (ImageButton) convertView.findViewById(R.id.deleteRecordingButton);
            convertView.setTag(viewHolder);
        }
        final RecordingsListViewAdapter.ViewHolder mainViewholder = (RecordingsListViewAdapter.ViewHolder) convertView.getTag();
        mainViewholder.displaySignalButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final File file = new File(stringPath);
                Intent displaySignal = new Intent(getContext(),DisplaySignalActivity.class);
                displaySignal.putExtra("fileName",stringPath);
                displaySignal.putExtra("exoName",dataSet.get(position));
                getContext().startActivity(displaySignal);
            }
        });
        mainViewholder.playRecordingButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mediaPlayer.stop();
                    isPlaying = false;
                }
                mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(stringPath));
                mediaPlayer.start();
                isPlaying = true;
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

                final File file = new File(stringPath);
                if(file.exists()){
                    System.out.println("Fichier trouvé");
                }else{
                    System.out.println("Fichier non trouvé");
                }
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create(); //Use context
                alertDialog.setTitle("Suppression du fichier");
                alertDialog.setMessage("Le fichier sera définitivement supprimé");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Annuler",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Continuer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(file.delete()){
                                    System.out.println(file.getName() + " is deleted!");
                                }else{
                                    System.out.println("Delete operation is failed.");
                                }

                                String fileName = dataSet.get(position);
                                String wav = ".wav";
                                fileName = fileName.replace(wav, "");
                                MyApplicationDataSource BD = new MyApplicationDataSource(getContext());
                                BD.open();
                                File file = new File("/storage/emulated/0/App/Exercises/Words/"+fileName+".txt");
                                file.delete();
                                file = new File("/storage/emulated/0/App/Exercises/Sentences/"+fileName+".txt");
                                file.delete();
                                file = new File("/storage/emulated/0/App/Exercises/Texts/"+fileName+".txt");
                                file.delete();
                                file = new File("/storage/emulated/0/App/Exercises/Custom/"+fileName+".txt");
                                file.delete();
                                BD.deleteExercise(fileName);
                                BD.close();

                                dataSet.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                alertDialog.show();

            }
        });
        mainViewholder.recordingDescriptionHolder.setText(getItem(position));

        return convertView;
    }

    public class ViewHolder {
        TextView recordingDescriptionHolder;
        ImageButton displaySignalButtonHolder;
        ImageButton playRecordingButtonHolder;
        ImageButton stopRecordingButtonHolder;
        ImageButton deleteRecordingButtonHolder;
    }
}