package com.raulvintila.app.lieflashcards;

import java.util.ArrayList;
import java.util.List;

public class PlayMode {
    private String text;
    private String type;
    private boolean expanded;
    private int children_number;
    private List<PlayMode> children;

    public PlayMode(String text) {
        this.text = text;
    }

    public PlayMode(String text, String type, int children_number) {
        this.text = text;
        this.type = type;
        this.expanded = false;
        this.children_number = children_number;

        if (type.equals("main")) {
            children = new ArrayList<PlayMode>();
            switch (text) {
                case "Study":
                    children.add(0,new PlayMode("Normal","submode",0));
                    children.add(0,new PlayMode("Custom","submode",0));
                    break;
                case "Cram":
                    children.add(0,new PlayMode("Normal","submode",0));
                    children.add(0,new PlayMode("Custom","submode",0));
                    break;
                case "Test":
                    children.add(0,new PlayMode("Normal","submode",0));
                    children.add(0,new PlayMode("Custom","submode",0));
                    break;
            }
        }
    }

    public List<PlayMode> getChildren() { return this.children; }

    public int getChildren_number() { return  this.children_number; }

    public void setExpanded(boolean expanded) { this.expanded = expanded; }

    public boolean getExpanded() { return this.expanded; }

    public String getType() {return  this.type; }

    public String getText() {
        return this.text;
    }
}