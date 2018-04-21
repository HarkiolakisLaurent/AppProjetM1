package ceri.m1ilsen.applicationprojetm1.sqlite;

/**
 * Created by Laurent on 31/01/2018.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import ceri.m1ilsen.applicationprojetm1.exercise.Exercise;
import ceri.m1ilsen.applicationprojetm1.exercise.Session;
import ceri.m1ilsen.applicationprojetm1.language.Language;
import ceri.m1ilsen.applicationprojetm1.user.*;

import static ceri.m1ilsen.applicationprojetm1.sqlite.MySQLiteDatabase.*;

public class MyApplicationDataSource {

    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteDatabase dbHelper;

    public MyApplicationDataSource(Context context) {
        dbHelper = new MySQLiteDatabase(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public boolean verificationPatientByPseudoAndPassword(String pseudo , String mdp){
        Cursor cursor = database.query("patients",new String[]{ "pseudo", "mail"},"pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
        if(cursor.getCount() == 0 ){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }

    }

    public boolean verificationPatientByMailAndPassword(String mail , String mdp){
        Cursor cursor = database.query("patients",new String[]{"pseudo", "mail"},"mail LIKE \""+mail+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
        if(cursor.getCount() == 0){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }

    public boolean verificationExistingPatientByPseudo(String pseudo , int id){
        Cursor cursor = database.query("patients",new String[]{ "pseudo"},"pseudo LIKE \""+pseudo+"\" and _id = \""+id+"\"",null,null,null,null);
        if(cursor.getCount() == 0 ){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }

    public boolean verificationClinicianByPseudoAndPassword(String pseudo , String mdp){
            Cursor cursor = database.query("cliniciens",new String[]{ "pseudo", "mail"},"pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
            if(cursor.getCount() == 0){
                cursor.close();
                return false;
            }else{
                cursor.close();
                return true;
            }
    }

    public boolean verificationClinicianByMailAndPassword(String mail , String mdp){
            Cursor cursor = database.query("cliniciens",new String[]{"pseudo", "mail"},"mail LIKE \""+mail+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
            if(cursor.getCount() == 0){
                cursor.close();
                return false;
            }else{
                cursor.close();
                return true;
            }
    }

    public boolean verificationExistingClinicianByPseudo(String pseudo , int id){
        Cursor cursor = database.query("cliniciens",new String[]{ "pseudo"},"pseudo LIKE \""+pseudo+"\" and _id = \""+id+"\"",null,null,null,null);
        if(cursor.getCount() == 0 ){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }

    public long insertPatient(Patient patient) {
        ContentValues values = new ContentValues();
        values.put("pseudo", patient.getPseudo());
        values.put("mail", patient.getMail());
        values.put("mot_de_passe", patient.getPassword());
        values.put("nom", patient.getLastName());
        values.put("prenom", patient.getFirstName());
        values.put("date_de_naissance", patient.getBirthday().toString());
        values.put("genre", patient.isGender());
        values.put("langue", patient.getSpokenLanguage().toString());
        values.put("comment", patient.getComment());
        values.put("mot_prefere", patient.getFavouriteWord());
        values.put("id_clinicien", patient.getClinicianInCharge());
        return database.insert("patients", null, values);
    }

    public long updatePatient(int id, Patient patient) {
        ContentValues values = new ContentValues();
        values.put("mail", patient.getMail());
        values.put("mot_de_passe", patient.getPassword());
        values.put("genre", patient.isGender());
        values.put("langue", patient.getSpokenLanguage().toString());
        return database.update("patients", values, "_id = "+id,null);
    }

    public long partiallyUpdatePatient(int id, Patient patient) {
        ContentValues values = new ContentValues();
        values.put("mail", patient.getMail());
        values.put("genre", patient.isGender());
        values.put("langue", patient.getSpokenLanguage().toString());
        return database.update("patients", values, "_id = "+id,null);
    }

    public long updatePatientComment(int id, String comment) {
        ContentValues values = new ContentValues();
        values.put("comment", comment);
        return database.update("patients", values, "_id = "+id,null);
    }

    public void deletePatient(String login) {
        database.delete("patients", "pseudo = ?",new String[]{login});
    }

    public long insertClinician(Clinician clinician) {
        ContentValues values = new ContentValues();
        values.put("pseudo", clinician.getPseudo());
        values.put("mail", clinician.getMail());
        values.put("mot_de_passe", clinician.getPassword());
        values.put("mot_prefere", clinician.getFavouriteWord());
        return database.insert("cliniciens", null, values);
    }

    public long updateClinician(int id, Clinician clinician) {
        ContentValues values = new ContentValues();
        values.put("mail", clinician.getMail());
        values.put("mot_de_passe", clinician.getPassword());
        return database.update("cliniciens", values, "_id = "+id,null);
    }

    public long partiallyUpdateClinician(int id, Clinician clinician) {
        ContentValues values = new ContentValues();
        values.put("mail", clinician.getMail());
        return database.update("cliniciens", values, "_id = "+id,null);
    }

    public void deleteClinician(String login) {
        database.delete("cliniciens", "pseudo = ?",new String[]{login});
    }

    public long insertSession(Session session, int patientId) {
        ContentValues values = new ContentValues();
        values.put("titre", session.getName());
        values.put("id_patient",patientId);
        return database.insert("sessions", null, values);
    }

    public long updateSessionComment(int id, String comment) {
        ContentValues values = new ContentValues();
        values.put("comment", comment);
        return database.update("sessions", values, "_id = "+id,null);
    }

    public long updateSessionScore(int id, double score) {
        ContentValues values = new ContentValues();
        values.put("score", score);
        return database.update("sessions", values, "_id = "+id,null);
    }

    public void deleteSession(String title) {
        database.delete("sessions", "titre = ?",new String[]{title});
    }

    public long insertExercise(Exercise exercise, int patientId) {
        ContentValues values = new ContentValues();
        values.put("titre", exercise.getName());
        values.put("mots_lus", exercise.getReadWordsCount());
        values.put("duree_max", exercise.getMaxDuration());
        values.put("tache", exercise.getTask());
        values.put("date_creation", exercise.getCreationDate());
        values.put("id_patient", patientId);
        values.put("score",exercise.getScore());
        System.out.println("insertion");
        return database.insert("exercices", null, values);
    }

    public long updateExerciseComment(int id, String comment) {
        ContentValues values = new ContentValues();
        values.put("comment", comment);
        return database.update("exercices", values, "_id = "+id,null);
    }

    public long updateExerciseScore(int id, double score) {
        ContentValues values = new ContentValues();
        values.put("score", score);
        return database.update("exercices", values, "_id = "+id,null);
    }
    public long updateExerciseReadWordsCount(int id, int readWordsCount) {
        ContentValues values = new ContentValues();
        values.put("mots_lus", readWordsCount);
        return database.update("exercices", values, "_id = "+id,null);
    }

    public void deleteExercise(String name) {
        database.delete("exercices", "titre = ?",new String[]{name});
    }

    public int getExerciseIdByTitle(String title) {
        Cursor cursor = database.rawQuery("select _id from exercices where titre = ?",new String[] {title});
        Integer colId = null;
        if (cursor.moveToFirst()) {
            // The elements to retrieve

            // The associated index within the cursor
            int indexId = cursor.getColumnIndex(COLUMN_ID);
            // Browse the results list:
            int count = 0;
            do {
                colId = cursor.getInt(indexId);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return colId;

    }

    public String getPatientPasswordByMailAndFavouriteWord(String mail, String favouriteWord) {
        Cursor cursor = database.rawQuery("select mot_de_passe from patients where mail = ? and mot_prefere = ?", new String[]{mail,favouriteWord});

        String password = null;
        int indexPassword = cursor.getColumnIndex(COLUMN_MDP);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                password = cursor.getString(indexPassword);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return password;
    }

    public String getClinicianPasswordByMailAndFavouriteWord(String mail, String favouriteWord) {
        Cursor cursor = database.rawQuery("select mot_de_passe from cliniciens where mail = ? and mot_prefere = ?", new String[]{mail,favouriteWord});

        String password = null;
        int indexPassword = cursor.getColumnIndex(COLUMN_MDP);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                password = cursor.getString(indexPassword);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return password;
    }

    public String getPatientCommentById(int id) {
        Cursor cursor = database.rawQuery("select comment from patients where _id = ?", new String[]{String.valueOf(id)});

        String comment = null;
        int indexComment = cursor.getColumnIndex(COLUMN_COMMENT);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                comment = cursor.getString(indexComment);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return comment;
    }

    public Patient getPatientByPseudoAndPassword (String pseudo, String mdp) {
        Patient patient = null;
        Cursor cursor = database.rawQuery("select * from patients where pseudo = ? AND mot_de_passe = ?", new String[]{pseudo, mdp});

        Integer colId;
        String mail;
        String password;
        String login;
        String lastName;
        String firstName;
        String birthday;
        boolean gender;
        Language language = Language.Français;
        String comment;
        String favouriteWord;
        int clinicianInCharge;

        int indexId = cursor.getColumnIndex(COLUMN_ID);
        int indexMail = cursor.getColumnIndex(COLUMN_MAIL);
        int indexPassword = cursor.getColumnIndex(COLUMN_MDP);
        int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
        int indexLastName = cursor.getColumnIndex(COLUMN_NOM);
        int indexFirstName = cursor.getColumnIndex(COLUMN_PRENOM);
        int indexBirthday = cursor.getColumnIndex(COLUMN_DATE);
        int indexGender = cursor.getColumnIndex(COLUMN_GENRE);
        //int indexLanguage = cursor.getColumnIndex(COLUMN_LANGUE);
        int indexComment = cursor.getColumnIndex(COLUMN_COMMENT);
        int indexFavouriteWord = cursor.getColumnIndex(COLUMN_WORD);
        int indexClinicianInCharge = cursor.getColumnIndex(COLUMN_ID_CLINICIEN);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                colId = cursor.getInt(indexId);
                mail = cursor.getString(indexMail);
                password = cursor.getString(indexPassword);
                login = cursor.getString(indexPseudo);
                lastName = cursor.getString(indexLastName);
                firstName = cursor.getString(indexFirstName);
                birthday = cursor.getString(indexBirthday);
                gender = (cursor.getInt(indexGender) == 1);
                //language = cursor.getString(indexLanguage);
                comment = cursor.getString(indexComment);
                favouriteWord = cursor.getString(indexFavouriteWord);
                clinicianInCharge = cursor.getInt(indexClinicianInCharge);
                count++;
            } while (cursor.moveToNext());

            List sessions = new ArrayList();
            //sessions = getPatientByClinicianId(colId);
            try {
                patient = new Patient(mail, password, login, lastName, firstName, convertStringToDate(birthday), gender, Language.Français, clinicianInCharge, comment, favouriteWord, sessions);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return patient;
    }

    public Patient getPatientByPseudo (String pseudo) {
        Patient patient = null;
        Cursor cursor = database.rawQuery("select * from patients where pseudo = ?", new String[]{pseudo});

        Integer colId;
        String mail;
        String password;
        String login;
        String lastName;
        String firstName;
        String birthday;
        boolean gender;
        Language language = Language.Français;
        String comment;
        String favouriteWord;
        int clinicianInCharge;

        int indexId = cursor.getColumnIndex(COLUMN_ID);
        int indexMail = cursor.getColumnIndex(COLUMN_MAIL);
        int indexPassword = cursor.getColumnIndex(COLUMN_MDP);
        int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
        int indexLastName = cursor.getColumnIndex(COLUMN_NOM);
        int indexFirstName = cursor.getColumnIndex(COLUMN_PRENOM);
        int indexBirthday = cursor.getColumnIndex(COLUMN_DATE);
        int indexGender = cursor.getColumnIndex(COLUMN_GENRE);
        //int indexLanguage = cursor.getColumnIndex(COLUMN_LANGUE);
        int indexComment = cursor.getColumnIndex(COLUMN_COMMENT);
        int indexFavouriteWord = cursor.getColumnIndex(COLUMN_WORD);
        int indexClinicianInCharge = cursor.getColumnIndex(COLUMN_ID_CLINICIEN);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                colId = cursor.getInt(indexId);
                mail = cursor.getString(indexMail);
                password = cursor.getString(indexPassword);
                login = cursor.getString(indexPseudo);
                lastName = cursor.getString(indexLastName);
                firstName = cursor.getString(indexFirstName);
                birthday = cursor.getString(indexBirthday);
                gender = (cursor.getInt(indexGender) == 1);
                //language = cursor.getString(indexLanguage);
                comment = cursor.getString(indexComment);
                favouriteWord = cursor.getString(indexFavouriteWord);
                clinicianInCharge = cursor.getInt(indexClinicianInCharge);
                count++;
            } while (cursor.moveToNext());

            List sessions = new ArrayList();
            //sessions = getPatientByClinicianId(colId);
            try {
                patient = new Patient(mail, password, login, lastName, firstName, convertStringToDate(birthday), gender, Language.Français, clinicianInCharge, comment, favouriteWord, sessions);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return patient;
    }

    public String getPatientPseudoByPseudoAndPassword (String pseudo, String mdp) {
        Cursor cursor = database.rawQuery("select pseudo from patients where pseudo = ? AND mot_de_passe = ?", new String[]{pseudo, mdp});

        String login = null;
        int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                login = cursor.getString(indexPseudo);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        //cursor.close();
        return login;
    }

    public Patient getPatientByMailAndPassword (String mail, String mdp) {
        Patient patient = null;
        Cursor cursor = database.rawQuery("select * from patients where mail = ? AND mot_de_passe = ?", new String[]{mail, mdp});

        String email;
        String password;
        String login;
        String lastName;
        String firstName;
        String birthday;
        boolean gender;
        Language language = Language.Français;
        String comment;
        String favouriteWord;
        int clinicianInCharge;

        int indexMail = cursor.getColumnIndex(COLUMN_MAIL);
        int indexPassword = cursor.getColumnIndex(COLUMN_MDP);
        int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
        int indexLastName = cursor.getColumnIndex(COLUMN_NOM);
        int indexFirstName = cursor.getColumnIndex(COLUMN_PRENOM);
        int indexBirthday = cursor.getColumnIndex(COLUMN_DATE);
        int indexGender = cursor.getColumnIndex(COLUMN_GENRE);
        //int indexLanguage = cursor.getColumnIndex(COLUMN_LANGUE);
        int indexComment = cursor.getColumnIndex(COLUMN_COMMENT);
        int indexFavouriteWord = cursor.getColumnIndex(COLUMN_WORD);
        int indexClinicianInCharge = cursor.getColumnIndex(COLUMN_ID_CLINICIEN);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                email = cursor.getString(indexMail);
                password = cursor.getString(indexPassword);
                login = cursor.getString(indexPseudo);
                lastName = cursor.getString(indexLastName);
                firstName = cursor.getString(indexFirstName);
                birthday = cursor.getString(indexBirthday);
                gender = (cursor.getInt(indexGender) == 1);
                //language = cursor.getString(indexLanguage);
                comment = cursor.getString(indexComment);
                favouriteWord = cursor.getString(indexFavouriteWord);
                clinicianInCharge = cursor.getInt(indexClinicianInCharge);
                count++;
            } while (cursor.moveToNext());

            List sessions = new ArrayList();
            //sessions = getPatientByClinicianId(colId);
            try {
                patient = new Patient(email, password, login, lastName, firstName, convertStringToDate(birthday), gender, Language.Français, clinicianInCharge, comment, favouriteWord, sessions);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return patient;
    }

    public String getPatientPseudoByMailAndPassword (String mail, String mdp) {
        Cursor cursor = database.rawQuery("select pseudo from patients where mail = ? AND mot_de_passe = ?", new String[]{mail, mdp});

        String login = null;
        int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                login = cursor.getString(indexPseudo);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return login;
    }

    public int getPatientIdByPseudo(String pseudo) {
        Cursor cursor = database.rawQuery("select _id from patients where pseudo = ?",new String[] {pseudo});
        //Cursor cursor = database.rawQuery("Select * from cliniciens where pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null);
        Integer colId = null;
        if (cursor.moveToFirst()) {
            // The elements to retrieve

            // The associated index within the cursor
            int indexId = cursor.getColumnIndex(COLUMN_ID);
            // Browse the results list:
            int count = 0;
            do {
                colId = cursor.getInt(indexId);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return colId;
    }

    public Clinician getClinicianByPseudoAndPassword (String pseudo, String mdp){
        Clinician clinician = null;
        Cursor cursor = database.rawQuery("select * from cliniciens where pseudo = ? AND mot_de_passe = ?",new String[] {pseudo, mdp});
        //Cursor cursor = database.rawQuery("Select * from cliniciens where pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null);

        if (cursor.moveToFirst()) {
            // The elements to retrieve
            Integer colId;
            String login;
            String mail;
            String password;
            String favouriteWord;
            // The associated index within the cursor
            int indexId = cursor.getColumnIndex(COLUMN_ID);
            int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
            int indexMail = cursor.getColumnIndex(COLUMN_MAIL);
            int indexPassword = cursor.getColumnIndex(COLUMN_MDP);
            int indexFavouriteWord = cursor.getColumnIndex(COLUMN_WORD);
            // Browse the results list:
            int count = 0;
            do {
                colId = cursor.getInt(indexId);
                login = cursor.getString(indexPseudo);
                mail = cursor.getString(indexMail);
                password = cursor.getString(indexPassword);
                favouriteWord = cursor.getString(indexFavouriteWord);
                count++;
            } while (cursor.moveToNext());

            clinician = new Clinician(mail, password, login, favouriteWord, null);
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return clinician;
    }

    public String getClinicianPseudoByPseudoAndPassword (String pseudo, String mdp) {
        Cursor cursor = database.rawQuery("select pseudo from cliniciens where pseudo = ? AND mot_de_passe = ?", new String[]{pseudo, mdp});

        String login = null;
        int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                login = cursor.getString(indexPseudo);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        //cursor.close();
        return login;
    }

    public Clinician getClinicianByMailAndPassword (String mail, String mdp){
        Clinician clinician = null;
        Cursor cursor = database.rawQuery("select * from cliniciens where mail = ? AND mot_de_passe = ?",new String[] {mail, mdp});
        //Cursor cursor = database.rawQuery("Select * from cliniciens where pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null);

        if (cursor.moveToFirst()) {
            // The elements to retrieve
            Integer colId;
            String login;
            String email;
            String password;
            String favouriteWord;
            // The associated index within the cursor
            int indexId = cursor.getColumnIndex(COLUMN_ID);
            int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
            int indexMail = cursor.getColumnIndex(COLUMN_MAIL);
            int indexPassword = cursor.getColumnIndex(COLUMN_MDP);
            int indexFavouriteWord = cursor.getColumnIndex(COLUMN_WORD);
            // Browse the results list:
            int count = 0;
            do {
                colId = cursor.getInt(indexId);
                login = cursor.getString(indexPseudo);
                email = cursor.getString(indexMail);
                password = cursor.getString(indexPassword);
                favouriteWord = cursor.getString(indexFavouriteWord);
                count++;
            } while (cursor.moveToNext());

            List patients = new ArrayList();
            patients = getPatientByClinicianId(colId);
            clinician = new Clinician(email, password, login, favouriteWord, patients);
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return clinician;
    }

    public String getClinicianPseudoByMailAndPassword (String mail, String mdp) {
        Cursor cursor = database.rawQuery("select pseudo from cliniciens where mail = ? AND mot_de_passe = ?", new String[]{mail, mdp});

        String login = null;
        int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                login = cursor.getString(indexPseudo);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return login;
    }

    public List<Patient> getPatientByClinicianId(int id) {
        List patients = new ArrayList();
        Cursor cursor = database.rawQuery("select * from patients where id_clinicien = ?", new String[] {Integer.toString(id)});

        String mail;
        String password;
        String login;
        String lastName;
        String firstName;
        String birthday;
        boolean gender;
        Language language = Language.Français;
        String comment;
        String favouriteWord;
        int clinicianInCharge;

        int indexMail = cursor.getColumnIndex(COLUMN_MAIL);
        int indexPassword = cursor.getColumnIndex(COLUMN_MDP);
        int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
        int indexLastName = cursor.getColumnIndex(COLUMN_NOM);
        int indexFirstName = cursor.getColumnIndex(COLUMN_PRENOM);
        int indexBirthday = cursor.getColumnIndex(COLUMN_DATE);
        int indexGender = cursor.getColumnIndex(COLUMN_GENRE);
        //int indexLanguage = cursor.getColumnIndex(COLUMN_LANGUE);
        int indexComment = cursor.getColumnIndex(COLUMN_COMMENT);
        int indexFavouriteWord = cursor.getColumnIndex(COLUMN_WORD);
        int indexClinicianInCharge = cursor.getColumnIndex(COLUMN_ID_CLINICIEN);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                mail = cursor.getString(indexMail);
                password = cursor.getString(indexPassword);
                login = cursor.getString(indexPseudo);
                lastName = cursor.getString(indexLastName);
                firstName = cursor.getString(indexFirstName);
                birthday = cursor.getString(indexBirthday);
                gender = (cursor.getInt(indexGender) == 1);
                //language = cursor.getString(indexLanguage);
                comment = cursor.getString(indexComment);
                favouriteWord = cursor.getString(indexFavouriteWord);
                clinicianInCharge = cursor.getInt(indexClinicianInCharge);
                try {
                    patients.add(new Patient(mail, password, login, lastName, firstName, convertStringToDate(birthday), gender, language, clinicianInCharge, comment, favouriteWord, null));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return patients;
    }

    public List<Exercise> getExerciseByPatientId(int id) {
        List exercises = new ArrayList();
        Cursor cursor = database.rawQuery("select * from exercices where id_patient = ?", new String[] {Integer.toString(id)});

        String name;
        String task;
        String creationDate;
        int readWordsCount;
        int session;

        int indexName = cursor.getColumnIndex(COLUMN_TITRE);
        int indexTask = cursor.getColumnIndex(COLUMN_TASK);
        int indexCreationDate = cursor.getColumnIndex(COLUMN_DATE_CREATION);
        int indexReadWordsCount = cursor.getColumnIndex(COLUMN_MOTS_LUS);
        int indexSession = cursor.getColumnIndex(COLUMN_ID_SESSION);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                name = cursor.getString(indexName);
                task = cursor.getString(indexTask);
                creationDate = cursor.getString(indexCreationDate);
                readWordsCount = cursor.getInt(indexReadWordsCount);
                session = cursor.getInt(indexSession);
                exercises.add(new Exercise(name, task, creationDate, readWordsCount,session));
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return exercises;
    }

    public String getExerciseCommentById(int id) {
        Cursor cursor = database.rawQuery("select comment from exercices where _id = ?", new String[]{String.valueOf(id)});

        String comment = null;
        int indexComment = cursor.getColumnIndex(COLUMN_COMMENT);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                comment = cursor.getString(indexComment);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return comment;
    }

    public String getExerciseTaskByTitle(String name) {
        Cursor cursor = database.rawQuery("select tache from exercices where titre = ?", new String[]{name});

        String task = null;
        int indexTask = cursor.getColumnIndex(COLUMN_TASK);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                task = cursor.getString(indexTask);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return task;
    }

    public String getExerciseMaxDurationByTitle(String name) {
        Cursor cursor = database.rawQuery("select duree_max from exercices where titre = ?", new String[]{name});

        String maxDuration = null;
        int indexMaxDuration = cursor.getColumnIndex(COLUMN_DUREE);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                maxDuration = cursor.getString(indexMaxDuration);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return maxDuration;
    }

    public int getClinicianIdByPseudo(String pseudo) {
        Cursor cursor = database.rawQuery("select _id from cliniciens where pseudo = ?",new String[] {pseudo});
        //Cursor cursor = database.rawQuery("Select * from cliniciens where pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null);
        Integer colId = null;
        if (cursor.moveToFirst()) {
            // The elements to retrieve

            // The associated index within the cursor
            int indexId = cursor.getColumnIndex(COLUMN_ID);
            // Browse the results list:
            int count = 0;
            do {
                colId = cursor.getInt(indexId);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return colId;
    }

    public Exercise getExerciseByTitle (String name) {
        Exercise exercise = null;
        Cursor cursor = database.rawQuery("select * from exercices where titre = ?", new String[]{name});

        String title;
        String task;
        String creationDate;
        int readWordsCount;
        int session;

        int indexTitle = cursor.getColumnIndex(COLUMN_TITRE);
        int indexTask = cursor.getColumnIndex(COLUMN_TASK);
        int indexCreationDate = cursor.getColumnIndex(COLUMN_DATE_CREATION);
        int indexReadWordsCount = cursor.getColumnIndex(COLUMN_MOTS_LUS);
        int indexSession = cursor.getColumnIndex(COLUMN_ID_SESSION);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                title = cursor.getString(indexTitle);
                task = cursor.getString(indexTask);
                creationDate = cursor.getString(indexCreationDate);
                readWordsCount = cursor.getInt(indexReadWordsCount);
                session = cursor.getInt(indexSession);
                count++;
            } while (cursor.moveToNext());

            exercise = new Exercise(title, task, creationDate, readWordsCount, session);
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return exercise;
    }

    public int getExerciseReadWordsCountByTitle(String name) {
        Cursor cursor = database.rawQuery("select mots_lus from exercices where titre = ?",new String[] {name});
        //Cursor cursor = database.rawQuery("Select * from cliniciens where pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null);
        Integer readWordsCount = null;
        if (cursor.moveToFirst()) {
            // The elements to retrieve

            // The associated index within the cursor
            int indexReadWordsCount = cursor.getColumnIndex(COLUMN_MOTS_LUS);
            // Browse the results list:
            int count = 0;
            do {
                readWordsCount = cursor.getInt(indexReadWordsCount);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return readWordsCount;
    }

    public int getExercisePatientIdByTitle(String name) {
        Cursor cursor = database.rawQuery("select id_patient from exercices where titre = ?",new String[] {name});
        //Cursor cursor = database.rawQuery("Select * from cliniciens where pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null);
        Integer patientId = null;
        if (cursor.moveToFirst()) {
            // The elements to retrieve

            // The associated index within the cursor
            int indexPatientId = cursor.getColumnIndex(COLUMN_ID_PATIENT);
            // Browse the results list:
            int count = 0;
            do {
                patientId = cursor.getInt(indexPatientId);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return patientId;
    }

    public String getPatientPseudoById(int patientId) {
        Cursor cursor = database.rawQuery("select pseudo from patients where _id = ?",new String[] {Integer.toString(patientId)});
        //Cursor cursor = database.rawQuery("Select * from cliniciens where pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null);
        String pseudo = null;
        if (cursor.moveToFirst()) {
            // The elements to retrieve

            // The associated index within the cursor
            int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
            // Browse the results list:
            int count = 0;
            do {
                pseudo = cursor.getString(indexPseudo);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return pseudo;
    }

    public int getSessionIdFromTitle(String name) {
        Cursor cursor = database.rawQuery("select _id from sessions where titre = ?",new String[] {name});
        Integer colId = null;
        if (cursor.moveToFirst()) {
            // The elements to retrieve

            // The associated index within the cursor
            int indexId = cursor.getColumnIndex(COLUMN_ID);
            // Browse the results list:
            int count = 0;
            do {
                colId = cursor.getInt(indexId);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return colId;
    }

    public Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(date);
    }

    public List<Session> getSessionByPatientId(int id) {
        List sessions = new ArrayList();
        Cursor cursor = database.rawQuery("select * from sessions where id_patient = ?", new String[] {Integer.toString(id)});

        String name;
        double score;
        String comment;

        int indexName = cursor.getColumnIndex(COLUMN_TITRE);
        int indexScore = cursor.getColumnIndex(COLUMN_SCORE);
        int indexComment = cursor.getColumnIndex(COLUMN_COMMENT);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                name = cursor.getString(indexName);
                score = cursor.getDouble(indexScore);
                comment = cursor.getString(indexComment);
                sessions.add(new Session(name, score, comment));
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return sessions;
    }

    public String getSessionCommentById(int id) {
        Cursor cursor = database.rawQuery("select comment from sessions where _id = ?", new String[]{String.valueOf(id)});

        String comment = null;
        int indexComment = cursor.getColumnIndex(COLUMN_COMMENT);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                comment = cursor.getString(indexComment);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return comment;
    }

    public double getSessionScoreByTitle(String name) {
        Cursor cursor = database.rawQuery("select score from sessions where titre = ?", new String[] {name});

        double score = 0;

        int indexScore = cursor.getColumnIndex(COLUMN_SCORE);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                score = cursor.getDouble(indexScore);
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return score;
    }

    //Meryem
    public String getName() {

        int i=0;
        String[] columns = new String[]{ MySQLiteDatabase.COLUMN_ID, MySQLiteDatabase.COLUMN_PSEUDO };
        Cursor c = database.query(MySQLiteDatabase.TABLE_PATIENTS, columns, null, null, null, null, null);
        String name = "";
        c.moveToFirst();
        while(!c.isAfterLast()) {
            name = c.getString(c.getColumnIndex(MySQLiteDatabase.COLUMN_PSEUDO));
            c.moveToNext();
            i++;
        }
        c.close();
        return name;
    }
}