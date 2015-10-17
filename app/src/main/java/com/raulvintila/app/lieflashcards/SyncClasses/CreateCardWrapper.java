package com.raulvintila.app.lieflashcards.SyncClasses;

import com.raulvintila.app.lieflashcards.Database.dao.DBCard;

public class CreateCardWrapper {
    private DBCard card;
    private String remote_deck_id;


    public CreateCardWrapper() {}

    public CreateCardWrapper(DBCard card, String remote_deck_id) {
        this.card = card;
        this.remote_deck_id = remote_deck_id;
    }

    public void setCard(DBCard card) {
        this.card = card;
    }

    public DBCard getCard() {
        return this.card;
    }

    public void setRemote_deck_id(String remote_deck_id) {
        this.remote_deck_id = remote_deck_id;
    }

    public String getRemote_deck_id() {
        return this.remote_deck_id;
    }
}
