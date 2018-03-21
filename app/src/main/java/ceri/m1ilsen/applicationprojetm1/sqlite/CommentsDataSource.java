package ceri.m1ilsen.applicationprojetm1.sqlite;

/**
 * Created by Laurent on 31/01/2018.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import ceri.m1ilsen.applicationprojetm1.comment.Comment;
import ceri.m1ilsen.applicationprojetm1.exercise.Session;
import ceri.m1ilsen.applicationprojetm1.language.Language;
import ceri.m1ilsen.applicationprojetm1.user.*;

import static ceri.m1ilsen.applicationprojetm1.sqlite.MySQLiteDatabase.*;

public class CommentsDataSource {

    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteDatabase dbHelper;
    private String[] allColumns = { MySQLiteDatabase.COLUMN_ID,
            MySQLiteDatabase.COLUMN_COMMENT };

    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteDatabase(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteDatabase.COLUMN_COMMENT, comment);
        long insertId = database.insert(MySQLiteDatabase.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(MySQLiteDatabase.TABLE_COMMENTS,
                allColumns, MySQLiteDatabase.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteDatabase.TABLE_COMMENTS, MySQLiteDatabase.COLUMN_ID
                + " = " + id, null);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(MySQLiteDatabase.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return comments;
    }

    public boolean verificationPatientByPseudoAndPassword(String pseudo , String mdp){
        Cursor c = database.query("patients",new String[]{ "pseudo", "mail"},"pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
        if(c.getCount() == 0 ){
            c.close();
            return false;
        }else{
            c.close();
            return true;
        }

    }

    public boolean verificationPatientByMailAndPassword(String mail , String mdp){
        Cursor c = database.query("patients",new String[]{"pseudo", "mail"},"mail LIKE \""+mail+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
        if(c.getCount() == 0){
            c.close();
            return false;
        }else{
            c.close();
            return true;
        }
    }

    public boolean verificationClinicianByPseudoAndPassword(String pseudo , String mdp){
            Cursor c = database.query("cliniciens",new String[]{ "pseudo", "mail"},"pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
            if(c.getCount() == 0){
                c.close();
                return false;
            }else{
                c.close();
                return true;
            }
    }

    public boolean verificationClinicianByMailAndPassword(String mail , String mdp){
            Cursor c = database.query("cliniciens",new String[]{"pseudo", "mail"},"mail LIKE \""+mail+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
            if(c.getCount() == 0){
                return false;
            }else{
                return true;
            }
    }

    public long insertPatient(Patient patient) {

        ContentValues values = new ContentValues();
        values.put("pseudo", patient.getPseudo());
        values.put("mail", patient.getMail());
        values.put("mot_de_passe", patient.getPassword());
        values.put("date_de_naissance", patient.getBirthday().toString());
        values.put("genre", patient.isGender());
        values.put("langue", patient.getSpokenLanguage().toString());
        return database.insert("patients", null, values);
    }

    public long insertPatientOfAClinician(Patient patient, int clinicianInCharge) {

        ContentValues values = new ContentValues();
        values.put("pseudo", patient.getPseudo());
        values.put("mail", patient.getMail());
        values.put("mot_de_passe", patient.getPassword());
        values.put("date_de_naissance", patient.getBirthday().toString());
        values.put("genre", patient.isGender());
        values.put("langue", patient.getSpokenLanguage().toString());
        values.put("id_clinicien", clinicianInCharge);
        return database.insert("patients", null, values);
    }

    public long insertClinician(Clinician clinician) {

        ContentValues values = new ContentValues();
        values.put("pseudo", clinician.getPseudo());
        values.put("mail", clinician.getMail());
        values.put("mot_de_passe", clinician.getPassword());
        return database.insert("cliniciens", null, values);
    }

    public long insertSession(Session session) {

        ContentValues values = new ContentValues();
        values.put("date_creation", String.valueOf(session.getCreationDate()));
        values.put("id_patient",session.getPatient());
        return database.insert("sessions", null, values);
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }

    public int getPatientByPseudoAndPassword (String pseudo , String mdp){
      Cursor cursor = database.rawQuery("Select _id from patients where pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null);
      cursor.moveToFirst();
      return cursor.getInt(0);
    }

    public int getPatientByMailAndPassword (String mail , String mdp){
        Cursor cursor = database.rawQuery("Select _id from patients where pseudo LIKE \""+mail+"\" and mot_de_passe LIKE \""+mdp+"\"",null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public Patient getPatientById (int id){
        Patient patient = (Patient)database.rawQuery("Select * from patients where _id="+id,null);
        return patient;
    }

    public int getPatientId(Patient patient){
        Cursor cursor = database.rawQuery("Select _id from patients where pseudo LIKE \""+patient.getPseudo()+"\" and mot_de_passe LIKE \""+patient.getPassword()+"\"" , null);
        int id = cursor.getInt(0);
        return id;
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
            // The associated index within the cursor
            int indexId = cursor.getColumnIndex(COLUMN_ID);
            int indexPseudo = cursor.getColumnIndex(COLUMN_PSEUDO);
            int indexMail = cursor.getColumnIndex(COLUMN_MAIL);
            int indexPassword = cursor.getColumnIndex(COLUMN_MDP);
            // Browse the results list:
            int count = 0;
            do {
                colId = cursor.getInt(indexId);
                login = cursor.getString(indexPseudo);
                mail = cursor.getString(indexMail);
                password = cursor.getString(indexPassword);
                count++;
            } while (cursor.moveToNext());

            List patients = new ArrayList();
            patients = getPatientByClinicianId(colId);
            clinician = new Clinician(mail,password,login,patients);
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        return clinician;
    }

    public List<Patient> getPatientByClinicianId(int id) {
        //Patient[] patients = {new Patient("test","test","test","test","test",new Date(2),true, Language.Français,id,null,null)};
        List patients = new ArrayList();
        Cursor cursor = database.rawQuery("select * from patients where id_clinicien = ?", new String[] {Integer.toString(id)});

        Integer colId;
        String mail;
        String password;
        String login;
        String lastName;
        String firstName;
        String birthday;
        boolean gender;
        Language language = Language.Français;
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
                // ici parser string to data
                gender = (cursor.getInt(indexGender) == 1);
                //language = cursor.getString(indexLanguage);
                clinicianInCharge = cursor.getInt(indexClinicianInCharge);
                try {
                    patients.add(new Patient(mail,password,login,lastName,firstName,convertStringToDate(birthday),gender,language,clinicianInCharge,null,null));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                count++;
            } while (cursor.moveToNext());
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        return patients;
    }

    public int getClinicianIdByPseudo(String pseudo) {
        Clinician clinician = null;
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
        return colId;

    }

    public Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        return formatter.parse(date);
    }

    public String[] getSessions (int id){
        Cursor c = database.rawQuery("Select _id , date_creation from sessions where id_patient="+id,null);
        c.moveToFirst();
        String[] sessions = new String[c.getCount()+1];
        sessions[0]="sessions";
        for(int i=1; i<=c.getCount() ; i++) {
            sessions[i]="Session n° "+c.getInt(0)+" du "+c.getString(1);
            c.moveToNext();
        }
        return sessions;
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