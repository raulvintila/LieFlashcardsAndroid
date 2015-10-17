package com.raulvintila.app.lieflashcards.SyncClasses;

import java.util.List;


import java.util.List;

public class Payload {
    private String user_id;
    private String test;
    private String deck_id;
    private String deck_name;
    private String card_question;
    private String card_answer;
    private String card_deck;
    private String card_id;
    private List<String> deck_tags;
    private List<String> deck_cards;
    private String user_session;
    private String question_type;
    private String answer_type;

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public String getQuestion_type() {
        return this.question_type;
    }

    public void setAnswer_type(String answer_type) {
        this.answer_type = answer_type;
    }

    public String getAnswer_type() {
        return this.answer_type;
    }

    public void setUser_session(String user_session) {
        this.user_session = user_session;
    }

    public String getUser_session() {
        return this.user_session;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCard_id() {
        return this.card_id;
    }

    public void setCard_deck(String card_deck) {
        this.card_deck = card_deck;
    }

    public String getCard_deck() {
        return this.card_deck;
    }

    public void setCard_answer(String card_answer) {
        this.card_answer = card_answer;
    }

    public String getCard_answer() {
        return this.card_answer;
    }

    public void setCard_question(String card_question) {
        this.card_question = card_question;
    }

    public String getCard_question() {
        return this.card_question;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return this.test;
    }

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

    public List<String> getDeck_tags () {
        return this.deck_tags;
    }

    public void setDeck_cards(List<String> deck_cards) {
        this.deck_tags = deck_cards;
    }

    public List<String> getDeck_cards() {
        return this.deck_cards;
    }
}
