package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import ceri.m1ilsen.applicationprojetm1.R;

public class DoExerciceActivity extends AppCompatActivity {

    private Button btnQuitter = null;
    private ImageButton imageMicroButton=null;
    private TextView txtView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_exercice);
        txtView=(TextView)findViewById(R.id.textExercice);

        int totalMot = getIntent().getIntExtra("WordTotal",0);
        int positionMot = getIntent().getIntExtra("PositionWord",0);
        String mot = getIntent().getStringExtra("Word");

        txtView.setText(positionMot+"/"+totalMot+"\n"+mot);

        addListenerOnButton();
        BoutonQuitter();

    }

    public void addListenerOnButton() {

        imageMicroButton = (ImageButton) findViewById(R.id.imageBtnMicro);

        imageMicroButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Toast.makeText(DoExerciceActivity.this,
                        "ImageButton is clicked!", Toast.LENGTH_SHORT).show();

            }

        });

    }
    public void BoutonQuitter(){

        btnQuitter=(Button) findViewById(R.id.btnQuitter);

        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent nextActivity = new Intent(DoExerciceActivity.this, CreateExerciceActivity2.class);
                startActivity(nextActivity);
            }
        });
    }
}
