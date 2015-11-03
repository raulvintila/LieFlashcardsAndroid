package com.raulvintila.app.lieflashcards.Database.dao;

import java.util.List;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table DBDECK.
 */
public class DBDeck {

    private Long id;
    private Long remoteId;
    /** Not-null value. */
    private String author;
    private int icon;
    private long dateCreated;
    private long lastUpdateDate;
    /** Not-null value. */
    private String version;
    private boolean editable;
    /** Not-null value. */
    private String minAppVersion;
    private String tags;
    private boolean published;
    private int originalId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient DBDeckDao myDao;

    private List<DBVote> votes;
    private List<DBCard> cards;

    public DBDeck() {
    }

    public DBDeck(Long id) {
        this.id = id;
    }

    public DBDeck(Long id, Long remoteId, String author, int icon, long dateCreated, long lastUpdateDate, String version, boolean editable, String minAppVersion, String tags, boolean published, int originalId) {
        this.id = id;
        this.remoteId = remoteId;
        this.author = author;
        this.icon = icon;
        this.dateCreated = dateCreated;
        this.lastUpdateDate = lastUpdateDate;
        this.version = version;
        this.editable = editable;
        this.minAppVersion = minAppVersion;
        this.tags = tags;
        this.published = published;
        this.originalId = originalId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDBDeckDao() : null;
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

    /** Not-null value. */
    public String getAuthor() {
        return author;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /** Not-null value. */
    public String getVersion() {
        return version;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setVersion(String version) {
        this.version = version;
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /** Not-null value. */
    public String getMinAppVersion() {
        return minAppVersion;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMinAppVersion(String minAppVersion) {
        this.minAppVersion = minAppVersion;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<DBVote> getVotes() {
        if (votes == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DBVoteDao targetDao = daoSession.getDBVoteDao();
            List<DBVote> votesNew = targetDao._queryDBDeck_Votes(id);
            synchronized (this) {
                if(votes == null) {
                    votes = votesNew;
                }
            }
        }
        return votes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetVotes() {
        votes = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<DBCard> getCards() {
        if (cards == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DBCardDao targetDao = daoSession.getDBCardDao();
            List<DBCard> cardsNew = targetDao._queryDBDeck_Cards(id);
            synchronized (this) {
                if(cards == null) {
                    cards = cardsNew;
                }
            }
        }
        return cards;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetCards() {
        cards = null;
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
