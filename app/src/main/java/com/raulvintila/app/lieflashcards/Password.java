package com.raulvintila.app.lieflashcards;


import android.widget.EditText;

public class Password {
    private EditText password;
    private EditText confirm_password;
    
    public void setPassword(EditText password) {
        this.password = password;
    }
    
    public void setConfirm_password(EditText confirm_password) {
        this.confirm_password = confirm_password;
    }
    
    public EditText getPassword() { 
        return this.password;
    }
    
    public EditText getConfirm_password() {
        return this.confirm_password;
    }
}
