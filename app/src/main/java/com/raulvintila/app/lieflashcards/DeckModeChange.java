package com.raulvintila.app.lieflashcards;


public class DeckModeChange {
    private String main_mode;
    private String sub_mode;

    public DeckModeChange(String main_mode, String sub_mode) {
        this.main_mode = main_mode;
        this.sub_mode = sub_mode;
    }

    public String getMain_mode() { return this.main_mode; }

    public String getSub_mode() { return this.sub_mode; }
}
