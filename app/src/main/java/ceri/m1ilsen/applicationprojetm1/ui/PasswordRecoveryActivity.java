package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;

public class PasswordRecoveryActivity extends AppCompatActivity {
    private EditText mail;
    private EditText favouriteColor;
    private Button passwordRecoveryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        mail = (EditText) findViewById(R.id.mail);
        favouriteColor = (EditText) findViewById(R.id.favouriteWord);
        passwordRecoveryButton = (Button) findViewById(R.id.passwordRecoveryButton);
        passwordRecoveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mail.getText().toString().equals("")
                        || favouriteColor.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Tous les champs doivent être remplis", Toast.LENGTH_LONG).show();
                }
                else {
                    if (mail.getText().toString().matches("[A-Za-z_\\-\\.]*[@]\\w*[\\.][A-Za-z]*")) {
                        MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
                        BD.open();
                        String password;
                        if (BD.getPatientPasswordByMailAndFavouriteWord(mail.getText().toString(), favouriteColor.getText().toString()) != null) {
                            password = BD.getPatientPasswordByMailAndFavouriteWord(mail.getText().toString(), favouriteColor.getText().toString());
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.setType("message/rfc822");
                            email.putExtra(android.content.Intent.EXTRA_EMAIL, mail.getText().toString());
                            email.putExtra(Intent.EXTRA_SUBJECT, "Récupération de mot de passe");
                            email.putExtra(Intent.EXTRA_TEXT, password);
                            email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(Intent.createChooser(email, "Choisir le logiciel"));
                            BD.close();
                            setResult(1);
                            finish();
                        }
                        else if (BD.getClinicianPasswordByMailAndFavouriteWord(mail.getText().toString(), favouriteColor.getText().toString()) != null) {
                            password = BD.getClinicianPasswordByMailAndFavouriteWord(mail.getText().toString(), favouriteColor.getText().toString());
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.setType("message/rfc822");
                            email.putExtra(android.content.Intent.EXTRA_EMAIL, mail.getText().toString());
                            email.putExtra(Intent.EXTRA_SUBJECT, "Récupération de mot de passe");
                            email.putExtra(Intent.EXTRA_TEXT, password);
                            email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(Intent.createChooser(email, "Choisir le logiciel"));
                            BD.close();
                            setResult(1);
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "L'addresse email n'a pas un format cohérent", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
