package com.raulvintila.app.lieflashcards.Utils;


public class CardTypesPlaceholderUtils {
    private String type;

    public CardTypesPlaceholderUtils(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setFrontType(String front_type) {
        String[] types = type.split("_");
        types[0] = front_type;

        type = types[0] + "_" + types[1];
    }

    public void setBackType(String back_type) {
        String[] types = type.split("_");
        types[1] = back_type;

        type = types[0] + "_" + types[1];
    }
}
