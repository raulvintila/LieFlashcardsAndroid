package com.raulvintila.app.lieflashcards.SyncClasses;

import com.raulvintila.app.lieflashcards.SyncClasses.Card;

import java.util.List;

public class Deck {
    private String deck_id;
    private String deck_name;
    private List<String> deck_tags;
    private List<Card> deck_cards;


    public void setDeck_id(String deck_id) {
        this.deck_id = deck_id;
    }

    public String getDeck_id() {
        return this.deck_id;
    }

    public void setDeck_name(String deck_name) {
        this.deck_name = deck_name;
    }

    public String getDeck_name() {
        return this.deck_name;
    }

    public void setDeck_tags(List<String> deck_tags) {
        this.deck_tags = deck_tags;
    }

    public List<String> getDeck_tags() {
        return this.deck_tags;
    }

    public void setDeck_cards(List<Card> deck_cards) {
        this.deck_cards = deck_cards;
    }

    public List<Card> getDeck_cards() {
        return this.deck_cards;
    }
}
