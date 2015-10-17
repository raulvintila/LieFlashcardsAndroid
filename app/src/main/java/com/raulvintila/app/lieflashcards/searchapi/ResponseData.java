package com.raulvintila.app.lieflashcards.SearchAPI;

import java.util.List;


public class ResponseData {
    private List<Result> results;
    private Cursor cursor;

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }
}
