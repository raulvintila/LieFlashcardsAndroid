package com.raulvintila.app.lieflashcards.RecyclerItems;

import android.util.Log;

/**
 * Created by sendroiu on 8/13/15.
 */
public class CardRecyclerViewItem {


    private int position;
    private Long card_id;
    private String question;
    private String answer;
    private long deck_id;

    public CardRecyclerViewItem(int position , Long card_id , String question , String answer, long deck_id){
        this.position = position;
        this.card_id = card_id;
        this.question = question;
        this.answer = answer;
        this.deck_id = deck_id;
    }

    public void setPosition( int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }

    public void setQuestion(String question){
        this.question =question;
    }

    public String getQuestion(){
        return question;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getAnswer(){
        return answer;
    }

    public void setCard_id(Long card_id){
        this.card_id = card_id;
    }

    public Long getCard_id(){
        return card_id;
    }

    public void setDard_id(long deck_id){
        this.deck_id = deck_id;
    }

    public Long getDeck_id(){
        return deck_id;
    }
}
