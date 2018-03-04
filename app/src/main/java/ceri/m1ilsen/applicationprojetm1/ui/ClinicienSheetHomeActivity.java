package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ceri.m1ilsen.applicationprojetm1.R;

public class ClinicienSheetHomeActivity extends AppCompatActivity {
    ListView lv;
    public ListViewAdapter adapter;
    public int n;
    private ArrayList<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinicienhome_sheet);
        lv=(ListView)findViewById(R.id.listResultsClinicien);
        data= new ArrayList<>();

        data.add(new String("Le DATE à HEURE, NOM Prenom a obtenu 75"));
        data.add(new String("Le DATE à HEURE, NOM Prenom a obtenu 80"));

        lv.setAdapter(new ListViewAdapter(this, R.layout.affichageitem, data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String dataModel= data.get(position);
                Toast.makeText(ClinicienSheetHomeActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();

            }
        });

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
                Intent accueil = new Intent(this, HomePageActivity.class);
                startActivity(accueil);
                return true;
            case R.id.action_conf:
                configuration();
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

