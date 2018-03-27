package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Context;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class ReadWordFile {
    private Context WContext;

    public ReadWordFile(Context context){
        this.WContext=context;
    }

    public List<String> readFile(BufferedReader bufferedReader) {
        List<String> mLines = new ArrayList<>();
        try {

            String sb = "";
            Intent intent = new Intent(WContext, DoExerciseActivity.class);
            while ((sb= bufferedReader.readLine()) != null){

                mLines.add(sb);

            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mLines;
    }
}

