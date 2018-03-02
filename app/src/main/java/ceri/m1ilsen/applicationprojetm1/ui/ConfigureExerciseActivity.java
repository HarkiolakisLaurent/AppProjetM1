package ceri.m1ilsen.applicationprojetm1.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ceri.m1ilsen.applicationprojetm1.R;

public class ConfigureExerciseActivity extends AppCompatActivity {

    private static final int REQUEST_PATH = 1;
    String curFileName;
    EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_exercise);
        edittext = (EditText)findViewById(R.id.editText6);
    }

    public void getfile(View view){
        Intent intent1 = new Intent(this, FileChooser.class);
        startActivityForResult(intent1,REQUEST_PATH);
    }
    // Listen for results.
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH){
            if (resultCode == RESULT_OK) {
                curFileName = data.getStringExtra("GetFileName");
                edittext.setText(curFileName);
            }
        }
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
}
