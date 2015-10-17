package com.raulvintila.app.lieflashcards.SyncClasses;

// Wrapped Deck Request Returned Message

import com.raulvintila.app.lieflashcards.Database.dao.DBCard;

public class WrappedCardRRM {
    private RequestReturnedMessage rrm;
    private DBCard card;


    public WrappedCardRRM() { }

    public WrappedCardRRM(DBCard card, RequestReturnedMessage rrm) {
        this.card = card;
        this.rrm = rrm;
    }

    public void setRrm(RequestReturnedMessage rrm) {
        this.rrm = rrm;
    }

    public RequestReturnedMessage getRrm() {
        return this.rrm;
    }

    public void setCard(DBCard card) {
        this.card = card;
    }

    public DBCard getCard() {
        return this.card;
    }
}