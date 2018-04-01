package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.language.Language;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

public class CreatePatientActivity extends AppCompatActivity {
    private TextView err;
    private EditText pseudo;
    private EditText nom;
    private EditText prenom;
    private EditText mdp;
    private EditText mail;
    private EditText mdpc;
    private EditText birth;
    private EditText favouriteWord;
    private Spinner genre;
    private Spinner langue;
    private ImageButton dateChooser;

    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);
        err =  (TextView) findViewById(R.id.errtext);
        pseudo =  (EditText) findViewById(R.id.loginField);
        mdp =  (EditText) findViewById(R.id.newPasswordField);
        nom =  (EditText) findViewById(R.id.lastNameField);
        prenom =  (EditText) findViewById(R.id.firstNameField);
        mail =  (EditText) findViewById(R.id.mailField);
        mdpc =  (EditText) findViewById(R.id.confirmNewPasswordField);
        birth = (EditText) findViewById(R.id.birthdayField);
        genre = (Spinner) findViewById(R.id.genreField);
        langue = (Spinner) findViewById(R.id.languageField);
        favouriteWord = (EditText) findViewById(R.id.favouriteWordField);
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
        final String currentUser = getIntent().getStringExtra("connectedUserPseudo");
        final int currentId = getIntent().getIntExtra("connectedUserId",0);
        if(!(pseudo.getText().toString().equals("")) && !(mdp.getText().toString().equals("")) && !(mdpc.getText().toString().equals("")) && !(mail.getText().toString().equals("")) && !(favouriteWord.getText().toString().equals(""))) {
            if (!BD.verificationPatientByPseudoAndPassword(pseudo.getText().toString(), mdp.getText().toString()) && !BD.verificationPatientByMailAndPassword(mail.getText().toString(),mdp.getText().toString())) {
                if (mdp.getText().toString().equals(mdpc.getText().toString())) {
                    if(mail.getText().toString().matches("[A-Za-z_\\-\\.]*[@]\\w*[\\.][A-Za-z]*")) {
                        Boolean sex = false;
                        if (genre.getSelectedItemPosition() == 0) {
                            sex = true;
                        }
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date utilDate = null;
                        try {
                            utilDate = formatter.parse(birth.getText().toString());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                        Patient patient = new Patient(mail.getText().toString(), mdp.getText().toString(), pseudo.getText().toString(),
                                nom.getText().toString(), prenom.getText().toString(), sqlDate, sex, Language.Français, currentId, null, favouriteWord.getText().toString(), null);

                        BD.insertPatient(patient);

                        final File recordingsDirectory = new File("/storage/emulated/0/recordings/" + pseudo.getText().toString());
                        if (!recordingsDirectory.exists()) {
                            recordingsDirectory.mkdirs();
                        }
                        this.setResult(10000);
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
