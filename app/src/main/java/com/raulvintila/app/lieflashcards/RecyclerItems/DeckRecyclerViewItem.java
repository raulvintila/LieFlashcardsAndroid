package com.raulvintila.app.lieflashcards.RecyclerItems;

public class DeckRecyclerViewItem {

    private int photoId;
    private int position;
    private int nr_of_cards;
    private Long deckId;
    private String name;
    private String cards;
    private String time;

    public DeckRecyclerViewItem(int position, String name, String cards, int photoId,String time, int nr_of_cards, Long deckId) {
        this.position = position;
        this.name = name;
        this.cards = cards;
        this.photoId = photoId;
        this.time = time;
        this.nr_of_cards = nr_of_cards;
        this.deckId = deckId;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public Long getDeckId() {
        return this.deckId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getNr_of_cards(){
        return nr_of_cards;
    }

    public  void setNr_of_cards(int nr_of_cards){
        this.nr_of_cards = nr_of_cards;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards){
        this.cards = cards;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId){
        this.photoId = photoId;
    }
}