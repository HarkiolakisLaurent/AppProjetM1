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
import android.widget.Toast;

import java.util.ArrayList;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.CommentsDataSource;

public class PatientSheetHomeActivity extends AppCompatActivity {
    ListView lv;
    public ListViewAdapter adapter;
    private Button btnExercice = null;
    private Button btnEnregist = null;
    private ArrayList<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patienthome_sheet);
        lv=(ListView)findViewById(R.id.listResults);
        data= new ArrayList<>();

        data.add(new String("Le DATE à HEURE, vous avez obtenu 75"));
        data.add(new String("Le DATE à HEURE, vous avez obtenu 80"));

        lv.setAdapter(new ListViewAdapter(this, R.layout.affichageitem, data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String dataModel= data.get(position);
                Toast.makeText(PatientSheetHomeActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();

            }
        });

        btnExercice = (Button) findViewById(R.id.btnExercice);
        btnExercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent nextActivity = new Intent(PatientSheetHomeActivity.this, CreateExerciceActivity.class);
                startActivity(nextActivity);
            }
        });

        btnEnregist = (Button) findViewById(R.id.btnEnregist);
        btnEnregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent nextActivity = new Intent(PatientSheetHomeActivity.this, RecordingsActivity.class);
                startActivity(nextActivity);
            }
        });

        CommentsDataSource BD = new CommentsDataSource(this);
        BD.open();
        // String data[] = BD.getName();
        System.out.println("LE pseaudo est hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"+ BD.getName());
        BD.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file, menu);
        return true;
    }

    //gère le click sur une action de l'ActionBar
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



    private void configuration(){
        Toast.makeText(this,R.string.menuConfig,Toast.LENGTH_LONG).show();
    }
     /*   public String getCurrentDate() {
        String strDate="";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        return  strDate= "Current Date : " + mdformat.format(calendar.getTime());
    }*/

}
