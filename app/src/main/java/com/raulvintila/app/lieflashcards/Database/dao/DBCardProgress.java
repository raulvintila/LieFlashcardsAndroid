package com.raulvintila.app.lieflashcards.Database.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table DBCARD_PROGRESS.
 */
public class DBCardProgress {

    private Long id;
    private Long remoteId;
    private double level;
    private double volatility;
    private Long lastStudyDate;
    /** Not-null value. */
    private String version;
    private long deckId;

    public DBCardProgress() {
    }

    public DBCardProgress(Long id) {
        this.id = id;
    }

    public DBCardProgress(Long id, Long remoteId, double level, double volatility, Long lastStudyDate, String version, long deckId) {
        this.id = id;
        this.remoteId = remoteId;
        this.level = level;
        this.volatility = volatility;
        this.lastStudyDate = lastStudyDate;
        this.version = version;
        this.deckId = deckId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public double getVolatility() {
        return volatility;
    }

    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

    public Long getLastStudyDate() {
        return lastStudyDate;
    }

    public void setLastStudyDate(Long lastStudyDate) {
        this.lastStudyDate = lastStudyDate;
    }

    /** Not-null value. */
    public String getVersion() {
        return version;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setVersion(String version) {
        this.version = version;
    }

    public long getDeckId() {
        return deckId;
    }

    public void setDeckId(long deckId) {
        this.deckId = deckId;
    }

}