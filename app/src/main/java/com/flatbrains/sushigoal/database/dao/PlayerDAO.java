package com.flatbrains.sushigoal.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.flatbrains.sushigoal.database.MySQListeBase;
import com.flatbrains.sushigoal.database.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 28/12/2017.
 */

public class PlayerDAO {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "sushiGoal.db";

    private static final String TABLE_PLAYERS = "table_player";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NAME = "Name";
    private static final int NUM_COL_NAME = 1;
    private static final String COL_SCORE = "Score";
    private static final int NUM_COL_SCORE = 2;
    private static final String COL_ALL_TIME_SCORE = "All_time_score";
    private static final int NUM_COL_ALL_TIME_SCORE = 3;
    private static final String COL_PARTIES_PLAYED = "Parties_played";
    private static final int NUM_COL_PARTIES_PLAYED = 4;

    private static String[] SELECT_ALL_PLAYER = new String[]{COL_ID, COL_NAME, COL_SCORE, COL_ALL_TIME_SCORE, COL_PARTIES_PLAYED};

    private SQLiteDatabase db;
    private MySQListeBase mySQListe;

    public PlayerDAO(Context context) {
        mySQListe = new MySQListeBase(context, DB_NAME, null, DB_VERSION);
    }

    public void open() {
        db = mySQListe.getReadableDatabase();
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDB() {
        return db;
    }

    /**
     * crée un nouveau joueur à appeler que quand un joueur n'existe pas dans la DB
     *
     * @param player
     * @return
     */
    public long createPlayer(Player player) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, player.getName()); //Ajout joueur
        values.put(COL_SCORE, 0); //Score à 0 par défaut
        values.put(COL_ALL_TIME_SCORE, 0); //Score à 0 par défaut
        values.put(COL_PARTIES_PLAYED, 0); //nb parties à 0 par défaut

        return db.insert(TABLE_PLAYERS, null, values);
    }

    /**
     * crée un nouveau joueur à appeler que quand un joueur n'existe pas dans la DB
     *
     * @param name
     * @return
     */
    public long createPlayer(String name) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name); //Ajout joueur
        values.put(COL_SCORE, 0); //Score à 0 par défaut
        values.put(COL_ALL_TIME_SCORE, 0); //Score à 0 par défaut
        values.put(COL_PARTIES_PLAYED, 0); //nb parties à 0 par défaut

        return db.insert(TABLE_PLAYERS, null, values);
    }

    public int updatePlayer(int id, Player player) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, player.getName());
        values.put(COL_SCORE, player.getScore());
        values.put(COL_ALL_TIME_SCORE, player.getAllTimeSore());
        values.put(COL_PARTIES_PLAYED, player.getPartyPlayed());

        return db.update(TABLE_PLAYERS, values, colId(id), null);
    }

    public Player getPlayerById(long id) {
        Cursor c = db.query(TABLE_PLAYERS, SELECT_ALL_PLAYER
                , COL_ID + " =\"" + id + "\"", null, null, null, null);
        Player player = cursorToPlayer(c);
        c.close();
        return player;
    }

    public int removePlayer(int id) {
        return db.delete(TABLE_PLAYERS, colId(id), null);
    }

    public Player getPlayerByName(String name) {
        Cursor c = db.query(TABLE_PLAYERS, SELECT_ALL_PLAYER
                , COL_NAME + " LIKE\"" + name + "\"", null, null, null, null);
        return cursorToPlayer(c);
    }

    public List<Player> getAllPlayers() {
        Cursor c = db.query(TABLE_PLAYERS, SELECT_ALL_PLAYER, null, null, null, null, null);
        return cursorToPlayersList(c);
    }

    private List<Player> cursorToPlayersList(Cursor c) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            players.add(cursorToPlayer(c));
        }
        c.close();
        return players;
    }

    private Player cursorToPlayer(Cursor c) {
        if (c != null && c.getCount() == 0) {
            return null;
        } else {
            c.moveToFirst();
        }

        Player player = new Player();

        player.setId(c.getInt(NUM_COL_ID));
        player.setName(c.getString(NUM_COL_NAME));
        player.setScore(c.getInt(NUM_COL_SCORE));
        player.setAllTimeSore(c.getInt(NUM_COL_ALL_TIME_SCORE));
        player.setPartyPlayed(c.getInt(NUM_COL_PARTIES_PLAYED));

        return player;

    }

    private String colId(int id) {
        return COL_ID + " = " + id;
    }

    public boolean existPlayer(String name) {
        if (getPlayerByName(name) != null) {
            return true;
        } else {
            return false;
        }
    }


}
