package com.flatbrains.sushigoal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQListeBase extends SQLiteOpenHelper {

    private static final String TABLE_PLAYERS = "table_player";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";
    private static final String COL_SCORE = "Score";
    private static final String COL_ALL_TIME_SCORE = "All_time_score";
    private static final String COL_PARTIES_PLAYED = "Parties_played";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_PLAYERS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT NOT NULL, "
            + COL_SCORE + " INTEGER NOT NULL, " + COL_ALL_TIME_SCORE + " INTEGER NOT NULL, "
            + COL_PARTIES_PLAYED + " INTEGER NOT NULL);";

    public MySQListeBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_PLAYERS + ";");
        onCreate(db);
    }

}