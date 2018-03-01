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

import ceri.m1ilsen.applicationprojetm1.comment.Comment;
import ceri.m1ilsen.applicationprojetm1.user.*;
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
            c = database.query("cliniciens",new String[]{ "pseudo", "mail"},"pseudo LIKE \""+pseudo+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
            if(c.getCount() == 0){
                c.close();
                return false;
            }else{
                c.close();
                return true;
            }
        }else{
            c.close();
            return true;
        }

    }

    public boolean verificationM(String mail , String mdp){
        Cursor c = database.query("patients",new String[]{"pseudo", "mail"},"mail LIKE \""+mail+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
        if(c.getCount() == 0 ){
            c = database.query("cliniciens",new String[]{"pseudo", "mail"},"mail LIKE \""+mail+"\" and mot_de_passe LIKE \""+mdp+"\"",null,null,null,null);
            if(c.getCount() == 0){
                return false;
            }else{
                return true;
            }
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

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
}