package ceri.m1ilsen.applicationprojetm1.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;

public class CommentPatientActivity extends AppCompatActivity {

    private TextView commentExplanation;
    private EditText commentField;
    private Button modifyCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_patient);
        commentExplanation = (TextView) findViewById(R.id.commentExplanation);
        commentField = (EditText) findViewById(R.id.commentField);
        commentExplanation.setText("Laisser un commentaire pour "+getIntent().getStringExtra("patientLastName")+" "+getIntent().getStringExtra("patientFirstName"));
        modifyCommentButton = (Button) findViewById(R.id.modifyCommentButton);

        MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
        BD.open();
        int patientId = getIntent().getExtras().getInt("patientId");
        commentField.setText(BD.getPatientCommentById(getIntent().getExtras().getInt("patientId")));
        BD.close();

        modifyCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
                BD.open();
                int patientId = getIntent().getExtras().getInt("patientId");
                BD.updatePatientComment(patientId,commentField.getText().toString());
                BD.close();
                setResult(1);
                finish();
            }
        });
    }
}
