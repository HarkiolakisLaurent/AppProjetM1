package ceri.m1ilsen.applicationprojetm1.sqlite;

/**
 * Created by Laurent on 31/01/2018.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import ceri.m1ilsen.applicationprojetm1.comment.Comment;
import ceri.m1ilsen.applicationprojetm1.exercise.Session;
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

    public boolean verification(String pseudo , String mdp){
        Cursor c = database.query("patients",new String[]{ "pseudo", "mail"},"pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
        if(c.getCount() == 0 ){
            c.close();
            return false;
        }else{
            c.close();
            return true;
        }

    }

    public boolean verificationM(String mail , String mdp){
        Cursor c = database.query("patients",new String[]{"pseudo", "mail"},"mail LIKE \""+mail+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
        if(c.getCount() == 0){
            c.close();
            return false;
        }else{
            c.close();
            return true;
        }
    }

    public boolean verificationC(String pseudo , String mdp){
            Cursor c = database.query("cliniciens",new String[]{ "pseudo", "mail"},"pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
            if(c.getCount() == 0){
                c.close();
                return false;
            }else{
                c.close();
                return true;
            }
    }

    public boolean verificationCM(String mail , String mdp){
            Cursor c = database.query("cliniciens",new String[]{"pseudo", "mail"},"mail LIKE \""+mail+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
            if(c.getCount() == 0){
                return false;
            }else{
                return true;
            }
    }

    public long insertPatient(Patient P) {

        ContentValues values = new ContentValues();
        values.put("pseudo", P.getPseudo());
        values.put("mail", P.getMail());
        values.put("mot_de_passe", P.getPassword());
        values.put("date_de_naissance", P.getBirthday().toString());
        values.put("genre", P.isGender());
        values.put("langue", P.getSpokenLanguage().toString());
        return database.insert("patients", null, values);
    }

    public long insertClinicien(Clinician P) {

        ContentValues values = new ContentValues();
        values.put("pseudo", P.getPseudo());
        values.put("mail", P.getMail());
        values.put("mot_de_passe", P.getPassword());
        return database.insert("cliniciens", null, values);
    }

    public long insertSession(Session S) {

        ContentValues values = new ContentValues();
        values.put("date_creation", String.valueOf(S.getCreationDate()));
        values.put("id_patient",S.getPatient());
        return database.insert("sessions", null, values);
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }

    public int getPatientP (String pseudo , String mdp){
      Cursor c = database.rawQuery("Select _id from patients where pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null);
      c.moveToFirst();
      return c.getInt(0);
    }

    public int getPatientM (String mail , String mdp){
        Cursor c = database.rawQuery("Select _id from patients where pseudo LIKE \""+mail+"\" and mot_de_passe LIKE \""+mdp+"\"",null);
        c.moveToFirst();
        return c.getInt(0);
    }

    public Patient getPatientI (int id){
        Patient p = (Patient)database.rawQuery("Select * from patients where _id="+id,null);
        return p;
    }

    public int getPatient_id(Patient patient){
        Cursor c=database.rawQuery("Select _id from patients where pseudo LIKE \""+patient.getPseudo()+"\" and mot_de_passe LIKE \""+patient.getPassword()+"\"" , null);
        int id=c.getInt(0);
        return id;
    }

    public Clinician getClinicianFromMailAndPassword (String pseudo, String mdp){
        Cursor cursor = database.rawQuery("Select * from cliniciens where pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null);
        Clinician clinician = null;
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
                System.out.println("id : "+colId+", login : "+login+", mail : "+mail);
                /*Toast.makeText(this,
                        "Retrieve element :" + login + "," + password + " ("
                                + colId + ")", Toast.LENGTH_LONG).show();*/
                count++;
            } while (cursor.moveToNext());
            //clinician = new Clinician(mail,password,login,lastName,firstName,listPatients);
            /*Toast.makeText(this,
                    "The number of elements retrieved is " + count,
                    Toast.LENGTH_LONG).show();*/
        } else {
            //Toast.makeText(this, "No element found : ", Toast.LENGTH_LONG).show();
        }
        return clinician;
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