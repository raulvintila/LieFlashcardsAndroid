package com.raulvintila.app.lieflashcards.Database.dao;

import java.util.List;
import com.raulvintila.app.lieflashcards.Database.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table DBCARD.
 */
public class DBCard {

    private Long id;
    private String remoteId;
    /** Not-null value. */
    private String question;
    /** Not-null value. */
    private String answer;
    /** Not-null value. */
    private String difficulty;
    private java.util.Date last_study;
    private Double current_level;
    private Double volatility;
    private Integer times_studied;
    /** Not-null value. */
    private java.util.Date date_creted;
    private long deckId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient DBCardDao myDao;

    private List<DBCardTag> tags;

    public DBCard() {
    }

    public DBCard(Long id) {
        this.id = id;
    }

    public DBCard(Long id, String remoteId, String question, String answer,
                  String difficulty, java.util.Date last_study, Double current_level,
                  Double volatility, Integer times_studied, java.util.Date date_creted, long deckId) {
        this.id = id;
        this.remoteId = remoteId;
        this.question = question;
        this.answer = answer;
        this.difficulty = difficulty;
        this.last_study = last_study;
        this.current_level = current_level;
        this.volatility = volatility;
        this.times_studied = times_studied;
        this.date_creted = date_creted;
        this.deckId = deckId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDBCardDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    /** Not-null value. */
    public String getQuestion() {
        return question;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setQuestion(String question) {
        this.question = question;
    }

    /** Not-null value. */
    public String getAnswer() {
        return answer;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /** Not-null value. */
    public String getDifficulty() {
        return difficulty;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public java.util.Date getLast_study() {
        return last_study;
    }

    public void setLast_study(java.util.Date last_study) {
        this.last_study = last_study;
    }

    public Double getCurrent_level() {
        return current_level;
    }

    public void setCurrent_level(Double current_level) {
        this.current_level = current_level;
    }

    public Double getVolatility() {
        return volatility;
    }

    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }

    public Integer getTimes_studied() {
        return times_studied;
    }

    public void setTimes_studied(Integer times_studied) {
        this.times_studied = times_studied;
    }

    /** Not-null value. */
    public java.util.Date getDate_creted() {
        return date_creted;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDate_creted(java.util.Date date_creted) {
        this.date_creted = date_creted;
    }

    public long getDeckId() {
        return deckId;
    }

    public void setDeckId(long deckId) {
        this.deckId = deckId;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<DBCardTag> getTags() {
        if (tags == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DBCardTagDao targetDao = daoSession.getDBCardTagDao();
            List<DBCardTag> tagsNew = targetDao._queryDBCard_Tags(id);
            synchronized (this) {
                if(tags == null) {
                    tags = tagsNew;
                }
            }
        }
        return tags;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetTags() {
        tags = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

}
