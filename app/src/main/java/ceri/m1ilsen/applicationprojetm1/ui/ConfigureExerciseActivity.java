package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.exercise.Exercise;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;

public class ConfigureExerciseActivity extends AppCompatActivity {

    EditText exerciseName;
    EditText exerciseDuration;
    EditText fileName;
    Button browser;

    // Stores names of traversed directories
    ArrayList<String> str = new ArrayList<String>();

    // Check if the first level of the directory structure is the one showing
    private Boolean firstLvl = true;

    private static final String TAG = "F_PATH";

    private ConfigureExerciseActivity.Item[] fileList;
    private File path = new File(Environment.getExternalStorageDirectory() + "");
    private String chosenFile;
    private Button createExerciseButton;
    private static final int DIALOG_LOAD_FILE = 1000;

    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_exercise);


        exerciseName = (EditText)findViewById(R.id.exerciseName);
        exerciseDuration = (EditText)findViewById(R.id.exerciseDuration);
        exerciseDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exerciseDuration.getText().clear();
            }
        });
        fileName = (EditText)findViewById(R.id.fileName);
        browser = (Button)findViewById(R.id.browser);
        browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFileList();
                showDialog(DIALOG_LOAD_FILE);
                Log.d(TAG, path.getAbsolutePath());
            }
        });
        createExerciseButton = (Button)findViewById(R.id.createExerciseButton);
        createExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
                BD.open();
                if (!exerciseName.getText().toString().equals("") && !fileName.getText().toString().equals("")) {
                    try {
                        int nbLigne = 0;
                        String ligne = null;
                        File file = new File("chemin_vers_mon_fichier");
                        BufferedReader reader = new BufferedReader(new FileReader(fileName.getText().toString()));
                        // si on arrive ici le reader est instancié donc il faudra fermer les flux
                        List lines=new ArrayList<String>();
                        try {
                            // tant qu'il il a au moins une ligne à lire
                            while((ligne = reader.readLine()) != null) {
                                // on incrémente le compteur
                                if (!lines.contains(ligne)) {
                                    lines.add(ligne);
                                    nbLigne++;
                                }
                            }
                        } finally {
                            reader.close();
                        }
                        // enfin on affiche le résultat sur la console
                        if (nbLigne >= 52) {
                            List<Exercise> exercises = BD.getExerciseByPatientId(getIntent().getExtras().getInt("patientId"));
                            boolean exists = false;
                            for(int i=0; i<exercises.size(); i++) {
                                if (exerciseName.getText().toString().equals(exercises.get(i).getName().toString())) {
                                    exists = true;
                                }
                            }
                            if (!exists) {
                                SimpleDateFormat sdfDay = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
                                Date resultdate = new Date(System.currentTimeMillis());
                                String creationDate = "Le "+sdfDay.format(resultdate)+" à "+sdfHour.format(resultdate);
                                Exercise configuredExercise;
                                Intent createExercise = new Intent(getApplicationContext(),DoExerciseActivity.class);
                                if (exerciseDuration.getText().toString().equals("")) {
                                    configuredExercise = new Exercise(exerciseName.getText().toString(),"custom", creationDate,
                                            30, null, 0, getIntent().getExtras().getInt("sessionId"));
                                    createExercise.putExtra("customExerciseMaxDuration","30");
                                } else {
                                    configuredExercise = new Exercise(exerciseName.getText().toString(),"custom", creationDate,
                                            Double.parseDouble(exerciseDuration.getText().toString()), null, 0, getIntent().getExtras().getInt("sessionId"));
                                    createExercise.putExtra("customExerciseMaxDuration",exerciseDuration.getText().toString());
                                }
                                BD.insertExercise(configuredExercise,getIntent().getExtras().getInt("patientId"));


                                List<String> data = new ArrayList<String>();
                                File dataPath;
                                String name;
                                dataPath = new File("/storage/emulated/0/App/Recordings/"+getIntent().getStringExtra("patientPseudo"));
                                data = new ArrayList(Arrays.asList(dataPath.list(new FilenameFilter() {
                                    public boolean accept(File directory, String fileName) {
                                        return fileName.endsWith(".wav") || fileName.endsWith(".mp3");
                                    }
                                })));
                                if (data.size() > 30) {
                                    Toast.makeText(getApplicationContext(),"Vous avez trop d'enregistrements. Veuillez en supprimer avant de faire un exercice",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    createExercise.putExtra("customExercisePath",fileName.getText().toString());
                                    createExercise.putExtra("exerciseName",exerciseName.getText().toString());
                                    createExercise.putExtra("task","custom");
                                    createExercise.putExtra("isNewExercise",true);
                                    startActivityForResult(createExercise,10000);
                                }

                                BD.close();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Un exercice porte déjà ce nom",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Le fichier doit contenir au moins 52 lignes différentes",Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException ex) {
                        // erreur d'entrée/sortie ou fichier non trouvé
                        ex.printStackTrace();
                    }
                }
            }
        });
        Intent intent = getIntent();
        fileName.setText(intent.getStringExtra("SELECTED_FILE"));
    }

    private void loadFileList() {
        try {
            path.mkdirs();
        } catch (SecurityException e) {
            Log.e(TAG, "unable to write on the sd card ");
        }

        // Checks whether path exists
        if (path.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    // Filters based on whether the file is hidden or not
                    return (sel.isFile() || sel.isDirectory())
                            && !sel.isHidden();

                }
            };

            String[] fList = path.list(filter);
            fileList = new ConfigureExerciseActivity.Item[fList.length];
            for (int i = 0; i < fList.length; i++) {
                fileList[i] = new ConfigureExerciseActivity.Item(fList[i], R.drawable.file_icon);

                // Convert into file path
                File sel = new File(path, fList[i]);

                // Set drawables
                if (sel.isDirectory()) {
                    fileList[i].icon = R.drawable.directory_icon;
                    Log.d("DIRECTORY", fileList[i].file);
                } else {
                    Log.d("FILE", fileList[i].file);
                }
            }

            if (!firstLvl) {
                ConfigureExerciseActivity.Item temp[] = new ConfigureExerciseActivity.Item[fileList.length + 1];
                for (int i = 0; i < fileList.length; i++) {
                    temp[i + 1] = fileList[i];
                }
                temp[0] = new ConfigureExerciseActivity.Item("Up", R.drawable.directory_up);
                fileList = temp;
            }
        } else {
            Log.e(TAG, "path does not exist");
        }

        adapter = new ArrayAdapter<ConfigureExerciseActivity.Item>(this,
                android.R.layout.select_dialog_item, android.R.id.text1,
                fileList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // creates view
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view
                        .findViewById(android.R.id.text1);

                // put the image on the text view
                textView.setCompoundDrawablesWithIntrinsicBounds(
                        fileList[position].icon, 0, 0, 0);

                // add margin between image and text (support various screen
                // densities)
                int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
                textView.setCompoundDrawablePadding(dp5);

                return view;
            }
        };
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
                Activity activity = ConfigureExerciseActivity.this;
                activity.setResult(1);
                activity.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class Item {
        public String file;
        public int icon;

        public Item(String file, Integer icon) {
            this.file = file;
            this.icon = icon;
        }

        @Override
        public String toString() {
            return file;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (fileList == null) {
            Log.e(TAG, "No files loaded");
            dialog = builder.create();
            return dialog;
        }

        switch (id) {
            case DIALOG_LOAD_FILE:
                builder.setTitle("Sélectionnez un fichier");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        chosenFile = fileList[which].file;
                        File sel = new File(path + "/" + chosenFile);
                        if (sel.isDirectory()) {
                            firstLvl = false;

                            // Adds chosen directory to list
                            str.add(chosenFile);
                            fileList = null;
                            path = new File(sel + "");

                            loadFileList();

                            removeDialog(DIALOG_LOAD_FILE);
                            showDialog(DIALOG_LOAD_FILE);
                            Log.d(TAG, path.getAbsolutePath());

                        }

                        // Checks if 'up' was clicked
                        else if (chosenFile.equalsIgnoreCase("up") && !sel.exists()) {

                            // present directory removed from list
                            String s = str.remove(str.size() - 1);

                            // path modified to exclude present directory
                            path = new File(path.toString().substring(0,
                                    path.toString().lastIndexOf(s)));
                            fileList = null;

                            // if there are no more directories in the list, then
                            // its the first level
                            if (str.isEmpty()) {
                                firstLvl = true;
                            }
                            loadFileList();

                            removeDialog(DIALOG_LOAD_FILE);
                            showDialog(DIALOG_LOAD_FILE);
                            Log.d(TAG, path.getAbsolutePath());

                        }
                        // File picked
                        else {
                            // Perform action with file picked
                            getIntent().putExtra("fileName",chosenFile);
                            fileName.setText(sel.toString());
                        }

                    }
                });
                break;
        }
        dialog = builder.show();
        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //Check the result and request code here and update ur activity class
        if ((requestCode == 10000) /* && (resultCode == Activity.RESULT_OK)*/) {
            // recreate your fragment here
            setResult(10000);
            finish();
        }
    }
}
