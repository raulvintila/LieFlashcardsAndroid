package com.raulvintila.app.lieflashcards.Database.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.raulvintila.app.lieflashcards.Database.dao.DBUser;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeckTag;
import com.raulvintila.app.lieflashcards.Database.dao.DBCardTag;

import com.raulvintila.app.lieflashcards.Database.dao.DBUserDao;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeckDao;
import com.raulvintila.app.lieflashcards.Database.dao.DBCardDao;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeckTagDao;
import com.raulvintila.app.lieflashcards.Database.dao.DBCardTagDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dBUserDaoConfig;
    private final DaoConfig dBDeckDaoConfig;
    private final DaoConfig dBCardDaoConfig;
    private final DaoConfig dBDeckTagDaoConfig;
    private final DaoConfig dBCardTagDaoConfig;

    private final DBUserDao dBUserDao;
    private final DBDeckDao dBDeckDao;
    private final DBCardDao dBCardDao;
    private final DBDeckTagDao dBDeckTagDao;
    private final DBCardTagDao dBCardTagDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dBUserDaoConfig = daoConfigMap.get(DBUserDao.class).clone();
        dBUserDaoConfig.initIdentityScope(type);

        dBDeckDaoConfig = daoConfigMap.get(DBDeckDao.class).clone();
        dBDeckDaoConfig.initIdentityScope(type);

        dBCardDaoConfig = daoConfigMap.get(DBCardDao.class).clone();
        dBCardDaoConfig.initIdentityScope(type);

        dBDeckTagDaoConfig = daoConfigMap.get(DBDeckTagDao.class).clone();
        dBDeckTagDaoConfig.initIdentityScope(type);

        dBCardTagDaoConfig = daoConfigMap.get(DBCardTagDao.class).clone();
        dBCardTagDaoConfig.initIdentityScope(type);

        dBUserDao = new DBUserDao(dBUserDaoConfig, this);
        dBDeckDao = new DBDeckDao(dBDeckDaoConfig, this);
        dBCardDao = new DBCardDao(dBCardDaoConfig, this);
        dBDeckTagDao = new DBDeckTagDao(dBDeckTagDaoConfig, this);
        dBCardTagDao = new DBCardTagDao(dBCardTagDaoConfig, this);

        registerDao(DBUser.class, dBUserDao);
        registerDao(DBDeck.class, dBDeckDao);
        registerDao(DBCard.class, dBCardDao);
        registerDao(DBDeckTag.class, dBDeckTagDao);
        registerDao(DBCardTag.class, dBCardTagDao);
    }
    
    public void clear() {
        dBUserDaoConfig.getIdentityScope().clear();
        dBDeckDaoConfig.getIdentityScope().clear();
        dBCardDaoConfig.getIdentityScope().clear();
        dBDeckTagDaoConfig.getIdentityScope().clear();
        dBCardTagDaoConfig.getIdentityScope().clear();
    }

    public DBUserDao getDBUserDao() {
        return dBUserDao;
    }

    public DBDeckDao getDBDeckDao() {
        return dBDeckDao;
    }

    public DBCardDao getDBCardDao() {
        return dBCardDao;
    }

    public DBDeckTagDao getDBDeckTagDao() {
        return dBDeckTagDao;
    }

    public DBCardTagDao getDBCardTagDao() {
        return dBCardTagDao;
    }

}
