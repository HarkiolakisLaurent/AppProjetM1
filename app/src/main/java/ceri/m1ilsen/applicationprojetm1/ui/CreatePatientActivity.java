package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.sql.Date;
import java.util.Calendar;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.language.Language;
import ceri.m1ilsen.applicationprojetm1.sqlite.CommentsDataSource;
import ceri.m1ilsen.applicationprojetm1.user.Clinician;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

public class CreatePatientActivity extends AppCompatActivity {
    public EditText pseudo;
    public EditText nom;
    public EditText prenom;
    public EditText mdp;
    public EditText mail;
    public EditText mdpc;
    public EditText birth;
    public Spinner genre;
    public Spinner langue;
    public ImageButton dateChooser;

    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);
        pseudo =  (EditText) findViewById(R.id.loginField);
        mdp =  (EditText) findViewById(R.id.newPasswordField);
        nom =  (EditText) findViewById(R.id.lastNameField);
        prenom =  (EditText) findViewById(R.id.firstNameField);
        mail =  (EditText) findViewById(R.id.mailField);
        mdpc =  (EditText) findViewById(R.id.confirmNewPasswordField);
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
        CommentsDataSource BD = new CommentsDataSource(this);
        BD.open();
        if(!(pseudo.getText().toString().equals("")) && !(mdp.getText().toString().equals("")) && !(mdpc.getText().toString().equals("")) && !(mail.getText().toString().equals(""))) {
            if (!BD.verification(pseudo.getText().toString(), mdp.getText().toString()) && !BD.verificationM(mail.getText().toString(),mdp.getText().toString())) {
                if (mdp.getText().toString().equals(mdpc.getText().toString())) {
                    Boolean sex = false;
                    if (genre.getSelectedItemPosition() == 0) {
                        sex = true;
                    }
                    String[] nums = birth.getText().toString().split(""+birth.getText().toString().charAt(2));
                    String zeDate = nums[2]+"-"+nums[1]+"-"+nums[0];
                    Patient P = new Patient(mail.getText().toString(), mdp.getText().toString(), pseudo.getText().toString(),
                            nom.getText().toString(), prenom.getText().toString(), Date.valueOf(zeDate), sex, Language.Français, 0, null, null);
                    BD.insertPatient(P);
                    this.setResult(1);
                    this.finish();
                }else{
                    nom.setText("les deux mots de passes sont différents !");
                }
            }else{
                nom.setText("ce compte existe déjà !");
            }
        }else{
            nom.setText("tous les champs obligatoires ne sont pas remplis !");
        }
        BD.close();
    }
}
