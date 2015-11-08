package com.raulvintila.app.lieflashcards.SyncClasses;

import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.Utils.NetworkUtils;

import java.util.List;

public class SyncWorker {

    private IDatabaseManager databaseManager;

    public SyncWorker(IDatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void deleteRemoteDecks(List<Deck> remote_decks, List<DBDeck> local_decks, String user_id) {

        boolean FOUND_LOCALLY;

        for(int i = 0; i < remote_decks.size(); i++) {
            FOUND_LOCALLY = false;
            for(int j = 0; j < local_decks.size(); j++) {
                if(remote_decks.get(i).getDeck_id().equals(local_decks.get(j).getRemoteId())) {
                    FOUND_LOCALLY = true;
                    break;
                }
            }
            if(!FOUND_LOCALLY) {
                /* If the remote Deck is not found in the list of local Decks it means
                   that it was deleted by the user. So we unlink it on the server.  */
                new NetworkUtils().deleteRemoteDeck(user_id, remote_decks.get(i).getDeck_id());
            }

            //TODO : Actually delete the deck from the remoteDatabase ( once the API supports that )
        }
    }

    public DBDeck createRemoteDeck(DBDeck deck) {

        /* Inserting the deck in the server *//*
        WrappedDeckRRM wDeckRRM = new NetworkUtils().createRemoteDeck(deck);

        DBDeck updated_deck = wDeckRRM.getDeck();
        String remote_deck_id = wDeckRRM.getRrm().getPayload().getDeck_id();

        *//* Updating the local deck with the remoteId *//*
        updated_deck.setRemoteId(remote_deck_id);
        databaseManager.insertOrUpdateDeck(updated_deck);

        return updated_deck;*/
        return deck;
    }

    public void linkDeckToUser(DBDeck deck, String user_id) {
        new NetworkUtils().linkDeckToUser(user_id, deck);
    }

    public void createRemoteCard(DBCard card, DBDeck deck) {

        /* Wrapping the card & deck_remote_id because the AsyncTask can take parameters of only one type *//*
        CreateCardWrapper wrapper = new CreateCardWrapper(card, deck.getRemoteId());
        *//* Inserting the card in the server *//*
        WrappedCardRRM wCardRRM = new NetworkUtils().createRemoteCard(wrapper);

        DBCard created_card = wCardRRM.getCard();
        String remote_card_id = wCardRRM.getRrm().getPayload().getCard_id();

        *//* Updating the local card with the remoteId *//*
        created_card.setRemoteId(remote_card_id);
        databaseManager.insertOrUpdateCard(wCardRRM.getCard());*/
    }

    public DBDeck updateRemoteDeck(DBDeck deck) {
        WrappedDeckRRM wDeckRRM = new NetworkUtils().updateRemoteDeck(deck);

        return wDeckRRM.getDeck();
    }

    public void deleteRemoteCards(List<DBCard> local_cards, List<Card> remote_cards) {
        boolean FOUND_CARD_LOCALLY;

        for(int i = 0; i < remote_cards.size(); i++) {
            FOUND_CARD_LOCALLY = false;
            for(int j = 0; j < local_cards.size(); j++) {
                if(remote_cards.get(i).getCard_id().equals(local_cards.get(j).getRemoteId())) {
                    FOUND_CARD_LOCALLY = true;
                    break;
                }
            }
            if(!FOUND_CARD_LOCALLY) {
                /* If the remote Card is not found in the list of local cards it means
                   that it was deleted by the user. So we delete it on the server.  */
                new NetworkUtils().deleteRemoteCard(remote_cards.get(i).getCard_id());
            }
        }
    }

    public void updateRemoteCard(DBCard card) {
        new NetworkUtils().updateRemoteCard(card);
    }
}
