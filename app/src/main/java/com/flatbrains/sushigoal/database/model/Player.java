package com.flatbrains.sushigoal.database.model;

/**
 * Created by Nicolas on 28/12/2017.
 */

public class Player {

    private int id;
    private String name;
    private int score;
    private int allTimeSore;
    private int partyPlayed;

    public Player() {
    }

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAllTimeSore() {
        return allTimeSore;
    }

    public void setAllTimeSore(int allTimeSore) {
        this.allTimeSore = allTimeSore;
    }

    public int getPartyPlayed() {
        return partyPlayed;
    }

    public void setPartyPlayed(int partyPlayed) {
        this.partyPlayed = partyPlayed;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
