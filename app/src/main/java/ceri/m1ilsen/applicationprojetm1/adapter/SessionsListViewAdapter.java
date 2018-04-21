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
import ceri.m1ilsen.applicationprojetm1.exercise.Session;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.ui.CommentSessionActivity;

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

            viewHolder.deleteSessionButtonHolder = (ImageButton) convertView.findViewById(R.id.deleteSessionButton);
            viewHolder.sessionDescriptionHolder = (TextView) convertView.findViewById(R.id.sessionDescription);
            viewHolder.sessionDetailsButtonHolder = (Button) convertView.findViewById(R.id.sessionDetailsButton);
            viewHolder.commentButtonHolder = (Button) convertView.findViewById(R.id.commentButton);
            convertView.setTag(viewHolder);
        }
        mainViewholder = (SessionsListViewAdapter.ViewHolder) convertView.getTag();
        mainViewholder.deleteSessionButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create(); //Use context
                alertDialog.setTitle("Suppression de l'exercice");
                alertDialog.setMessage("Après cette opération, la session sera définitivement supprimée et les informations seront perdues.");
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
                                BD.deleteSession(dataSet.get(position));
                                BD.close();
                                dataSet.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                alertDialog.show();

            }
        });
        mainViewholder.sessionDetailsButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MyApplicationDataSource BD = new MyApplicationDataSource(getContext());
                BD.open();
                Toast.makeText(getContext(),Double.toString(BD.getSessionScoreByTitle(dataSet.get(position))),Toast.LENGTH_LONG).show();
                BD.close();
            }
        });
        mainViewholder.sessionDescriptionHolder.setText(getItem(position));
        mainViewholder.commentButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comment = new Intent(getContext(), CommentSessionActivity.class);
                MyApplicationDataSource BD = new MyApplicationDataSource(getContext());
                BD.open();
                int sessionId = BD.getSessionIdFromTitle(dataSet.get(position));
                comment.putExtra("sessionName",dataSet.get(position));
                comment.putExtra("sessionId",sessionId);
                BD.close();
                getContext().startActivity(comment);

            }
        });

        return convertView;
    }

    public class ViewHolder {
        ImageButton deleteSessionButtonHolder;
        TextView sessionDescriptionHolder;
        Button sessionDetailsButtonHolder;
        Button commentButtonHolder;
    }
}
