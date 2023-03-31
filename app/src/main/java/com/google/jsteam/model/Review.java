package com.google.jsteam.model;

public class Review {
    private int     id,
                    userID,
                    gameID;
    private String  review;

    public Review(int id, int userID, int gameID, String review) {
        this.id = id;
        this.userID = userID;
        this.gameID = gameID;
        this.review = review;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
