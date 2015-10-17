package com.raulvintila.app.lieflashcards;

public class LoginBUS {
    private String session;
    private String user_name;

    public LoginBUS(String session, String user_name) {
        this.session = session;
        this.user_name = user_name;
    }
}
