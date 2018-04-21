package ceri.m1ilsen.applicationprojetm1.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ceri.m1ilsen.applicationprojetm1.R;

public class AlignActivity extends AppCompatActivity {

    private Button alignButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_align);

        Toast.makeText(getApplicationContext(),getIntent().getStringExtra("alignedFile"),Toast.LENGTH_LONG).show();

        alignButton = (Button) findViewById(R.id.alignButton);
        alignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1000);
                finish();
            }
        });
    }
}
