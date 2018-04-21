package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;

public class CommentSessionActivity extends AppCompatActivity {

    private TextView commentExplanation;
    private EditText commentField;
    private Button saveCommentButton;
    private Button sendCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_session);
        commentExplanation = (TextView) findViewById(R.id.commentExplanation);
        commentField = (EditText) findViewById(R.id.commentField);
        commentExplanation.setText("Laisser un commentaire pour "+getIntent().getStringExtra("sessionName"));
        saveCommentButton = (Button) findViewById(R.id.saveCommentButton);
        sendCommentButton = (Button) findViewById(R.id.sendCommentButton);

        MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
        BD.open();
        commentField.setText(BD.getSessionCommentById(getIntent().getExtras().getInt("sessionId")));
        BD.close();

        saveCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
                BD.open();
                int sessionId = getIntent().getExtras().getInt("sessionId");
                BD.updateSessionComment(sessionId,commentField.getText().toString());
                BD.close();
                Toast.makeText(getApplicationContext(), "Commentaire sauvegardé", Toast.LENGTH_LONG).show();
            }
        });

        sendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentField.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Le champ commentaire ne doit pas être vide", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("message/rfc822");
                    email.putExtra(android.content.Intent.EXTRA_EMAIL,getIntent().getStringExtra("connectedUserMail"));
                    email.putExtra(Intent.EXTRA_SUBJECT, "Commentaire pour "+getIntent().getStringExtra("sessionName"));
                    email.putExtra(Intent.EXTRA_TEXT, commentField.getText().toString());
                    email.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(email, "Choisir le logiciel"));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_retour, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_return:
                Activity activity = CommentSessionActivity.this;
                activity.setResult(1);
                activity.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
