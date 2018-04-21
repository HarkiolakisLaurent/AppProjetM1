package ceri.m1ilsen.applicationprojetm1.sqlite;

import android.content.Context;

/**
 * Created by Laurent on 31/01/2018.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteDatabase extends SQLiteOpenHelper {

    public static final String TABLE_PATIENTS = "patients";
    public static final String TABLE_CLINICIENS = "cliniciens";
    public static final String TABLE_SESSIONS = "sessions";
    public static final String TABLE_EXERCICES = "exercices";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID_CLINICIEN = "id_clinicien";
    public static final String COLUMN_ID_SESSION= "id_session";
    public static final String COLUMN_ID_PATIENT= "id_patient";

    public static final String COLUMN_PSEUDO = "pseudo";
    public static final String COLUMN_MAIL = "mail";
    public static final String COLUMN_NOM = "nom";
    public static final String COLUMN_PRENOM = "prenom";
    public static final String COLUMN_DATE= "date_de_naissance";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_LANGUE = "langue";
    public static final String COLUMN_MDP = "mot_de_passe";
    public static final String COLUMN_DATE_CREATION = "date_creation";
    public static final String COLUMN_TITRE = "titre";
    public static final String COLUMN_MOTS_LUS = "mots_lus";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_WORD = "mot_prefere";
    public static final String COLUMN_DUREE = "duree_max";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_TASK = "tache";

    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 16;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE_PATIENT = " create table " + TABLE_PATIENTS + "( " + COLUMN_ID
            + " integer primary key autoincrement, "+ COLUMN_PSEUDO + " text not null ," + COLUMN_MAIL +" text not null ,"
            + COLUMN_MDP +" text not null ," + COLUMN_NOM +" text not null ," + COLUMN_PRENOM + " prenom text not null ,"
            + COLUMN_DATE +" text not null , "+ COLUMN_GENRE +" boolean not null ," + COLUMN_LANGUE + " text not null ,"
            + COLUMN_COMMENT + " text ,"+ COLUMN_WORD + " text not null ,"+ COLUMN_ID_CLINICIEN +" integer );\n";

    private static final String DATABASE_CREATE_CLINICIAN = " create table " + TABLE_CLINICIENS + "( "
            + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_PSEUDO + " text not null ,"
            + COLUMN_MAIL +" text not null ,"+ COLUMN_MDP +" text not null ,"+ COLUMN_WORD + " text not null );";

    private static final String DATABASE_CREATE_SESSION = " create table " + TABLE_SESSIONS + "( "
            + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_TITRE +" text not null,"
            + COLUMN_COMMENT + " text ,"+ COLUMN_SCORE + " double ,"+ COLUMN_ID_PATIENT +" integer not null );";

    private static final String DATABASE_CREATE_EXERCISE = " create table " + TABLE_EXERCICES + "( "
            + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_TITRE +" text not null, "+ COLUMN_MOTS_LUS + " integer,"
            + COLUMN_COMMENT + " text ,"+ COLUMN_DATE_CREATION + " text ,"+ COLUMN_SCORE + " double , "+ COLUMN_TASK + " text, " + COLUMN_DUREE + " integer , "
            + COLUMN_ID_SESSION +" integer, "+ COLUMN_ID_PATIENT + " integer not null);";

    public MySQLiteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_PATIENT);
        database.execSQL(DATABASE_CREATE_CLINICIAN);
        database.execSQL(DATABASE_CREATE_SESSION);
        database.execSQL(DATABASE_CREATE_EXERCISE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLINICIENS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCICES);
        onCreate(db);
    }
}
