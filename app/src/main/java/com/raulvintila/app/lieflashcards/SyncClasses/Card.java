package com.raulvintila.app.lieflashcards.SyncClasses;


public class Card {

    private String card_question;
    private String card_answer;
    private String card_id;


    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCard_id() {
        return this.card_id;
    }

    public void setCard_question(String card_question) {
        this.card_question = card_question;
    }

    public String getCard_question() {
        return this.card_question;
    }

    public void setCard_answer (String card_answer) { this.card_answer = card_answer;}

    public String getCard_answer() { return this.card_answer; }

}