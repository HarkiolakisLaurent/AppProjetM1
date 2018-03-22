package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import ceri.m1ilsen.applicationprojetm1.R;

public class EditPatientProfileActivity extends AppCompatActivity {
    public ImageButton dateChooser;
    public TextView lastNameField;
    public TextView firstNameField;
    public EditText birthdayField;
    public EditText loginField;
    public EditText mailField;
    public Spinner languageField;
    public Spinner genreField;
    public Button saveChangesButton;

    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_profile);
        lastNameField = (TextView) findViewById(R.id.lastNameField);
        firstNameField = (TextView) findViewById(R.id.firstNameField);
        birthdayField = (EditText) findViewById(R.id.birthdayField);
        loginField = (EditText) findViewById(R.id.loginField);
        mailField = (EditText) findViewById(R.id.mailField);
        languageField = (Spinner) findViewById(R.id.languageField);
        genreField = (Spinner) findViewById(R.id.genreField);

        lastNameField.setText(getIntent().getStringExtra("connectedUserLastName"));
        firstNameField.setText(getIntent().getStringExtra("connectedUserFirstName"));
        birthdayField.setText(getIntent().getStringExtra("connectedUserBirthday"));
        loginField.setText(getIntent().getStringExtra("connectedUserPseudo"));
        mailField.setText(getIntent().getStringExtra("connectedUserMail"));
        languageField.setSelection(0);
        if (getIntent().getExtras().getBoolean("connectedUserGender") == true)
            genreField.setSelection(0);
        else
            genreField.setSelection(1);

        dateChooser = (ImageButton) findViewById(R.id.dateChooser);
        dateChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
        saveChangesButton = (Button) findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mettre à jour les infos ici
                setResult(1);
                finish();

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
            birthdayField.setText(selectedDay+"/"+(selectedMonth + 1)+"/"+selectedYear);
        }
    };
}
