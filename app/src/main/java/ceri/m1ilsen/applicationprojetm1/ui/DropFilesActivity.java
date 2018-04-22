package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import ceri.m1ilsen.applicationprojetm1.R;

public class DropFilesActivity extends AppCompatActivity {

    EditText fileName;
    Button browser;

    // Stores names of traversed directories
    ArrayList<String> str = new ArrayList<String>();

    // Check if the first level of the directory structure is the one showing
    private Boolean firstLvl = true;

    private static final String TAG = "F_PATH";

    private DropFilesActivity.Item[] fileList;
    private File path = new File(Environment.getExternalStorageDirectory() + "");
    private Spinner exerciseType;
    private String chosenFile;
    private Button dropFileButton;
    private static final int DIALOG_LOAD_FILE = 1000;

    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_files);
        exerciseType = (Spinner) findViewById(R.id.exerciseType);
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
        dropFileButton = (Button)findViewById(R.id.dropFileButton);
        dropFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dépôt fichier ici
                File file = new File(fileName.getText().toString());
                if (!fileName.getText().toString().equals("") && file.exists()) {
                    switch(exerciseType.getSelectedItemPosition()) {

                        case(0):
                            dropFile(file, "WORDS");
                            Toast.makeText(getApplicationContext(), "Dépôt du fichier", Toast.LENGTH_LONG).show();
                            break;

                        case(1):
                            dropFile(file, "SENTENCES");
                            Toast.makeText(getApplicationContext(), "Dépôt du fichier", Toast.LENGTH_LONG).show();
                            break;

                        case(2):
                            dropFile(file, "TEXT");
                            Toast.makeText(getApplicationContext(), "Dépôt du fichier", Toast.LENGTH_LONG).show();
                            break;

                        default:
                            break;
                    }
                    fileName.setText("");
                }
            }
        });
    }

    public void dropFile(File droppedFile, String resourceType) {
        try {
            InputStream inputStream = new FileInputStream(droppedFile);
            if (inputStream != null) {
                // open a reader on the inputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                // String used to store the lines
                String str;
                StringBuilder buf = new StringBuilder();

                // Read the file
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\r\n");
                }
                // Close streams
                reader.close();
                inputStream.close();
                File file = new File("storage/emulated/0/App/Resources/"+resourceType+"_RESOURCE.txt");
                try {
                    if (!file.exists()) {
                        new File(file.getParent()).mkdirs();
                        file.createNewFile();
                    }
                } catch (IOException e) {
                    Log.e("", "Could not create file.", e);
                    return;
                }
                FileWriter fw = new FileWriter(file,true);
                fw.write (buf.toString());
                fw.close();
            }
        } catch (java.io.FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "FileNotFoundException", Toast.LENGTH_LONG);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "FileNotFoundException", Toast.LENGTH_LONG);
        }
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
            fileList = new DropFilesActivity.Item[fList.length];
            for (int i = 0; i < fList.length; i++) {
                fileList[i] = new DropFilesActivity.Item(fList[i], R.drawable.file_icon);

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
                DropFilesActivity.Item temp[] = new DropFilesActivity.Item[fileList.length + 1];
                for (int i = 0; i < fileList.length; i++) {
                    temp[i + 1] = fileList[i];
                }
                temp[0] = new DropFilesActivity.Item("Up", R.drawable.directory_up);
                fileList = temp;
            }
        } else {
            Log.e(TAG, "path does not exist");
        }

        adapter = new ArrayAdapter<DropFilesActivity.Item>(this,
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
                Activity activity = DropFilesActivity.this;
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
                            fileName.setText(sel.toString());
                        }

                    }
                });
                break;
        }
        dialog = builder.show();
        return dialog;
    }
}
