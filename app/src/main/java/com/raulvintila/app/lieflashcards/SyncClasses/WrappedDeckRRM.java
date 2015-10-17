package com.raulvintila.app.lieflashcards.SyncClasses;


// Wrapped Deck Request Returned Message

import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;

public class WrappedDeckRRM {
    private RequestReturnedMessage rrm;
    private DBDeck deck;

    public WrappedDeckRRM() { }

    public WrappedDeckRRM(DBDeck deck, RequestReturnedMessage rrm) {
        this.deck = deck;
        this.rrm = rrm;
    }

    public void setRrm(RequestReturnedMessage rrm) {
        this.rrm = rrm;
    }

    public RequestReturnedMessage getRrm() {
        return this.rrm;
    }

    public void setDeck(DBDeck deck) {
        this.deck = deck;
    }

    public DBDeck getDeck() {
        return this.deck;
    }
}
