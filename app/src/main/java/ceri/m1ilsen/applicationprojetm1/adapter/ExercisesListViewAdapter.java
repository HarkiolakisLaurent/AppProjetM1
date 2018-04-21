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
import android.widget.Toast;

import java.io.File;
import java.util.List;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.exercise.Exercise;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.ui.CommentExerciseActivity;
import ceri.m1ilsen.applicationprojetm1.ui.DoExerciseActivity;

/**
 * Created by Laurent on 21/03/2018.
 */

public class ExercisesListViewAdapter extends ArrayAdapter<String> {

    public List<String> dataSet;
    Context mContext;
    public int layout;

    public ExercisesListViewAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        dataSet = objects;
        layout = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ExercisesListViewAdapter.ViewHolder mainViewholder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ExercisesListViewAdapter.ViewHolder viewHolder = new ExercisesListViewAdapter.ViewHolder();

            viewHolder.deleteExerciseButtonHolder = (ImageButton) convertView.findViewById(R.id.deleteExerciseButton);
            viewHolder.exerciseDescriptionHolder = (TextView) convertView.findViewById(R.id.exerciseDescription);
            viewHolder.launchExerciseButtonHolder = (Button) convertView.findViewById(R.id.launchExerciseButton);
            viewHolder.commentButtonHolder = (Button) convertView.findViewById(R.id.commentButton);
            convertView.setTag(viewHolder);
        }
        mainViewholder = (ExercisesListViewAdapter.ViewHolder) convertView.getTag();
        mainViewholder.deleteExerciseButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create(); //Use context
                alertDialog.setTitle("Suppression de l'exercice");
                alertDialog.setMessage("Après cette opération, l'exercice sera définitivement supprimé et ne pourra plus être effectué.");
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
                                MyApplicationDataSource BD = new MyApplicationDataSource(getContext());
                                BD.open();
                                File file = new File("/storage/emulated/0/App/Exercises/Words/"+dataSet.get(position)+".txt");
                                file.delete();
                                file = new File("/storage/emulated/0/App/Exercises/Sentences/"+dataSet.get(position)+".txt");
                                file.delete();
                                file = new File("/storage/emulated/0/App/Exercises/Texts/"+dataSet.get(position)+".txt");
                                file.delete();
                                file = new File("/storage/emulated/0/App/Exercises/Custom/"+dataSet.get(position)+".txt");
                                file.delete();
                                BD.deleteExercise(dataSet.get(position));
                                BD.close();
                                dataSet.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                alertDialog.show();

            }
        });
        mainViewholder.launchExerciseButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplicationDataSource BD = new MyApplicationDataSource(getContext());
                BD.open();
                if (BD.getExerciseReadWordsCountByTitle(dataSet.get(position)) >= 52) {
                    Toast.makeText(v.getContext(),"Exercice terminé",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent continueExercise = new Intent(getContext(), DoExerciseActivity.class);
                    continueExercise.putExtra("isNewExercise",false);
                    continueExercise.putExtra("exerciseName",dataSet.get(position));
                    int patientId = BD.getExercisePatientIdByTitle(dataSet.get(position));
                    String exerciseDuration = BD.getExerciseMaxDurationByTitle(dataSet.get(position));
                    String patientPseudo = BD.getPatientPseudoById(patientId);
                    continueExercise.putExtra("patientPseudo",patientPseudo);
                    switch(BD.getExerciseTaskByTitle(dataSet.get(position))) {
                        case("mots"):
                            continueExercise.putExtra("exercisePath","storage/emulated/0/App/Exercises/Words");
                            continueExercise.putExtra("task",BD.getExerciseTaskByTitle(dataSet.get(position)));
                            break;

                        case("phrases"):
                            continueExercise.putExtra("exercisePath","storage/emulated/0/App/Exercises/Sentences");
                            continueExercise.putExtra("task",BD.getExerciseTaskByTitle(dataSet.get(position)));
                            break;

                        case("textes"):
                            continueExercise.putExtra("exercisePath","storage/emulated/0/App/Exercises/Texts");
                            continueExercise.putExtra("task",BD.getExerciseTaskByTitle(dataSet.get(position)));
                            break;

                        case("custom"):
                            continueExercise.putExtra("exercisePath","storage/emulated/0/App/Exercises/Custom");
                            continueExercise.putExtra("task",BD.getExerciseTaskByTitle(dataSet.get(position)));
                            continueExercise.putExtra("customExerciseMaxDuration",exerciseDuration);

                            break;

                        default:
                            break;
                    }
                    continueExercise.putExtra("exerciseReadWordsCount",BD.getExerciseReadWordsCountByTitle(dataSet.get(position)));
                    mContext.startActivity(continueExercise);
                }
                BD.close();

            }
        });
        mainViewholder.commentButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comment = new Intent(getContext(), CommentExerciseActivity.class);
                MyApplicationDataSource BD = new MyApplicationDataSource(getContext());
                BD.open();
                Exercise exercise = BD.getExerciseByTitle(dataSet.get(position));
                int exerciseId = BD.getExerciseIdByTitle(exercise.getName());
                comment.putExtra("exerciseName",exercise.getName());
                comment.putExtra("exerciseId",exerciseId);
                BD.close();
                getContext().startActivity(comment);

            }
        });
        mainViewholder = (ExercisesListViewAdapter.ViewHolder) convertView.getTag();
        mainViewholder.exerciseDescriptionHolder.setText(getItem(position));

        return convertView;
    }

    public class ViewHolder {
        ImageButton deleteExerciseButtonHolder;
        TextView exerciseDescriptionHolder;
        Button launchExerciseButtonHolder;
        Button commentButtonHolder;
    }
}
