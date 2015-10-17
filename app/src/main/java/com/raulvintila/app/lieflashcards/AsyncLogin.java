package com.raulvintila.app.lieflashcards;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.raulvintila.app.lieflashcards.SyncClasses.Login;
import com.raulvintila.app.lieflashcards.SyncClasses.RequestReturnedMessage;
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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;

public class AsyncLogin extends AsyncTask<Void, Void, String> {

    private String mQuery;
    private String user_email;
    private String user_password;

    public AsyncLogin(String user_email, String user_password) {
        this.user_email = user_email;
        this.user_password = user_password;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL("http://lie-server.herokuapp.com/session/login");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type", "application/json");
            httpCon.setRequestProperty("Accept", "application/json");


            Login login = new Login();
            login.setUser_email(user_email);
            login.setUser_password(user_password);

            OutputStream printout = new DataOutputStream(httpCon.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(printout, "UTF-8"));
            writer.write(login.toString());
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

            if(message.getError() == (Number) 0) {
                return message.getPayload().getUser_session();
            } else {
                return "Error";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Exception";
        }
    }


    @Override
    protected void onPostExecute(String result) {

    }
}
