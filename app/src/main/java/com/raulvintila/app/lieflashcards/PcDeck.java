package com.raulvintila.app.lieflashcards;

import java.util.ArrayList;
import java.util.List;

public class PcDeck
{
    private String name;
    private List<PcCard> cards = new ArrayList<>();

    public PcDeck(String name, List<PcCard> cards)
    {
        this.name = name;
        this.cards = cards;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<PcCard> getCards()
    {
        return cards;
    }

    public void setCards(List<PcCard> cards)
    {
        this.cards = cards;
    }
}
