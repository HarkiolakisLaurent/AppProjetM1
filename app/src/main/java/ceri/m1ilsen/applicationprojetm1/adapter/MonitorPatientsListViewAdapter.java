package ceri.m1ilsen.applicationprojetm1.adapter;

import android.app.Activity;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.ui.CommentPatientActivity;
import ceri.m1ilsen.applicationprojetm1.ui.ConfigureExerciseActivity;
import ceri.m1ilsen.applicationprojetm1.ui.PatientActivity;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

/**
 * Created by Laurent on 19/03/2018.
 */

public class MonitorPatientsListViewAdapter extends ArrayAdapter<String> {

    public List<String> dataSet;
    Context mContext;
    public int layout;
    private String email;

    public MonitorPatientsListViewAdapter(Context context, int resource, List<String> objects, String mail) {
        super(context, resource, objects);
        mContext = context;
        dataSet = objects;
        layout = resource;
        email = mail;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MonitorPatientsListViewAdapter.ViewHolder mainViewholder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            MonitorPatientsListViewAdapter.ViewHolder viewHolder = new MonitorPatientsListViewAdapter.ViewHolder();

            viewHolder.patientDescriptionHolder = (TextView) convertView.findViewById(R.id.patientDescription);
            viewHolder.deletePatientHolder = (ImageButton) convertView.findViewById(R.id.deletePatientButton);
            viewHolder.loginAsPatientButtonHolder = (Button) convertView.findViewById(R.id.loginAsPatientButton);
            viewHolder.commentButtonHolder = (Button) convertView.findViewById(R.id.commentButton);
            convertView.setTag(viewHolder);
        }
        mainViewholder = (MonitorPatientsListViewAdapter.ViewHolder) convertView.getTag();
        mainViewholder.deletePatientHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final File file = new File("/storage/emulated/0/App/Recordings/"+dataSet.get(position));
                if(file.exists()){
                    System.out.println("Fichier trouvé");
                }else{
                    System.out.println("Fichier non trouvé");
                }
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create(); //Use context
                alertDialog.setTitle("Suppression du patient");
                alertDialog.setMessage("Après cette opération, le profil du patient sera définitivement supprimé.");
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
                                BD.deletePatient(dataSet.get(position));
                                BD.close();
                                if(file.delete()){
                                    System.out.println(file.getName() + " is deleted!");
                                }else{
                                    System.out.println("Delete operation is failed.");
                                }
                                dataSet.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                alertDialog.show();

            }
        });
        mainViewholder.loginAsPatientButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // connexion en tant que patient
                Intent loginAsPatient = new Intent(getContext(), PatientActivity.class);
                MyApplicationDataSource BD = new MyApplicationDataSource(getContext());
                BD.open();
                Patient patient = BD.getPatientByPseudo(dataSet.get(position));
                int patientId = BD.getPatientIdByPseudo(patient.getPseudo());
                loginAsPatient.putExtra("connectedUserMail",patient.getMail());
                loginAsPatient.putExtra("connectedUserPseudo",patient.getPseudo());
                loginAsPatient.putExtra("connectedUserLastName",patient.getLastName());
                loginAsPatient.putExtra("connectedUserFirstName",patient.getFirstName());
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                loginAsPatient.putExtra("connectedUserBirthday",df.format(patient.getBirthday()));
                loginAsPatient.putExtra("connectedUserGender",patient.isGender());
                loginAsPatient.putExtra("connectedUserLanguage",patient.getSpokenLanguage());
                loginAsPatient.putExtra("connectedUserId",patientId);
                BD.close();
                //getContext().startActivity(loginAsPatient);
                ((Activity) mContext).startActivityForResult(loginAsPatient,101);
            }
        });
        mainViewholder.commentButtonHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent comment = new Intent(getContext(), CommentPatientActivity.class);
                MyApplicationDataSource BD = new MyApplicationDataSource(getContext());
                BD.open();
                Patient patient = BD.getPatientByPseudo(dataSet.get(position));
                int patientId = BD.getPatientIdByPseudo(patient.getPseudo());
                comment.putExtra("connectedUserMail",email);
                comment.putExtra("patientLastName",patient.getLastName());
                comment.putExtra("patientFirstName",patient.getFirstName());
                comment.putExtra("patientId",patientId);
                BD.close();
                getContext().startActivity(comment);

            }
        });
        mainViewholder.patientDescriptionHolder.setText(getItem(position));

        return convertView;
    }

    public class ViewHolder {
        TextView patientDescriptionHolder;
        ImageButton deletePatientHolder;
        Button loginAsPatientButtonHolder;
        Button commentButtonHolder;
    }
}
