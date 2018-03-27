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

            viewHolder.exerciseDescriptionHolder = (TextView) convertView.findViewById(R.id.exerciseDescription);
            viewHolder.launchExerciseButtonHolder = (Button) convertView.findViewById(R.id.launchExerciseButton);
            viewHolder.launchExerciseButtonHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Détails de l'exercice",Toast.LENGTH_LONG);
                }
            });
            convertView.setTag(viewHolder);
        }
        mainViewholder = (ExercisesListViewAdapter.ViewHolder) convertView.getTag();
        mainViewholder.exerciseDescriptionHolder.setText(getItem(position));

        return convertView;
    }

    public class ViewHolder {
        TextView exerciseDescriptionHolder;
        Button launchExerciseButtonHolder;
    }
}
