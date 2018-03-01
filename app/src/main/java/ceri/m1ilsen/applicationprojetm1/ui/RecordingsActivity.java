package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ceri.m1ilsen.applicationprojetm1.R;

public class RecordingsActivity extends AppCompatActivity {


    private ListView lv;
    private RecordingsListViewAdapter recordingsAdapter;
    private ArrayList<String> data = new ArrayList<String>();
    private TextView numberOfRecordings;
    private Button exerciseMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordings);

        lv=(ListView)findViewById(R.id.listResults);
        data= new ArrayList<>();

        data.add(new String("Le DATE à HEURE, vous avez obtenu 75"));
        data.add(new String("Le DATE à HEURE, vous avez obtenu 80"));
        data.add(new String("Le DATE à HEURE, vous avez obtenu 80"));
        data.add(new String("Le DATE à HEURE, vous avez obtenu 80"));
        data.add(new String("Le DATE à HEURE, vous avez obtenu 80"));
        data.add(new String("Le DATE à HEURE, vous avez obtenu 80"));
        data.add(new String("Le DATE à HEURE, vous avez obtenu 80"));
        data.add(new String("Le DATE à HEURE, vous avez obtenu 80"));
        data.add(new String("Le DATE à HEURE, vous avez obtenu 80"));

        numberOfRecordings = (TextView) findViewById(R.id.numberOfRecordings);
        if (data.size() == 0)
            numberOfRecordings.setText("Aucun enregistrement disponible");
        else if (data.size() == 1)
            numberOfRecordings.setText(data.size()+" enregistrement est disponible");
        else
            numberOfRecordings.setText(data.size()+" enregistrements sont disponibles");

        lv.setAdapter(new RecordingsListViewAdapter(this, R.layout.recording_item_view, data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String dataModel= data.get(position);
                Toast.makeText(RecordingsActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();

            }
        });

        exerciseMenu = (Button) findViewById(R.id.exerciseMenu);
        exerciseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(RecordingsActivity.this, CreateExerciceActivity.class);
                startActivity(nextActivity);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home:
                Intent homepage = new Intent(this, HomePageActivity.class);
                startActivity(homepage);
                return true;
            case R.id.action_conf:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
