package com.raulvintila.app.lieflashcards.Utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.SyncClasses.CreateCardWrapper;
import com.raulvintila.app.lieflashcards.SyncClasses.RequestReturnedMessage;
import com.raulvintila.app.lieflashcards.SyncClasses.UserData;
import com.raulvintila.app.lieflashcards.SyncClasses.WrappedCardRRM;
import com.raulvintila.app.lieflashcards.SyncClasses.WrappedDeckRRM;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class NetworkUtils {

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public UserData getUserData(String user_id) {
        try {
            URL url = new URL("http://lie-server.herokuapp.com/users/" + user_id );

            URLConnection connection = url.openConnection();
            connection.addRequestProperty("Referer", "anki.ichi2.com");

            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            String string = builder.toString();
            Gson gson = new Gson();

            UserData userData = gson.fromJson(string, UserData.class);
            userData.setFailed(false);

            return userData;

        } catch (Exception e) {
            e.printStackTrace();
            UserData userData = new UserData();
            userData.setFailed(true);
            return userData;
        }
    }

    public RequestReturnedMessage deleteRemoteDeck(String user_id, String remote_deck_id) {
        try {
            URL url = new URL("http://lie-server.herokuapp.com/users/deck");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");

            httpCon.setUseCaches(false);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("user_id", user_id);
            jsonParam.put("deck_id",remote_deck_id);

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
            message.setFailed(false);

            return message;
        } catch (Exception e) {
            e.printStackTrace();
            RequestReturnedMessage rrm = new RequestReturnedMessage();
            rrm.setFailed(true);
            return rrm;
        }
    }

    public RequestReturnedMessage deleteRemoteCard(String card_id){
        try {
            URL url = new URL("http://lie-server.herokuapp.com/cards/delete");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");

            httpCon.setUseCaches(false);
            //httpCon.connect();

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("card_id", card_id);

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
            message.setFailed(false);

            return message;

        } catch (Exception e) {
            e.printStackTrace();
            RequestReturnedMessage rrm = new RequestReturnedMessage();
            rrm.setFailed(true);
            return rrm;
        }
    }

    public WrappedDeckRRM createRemoteDeck(DBDeck deck) {
        try {
            URL url = new URL("http://lie-server.herokuapp.com/decks/create");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");

            httpCon.setUseCaches(false);

            ArrayList<String> tagList = new ArrayList<String>();
                /*for(int i = 0; i < deck.getTags().size(); i++) {
                    tagList.add(deck.getTags().get(i).getText());
                }*/

            tagList.add("shaorma");


            JSONObject jsonParam = new JSONObject();
            jsonParam.put("deck_name", deck.getName());
            jsonParam.put("tags", new JSONArray(tagList));

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

            WrappedDeckRRM wrapper = new WrappedDeckRRM(deck,message);;

            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            WrappedDeckRRM wDeckRRM = new WrappedDeckRRM();

            return wDeckRRM;
        }
    }

    public WrappedDeckRRM updateRemoteDeck(DBDeck deck) {
        try {
            URL url = new URL("http://lie-server.herokuapp.com/decks/update");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("PUT");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");

            httpCon.setUseCaches(false);

            ArrayList<String> tagList = new ArrayList<String>();
                /*for(int i = 0; i < deck.getTags().size(); i++) {
                    tagList.add(deck.getTags().get(i).getText());
                }*/

            tagList.add("shaorma");


            JSONObject jsonParam = new JSONObject();
            jsonParam.put("deck_id", deck.getRemoteId());
            jsonParam.put("deck_name", deck.getName());
            jsonParam.put("tags", new JSONArray(tagList));

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

            WrappedDeckRRM wrapper = new WrappedDeckRRM(deck,message);

            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            WrappedDeckRRM wDeckRRM = new WrappedDeckRRM();

            return wDeckRRM;
        }
    }

    public RequestReturnedMessage linkDeckToUser(String user_id, DBDeck deck) {
        try {
            URL url = new URL("http://lie-server.herokuapp.com/users/deck");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("PUT");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setUseCaches(false);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("user_id", user_id);
            jsonParam.put("deck_id", deck.getRemoteId());

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
            message.setFailed(false);

            return message;
        } catch (Exception e) {
            e.printStackTrace();
            RequestReturnedMessage RRM = new RequestReturnedMessage();
            RRM.setFailed(true);

            return RRM;
        }
    }

    public WrappedCardRRM createRemoteCard(CreateCardWrapper wrapper) {
        try {
            DBCard card = wrapper.getCard();
            String remote_deck_id = wrapper.getRemote_deck_id();

            URL url = new URL("http://lie-server.herokuapp.com/cards/create");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setUseCaches(false);

            String[] types = card.getDifficulty().split("_");

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("card_question", card.getQuestion());
            jsonParam.put("card_answer", card.getAnswer());
            jsonParam.put("card_question_type", types[0]);
            jsonParam.put("card_answer_type", types[1]);
            jsonParam.put("deck_id", remote_deck_id);

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

            WrappedCardRRM wCardRRM = new WrappedCardRRM(card, message);
;

            return wCardRRM;
        } catch (Exception e) {
            e.printStackTrace();
            WrappedCardRRM wCardRRM = new WrappedCardRRM();

            return wCardRRM;
        }
    }

    public WrappedCardRRM updateRemoteCard(DBCard card) {
        try {

            URL url = new URL("http://lie-server.herokuapp.com/cards/update");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("PUT");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setUseCaches(false);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("card_question", card.getQuestion());
            jsonParam.put("card_answer", card.getAnswer());
            jsonParam.put("card_id", card.getRemoteId());

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

            WrappedCardRRM wCardRRM = new WrappedCardRRM(card, message);

            return wCardRRM;
        } catch (Exception e) {
            e.printStackTrace();
            WrappedCardRRM wCardRRM = new WrappedCardRRM();

            return wCardRRM;
        }
    }
}
