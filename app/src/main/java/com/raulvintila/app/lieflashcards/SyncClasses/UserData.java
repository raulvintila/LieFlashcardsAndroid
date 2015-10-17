package com.raulvintila.app.lieflashcards.SyncClasses;


import com.raulvintila.app.lieflashcards.SyncClasses.Deck;

import java.util.List;

public class UserData {


    private String user_id;
    private String user_email;
    private String user_name;
    private List<Deck> user_decks;

    private Boolean failed;

    public void setFailed(Boolean failed) {
        this.failed = failed;
    }

    public Boolean getFailed() {
        return this.failed;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_email() {
        return this.user_email;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_decks(List<Deck> user_decks) {
        this.user_decks = user_decks;
    }

    public List<Deck> getUser_decks() {
        return this.user_decks;
    }

    public Deck getDeckbyId(String deck_id){
        Deck deck= new Deck();
        for(int i=0;i<user_decks.size();i++){
            if(user_decks.get(i).getDeck_id().equals(deck_id)){
                deck = user_decks.get(i);
                break;
            }
        }
        return deck;
    }
}

