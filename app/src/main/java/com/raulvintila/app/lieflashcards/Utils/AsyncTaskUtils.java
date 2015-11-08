package com.raulvintila.app.lieflashcards.Utils;

import android.app.Activity;
import android.os.AsyncTask;

import com.raulvintila.app.lieflashcards.Activities.MainActivity;
import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.DatabaseManager;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.Fragments.TaskFragment;
import com.raulvintila.app.lieflashcards.SyncClasses.Card;
import com.raulvintila.app.lieflashcards.SyncClasses.Deck;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.SyncClasses.SyncWorker;
import com.raulvintila.app.lieflashcards.SyncClasses.UserData;

import java.util.List;

public class AsyncTaskUtils {

    private class AsyncSynchronize extends AsyncTask<Void, Void, Void> {

        private MainActivity activity;
        private boolean PULL_ACTION = false;
        private boolean PUSH_ACTION = false;
        private String user_id;
        private IDatabaseManager databaseManager;

        private SyncWorker doWork;

        private TaskFragment taskFragment;
        private static final int TASK_FRAGMENT = 0;

        // Tag so we can find the task fragment again, in another instance of this fragment after rotation.
        private static final String TASK_FRAGMENT_TAG = "task";

        public AsyncSynchronize(String user_id, String action) {
            databaseManager=new DatabaseManager(CustomModel.getInstance().getContext());
            doWork = new SyncWorker(databaseManager);
            this.user_id = user_id;
            if (action.equals("PULL_ACTION")) {
                PULL_ACTION = true;
            } else {
                PUSH_ACTION = true;
            }
        }

        @Override
        protected  void onPreExecute() {
            taskFragment = new TaskFragment();

            taskFragment.show(((MainActivity) CustomModel.getInstance().getContext()).getSupportFragmentManager(), TASK_FRAGMENT_TAG);
        }

        @Override
        protected Void doInBackground(Void ...params) {
            try {
                // Get UserData & (Check if Update is needed)*
                UserData user_data = new NetworkUtils().getUserData(user_id);
                List<DBDeck> local_decks = databaseManager.getAllDecks();
                List<Deck> remote_decks = user_data.getUser_decks();


                if(PUSH_ACTION) {
                    /* Deletes decks on the server */
                    doWork.deleteRemoteDecks(remote_decks, local_decks, user_id);

                    for(int k = 0; k < local_decks.size(); k++) {
                        DBDeck deck = local_decks.get(k);
                        /* This deck was never stored in the remote DB */
                        if (local_decks.get(k).getRemoteId() == null) {

                            /* Inserting the new deck in the server */
                            deck = doWork.createRemoteDeck(deck);
                            
                            /* Linking the deck with the user*/
                            doWork.linkDeckToUser(deck, user_id);
                            
                            /* Inserting all the cards related to this deck in the server */
                            List<DBCard> card_list = databaseManager.getCardsByDeckId(local_decks.get(k).getId());
                            for (int j = 0; j < card_list.size(); j++) { 
                                DBCard card = card_list.get(j);
                                
                                doWork.createRemoteCard(card, deck);
                            }
                            
                            /* Because the deck was new, there were no cards to update */
                        } 
                        /* This deck is already in the remote DB, we are looking for possible updates */
                        else {
                            
                            /* Update the deck on the server *//*
                            deck = doWork.updateRemoteDeck(deck);
                            
                            List<DBCard> local_cards = databaseManager.getCardsByDeckId(local_decks.get(k).getId());
                            List<Card> remote_cards = user_data.getDeckbyId(deck.getRemoteId()).getDeck_cards();
                            
                            *//* Deletes Deck cards on the server *//*
                            doWork.deleteRemoteCards(local_cards, remote_cards);
                            

                            for (int i = 0; i<local_cards.size();i++) {
                                DBCard card = local_cards.get(i);
                                if (local_cards.get(i).getRemoteId() == null) {
                                    *//* Inserting the new card on the server *//*
                                    doWork.createRemoteCard(card, deck);
                                } 
                                *//* This card is already in the remote DB, we are looking for possible updates *//*
                                else {
                                    doWork.updateRemoteCard(card);
                                }
                            }*/
                        }


                    }

                } else if (PULL_ACTION) { }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Void... progress) { }

        @Override
        protected void onPostExecute(Void result) {
            taskFragment.dismiss();
        }

        /**
         * Attaches an activity to the task
         * @param a The activity to attach
         */
        public void attach(Activity a)
        {
            this.activity = (MainActivity)a;
        }

        /**
         * Removes the activity from the task
         */
        public void detach()
        {
            this.activity = null;
        }
    }

    public void attemptSync(String user_id, String action) {
        AsyncSynchronize syncTask = new AsyncSynchronize(user_id, action);
        syncTask.execute();
    }

}

    /*private class AsyncUpdateCard extends AsyncTask<DBCard, Void, RequestReturnedMessage> {
        @Override
        protected RequestReturnedMessage doInBackground(DBCard ...params) {
            try {
                DBCard card = params[0];

                URL url = new URL("http://lie-server.herokuapp.com/cards/update");
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setDoInput(true);
                httpCon.setRequestMethod("PUT");
                httpCon.setRequestProperty("Content-Type", "application/json");
                httpCon.setRequestProperty("Accept", "application/json");
                httpCon.setUseCaches(false);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("card_id", card.getRemoteId());
                jsonParam.put("card_question", card.getQuestion());
                jsonParam.put("card_answer", card.getAnswer());

                OutputStream printout = new DataOutputStream(httpCon.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(printout, "UTF-8"));
                writer.write(jsonParam.toString());
                writer.close();
                printout.close();

                String line;
                StringBuilder result = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                Gson gson = new Gson();
                RequestReturnedMessage message = gson.fromJson(result.toString(), RequestReturnedMessage.class);

                return message;
            } catch (Exception e) {
                return new RequestReturnedMessage();
            }

        }

        @Override
        protected void onProgressUpdate(Void... progress) { }

        @Override
        protected void onPostExecute(RequestReturnedMessage result) { }
    }

    public void executeAsyncUpdateCard(DBCard param) {
        AsyncUpdateCard updateCard_task = new AsyncUpdateCard();
        updateCard_task.execute(param);
    }*/
