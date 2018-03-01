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
    public static final String COLUMN_ID2 = "_id";
    public static final String COLUMN_PSEUDO = "pseudo";
    public static final String COLUMN_MAIL = "mail";
    public static final String COLUMN_DATE= "date_de_naissance";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_LANGUE = "langue";
    public static final String COLUMN_MDP = "mot_de_passe";
    public static final String COLUMN_CLINICIEN = "clinicien";

    public static final String TABLE_CLINICIENS = "cliniciens";
    public static final String COLUMN_ID3 = "_id";
    public static final String COLUMN_PSEUDO2 = "pseudo";
    public static final String COLUMN_MAIL2 = "mail";
    public static final String COLUMN_DATE2= "mot_de_passe";

    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMENT = "comment";

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table " + TABLE_PATIENTS + "( " + COLUMN_ID2
            + " integer primary key autoincrement, "+ COLUMN_PSEUDO
            + " text not null ,"+ COLUMN_MAIL +" text not null ,"+COLUMN_MDP+" text not null ,"
            + COLUMN_DATE +" text not null , "+ COLUMN_GENRE +" boolean not null ,"
            + COLUMN_LANGUE+ " text not null ,"+ COLUMN_CLINICIEN +" integer );\n"
            ;

    private static final String DATABASE_CREATE2 = "create table " + TABLE_CLINICIENS + "( " + COLUMN_ID3
            + " integer primary key autoincrement, "+ COLUMN_PSEUDO2
            + " text not null ,"+ COLUMN_MAIL2 +" text not null ,"+COLUMN_DATE2+" text not null );";

    public MySQLiteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE2);
        System.out.println("CREATION DE LA BDD");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        onCreate(db);
    }
}
