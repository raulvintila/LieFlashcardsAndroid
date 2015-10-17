package com.raulvintila.app.lieflashcards.SyncClasses;

import java.util.List;

public class User {
    private String user_id;
    private String user_email;
    private String user_name;
    private List<String> user_decks;

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

    public void setUser_decks(List<String> user_decks) {
        this.user_decks = user_decks;
    }

    public List<String> getUser_decks() {
        return this.user_decks;
    }
}
