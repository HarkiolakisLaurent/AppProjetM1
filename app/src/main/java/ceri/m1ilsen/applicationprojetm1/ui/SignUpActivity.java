package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ceri.m1ilsen.applicationprojetm1.language.Language;
import ceri.m1ilsen.applicationprojetm1.user.*;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;


public class SignUpActivity extends AppCompatActivity {
    public TextView err;
    public EditText pseudo;
    public EditText nom;
    public EditText prenom;
    public EditText mdp;
    public EditText mail;
    public EditText mdpc;
    public EditText birth;
    public CheckBox tg;
    public Spinner genre;
    public Spinner langue;
    public ImageButton dateChooser;

    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        err =  (TextView) findViewById(R.id.errtext);
        pseudo =  (EditText) findViewById(R.id.loginField);
        mdp =  (EditText) findViewById(R.id.newPasswordField);
        nom =  (EditText) findViewById(R.id.lastNameField);
        prenom =  (EditText) findViewById(R.id.firstNameField);
        mail =  (EditText) findViewById(R.id.mailField);
        mdpc =  (EditText) findViewById(R.id.confirmNewPasswordField);
        tg = (CheckBox) findViewById(R.id.clini);
        birth = (EditText) findViewById(R.id.birthdayField);
        genre = (Spinner) findViewById(R.id.genreField);
        langue = (Spinner) findViewById(R.id.languageField);
        dateChooser = (ImageButton) findViewById(R.id.dateChooser);
        dateChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, datePickerListener, year, month, day);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        return  dialog;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar now = Calendar.getInstance();
            now.set(selectedYear,selectedMonth,selectedDay);
            day = selectedDay;
            month = selectedMonth;
            year = selectedYear;
            birth.setText(selectedDay+"/"+(selectedMonth + 1)+"/"+selectedYear);
        }
    };

    public void creerCompte(View view) {
        MyApplicationDataSource BD = new MyApplicationDataSource(this);
        BD.open();
        if(!(pseudo.getText().toString().equals("")) && !(mdp.getText().toString().equals("")) && !(mdpc.getText().toString().equals("")) && !(mail.getText().toString().equals(""))) {
            if (!BD.verificationPatientByPseudoAndPassword(pseudo.getText().toString(), mdp.getText().toString()) && !BD.verificationPatientByMailAndPassword(mail.getText().toString(),mdp.getText().toString())) {
                if (mdp.getText().toString().equals(mdpc.getText().toString())) {
                    if(mail.getText().toString().matches("[A-Za-z_\\-\\.]*[@]\\w*[\\.][A-Za-z]*")) {
                        if (!tg.isChecked()) {
                            Boolean sex = false;
                            if (genre.getSelectedItemPosition() == 0) {
                                sex = true;
                            }
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
                            java.util.Date utilDate = new java.util.Date();
                            try {
                                utilDate = formatter.parse(birth.getText().toString());

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                            Patient patient = new Patient(mail.getText().toString(), mdp.getText().toString(), pseudo.getText().toString(),
                                    nom.getText().toString(), prenom.getText().toString(), sqlDate, sex, Language.Français, 0, null, null);
                            BD.insertPatient(patient);
                        } else {
                            Clinician clinician = new Clinician(mail.getText().toString(), mdp.getText().toString(), pseudo.getText().toString(), null);
                            BD.insertClinician(clinician);
                        }
                        this.setResult(1000);
                        this.finish();
                    }else{
                        Toast.makeText(this,"L'addresse email n'a pas un format cohérent",Toast.LENGTH_LONG).show();
                        //err.setText("l'addresse email n'a pas un format cohérent");
                    }
                }else{
                    Toast.makeText(this,"Les deux mots de passes sont différents",Toast.LENGTH_LONG).show();
                    //err.setText("les deux mots de passes sont différents !");
                }
            }else{
                Toast.makeText(this,"Ce compte existe déjà",Toast.LENGTH_LONG).show();
                //err.setText("ce compte existe déjà !");
            }
        }else{
            Toast.makeText(this,"Tous les champs obligatoires ne sont pas remplis",Toast.LENGTH_LONG).show();
            //err.setText("tous les champs obligatoires ne sont pas remplis !");
        }
        BD.close();
    }
}



