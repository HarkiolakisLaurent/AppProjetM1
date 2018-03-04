package ceri.m1ilsen.applicationprojetm1.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by le fléo on 26/02/2018.
 */

public class CliniHelper extends SQLiteOpenHelper {


    public static final String TABLE_CLINICIENS = "cliniciens";
    public static final String COLUMN_ID3 = "_id";
    public static final String COLUMN_PSEUDO2 = "pseudo";
    public static final String COLUMN_MAIL2 = "mail";
    public static final String COLUMN_DATE2= "mot_de_passe";


    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = " create table " + TABLE_CLINICIENS + "( " + COLUMN_ID3
            + " integer primary key autoincrement, "+ COLUMN_PSEUDO2
            + " text not null ,"+ COLUMN_MAIL2 +" text not null ,"+COLUMN_DATE2+" text not null ); "
            ;

    public CliniHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        System.out.println("CREATION DE LA BDD");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLINICIENS);
        onCreate(db);
    }
}