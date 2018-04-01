package ceri.m1ilsen.applicationprojetm1.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import ceri.m1ilsen.applicationprojetm1.R;

public class CommentPatientActivity extends AppCompatActivity {

    public TextView commentExplanation;
    public EditText commentField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_patient);
        commentExplanation = (TextView) findViewById(R.id.commentExplanation);
        commentField = (EditText) findViewById(R.id.commentField);
        commentExplanation.setText("Laisser un commentaire pour "+getIntent().getStringExtra("patientLastName")+" "+getIntent().getStringExtra("patientFirstName"));
    }
}
