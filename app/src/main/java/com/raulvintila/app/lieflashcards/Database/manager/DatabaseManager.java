package com.raulvintila.app.lieflashcards.Database.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBCardDao;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeckDao;
import com.raulvintila.app.lieflashcards.Database.dao.DBUser;
import com.raulvintila.app.lieflashcards.Database.dao.DBUserDao;
import com.raulvintila.app.lieflashcards.Database.dao.DaoMaster;
import com.raulvintila.app.lieflashcards.Database.dao.DaoSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.greenrobot.dao.async.AsyncOperation;
import de.greenrobot.dao.async.AsyncOperationListener;
import de.greenrobot.dao.async.AsyncSession;
import de.greenrobot.dao.query.QueryBuilder;


/**
 * @author Octa
 */
public class DatabaseManager implements IDatabaseManager, AsyncOperationListener {

    /**
     * Class tag. Used for debug.
     */
    private static final String TAG = DatabaseManager.class.getCanonicalName();
    /**
     * Instance of DatabaseManager
     */
    private static DatabaseManager instance;
    /**
     * The Android Activity reference for access to DatabaseManager.
     */
    private Context context;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private AsyncSession asyncSession;
    private List<AsyncOperation> completedOperations;

    /**
     * Constructs a new DatabaseManager with the specified arguments.
     *
     * @param context The Android {@link Context}.
     */
    public DatabaseManager(final Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(this.context, "sample-database", null);
        completedOperations = new CopyOnWriteArrayList<AsyncOperation>();
    }

    /**
     * @param context The Android {@link Context}.
     * @return this.instance
     */
    public static DatabaseManager getInstance(Context context) {

        if (instance == null) {
            instance = new DatabaseManager(context);
        }

        return instance;
    }

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        completedOperations.add(operation);
    }

    private void assertWaitForCompletion1Sec() {
        asyncSession.waitForCompletion(1000);
        asyncSession.isCompleted();
    }

    /**
     * Query for readable DB
     */
    public void openReadableDb() throws SQLiteException {
        database = mHelper.getReadableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    /**
     * Query for writable DB
     */
    public void openWritableDb() throws SQLiteException {
        database = mHelper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
        asyncSession.setListener(this);
    }

    @Override
    public void closeDbConnections() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (instance != null) {
            instance = null;
        }
    }

    @Override
    public synchronized void dropDatabase() {
        try {
            openWritableDb();
            DaoMaster.dropAllTables(database, true); // drops all tables
            mHelper.onCreate(database);              // creates the tables
            asyncSession.deleteAll(DBUser.class);    // clear all elements from a table
            asyncSession.deleteAll(DBDeck.class);
            asyncSession.deleteAll(DBCard.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized DBUser insertUser(DBUser user) {
        try {
            if (user != null) {
                openWritableDb();
                DBUserDao userDao = daoSession.getDBUserDao();
                userDao.insert(user);
                Log.d(TAG, "Inserted user: " + user.getEmail() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public synchronized DBDeck insertOrUpdateDeck(DBDeck deck) {
        try {
            if (deck != null) {
                openWritableDb();
                daoSession.insertOrReplace(deck);
                //Log.d(TAG, "Inserted card: question: " + card.getQuestion() + ", asnwer :" + card.getAnswer() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deck;
    }


    @Override
    public synchronized DBCard insertOrUpdateCard(DBCard card) {
        try {
            if (card != null) {
                openWritableDb();
                daoSession.insertOrReplace(card);
                //Log.d(TAG, "Inserted card: question: " + card.getQuestion() + ", asnwer :" + card.getAnswer() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return card;
    }

    @Override
    public synchronized DBCard insertCard(DBCard card) {
        try {
            if (card != null) {
                openWritableDb();
                DBCardDao cardDao = daoSession.getDBCardDao();
                cardDao.insert(card);
                Log.d(TAG, "Inserted card: question: " + card.getQuestion() + ", asnwer :" + card.getAnswer() + " to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return card;
    }

    @Override
    public synchronized DBDeck insertDeck(DBDeck deck) {
        try {
            if ( deck != null) {
                openWritableDb();
                DBDeckDao deckDao = daoSession.getDBDeckDao();
                deckDao.insert(deck);
                Log.d(TAG, "Inserted deck to the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deck;
    }

    @Override
    public synchronized List<DBUser> listUsers() {
        List<DBUser> users = null;
        try {
            openReadableDb();
            DBUserDao userDao = daoSession.getDBUserDao();
            users = userDao.loadAll();

            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public synchronized void updateUser(DBUser user) {
        try {
            if (user != null) {
                openWritableDb();
                daoSession.update(user);
                Log.d(TAG, "Updated user: " + user.getEmail() + " from the schema.");
                daoSession.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void deleteUserByEmail(String email) {
        try {
            openWritableDb();
            DBUserDao userDao = daoSession.getDBUserDao();
            QueryBuilder<DBUser> queryBuilder = userDao.queryBuilder().where(DBUserDao.Properties.Email.eq(email));
            List<DBUser> userToDelete = queryBuilder.list();
            for (DBUser user : userToDelete) {
                userDao.delete(user);
            }
            daoSession.clear();
            Log.d(TAG, userToDelete.size() + " entry. " + "Deleted user: " + email + " from the schema.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized List<DBCard> getCardsByDeckId(Long deckId) {
        try {
            openReadableDb();
            /*DBDeckDao deckDao = daoSession.getDBDeckDao();
            deckDao.queryBuilder().where(DBDeckDao.Properties.Id.eq(deckId)).unique();*/
            //user = userDao.queryBuilder().where(DBUserDao.Properties.Id.eq(userId)).unique();

            List<DBCard> cardList;

            DBCardDao cardDao = daoSession.getDBCardDao();
            cardList = cardDao.queryBuilder().where(DBCardDao.Properties.DeckId.eq(deckId)).list();
            return cardList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<DBCard>();
        }
    }

    @Override
    public synchronized boolean deleteUserById(Long userId) {
        try {
            openWritableDb();
            DBUserDao userDao = daoSession.getDBUserDao();
            userDao.deleteByKey(userId);
            daoSession.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public synchronized List<DBDeck> getAllDecks() {
        openReadableDb();
        DBDeckDao deckDao = daoSession.getDBDeckDao();
        List<DBDeck> list = deckDao.queryBuilder().list();
        return list;
    }

    @Override
    public synchronized List<DBCard> getAllCards() {
        openReadableDb();
        DBCardDao cardDao = daoSession.getDBCardDao();
        List<DBCard> list = cardDao.queryBuilder()
                .list();
        return list;
    }

    @Override
    public synchronized boolean deleteCardById(Long cardId) {
        DBCard card = null;
        try {
            openReadableDb();
            DBCardDao cardDao = daoSession.getDBCardDao();
            cardDao.deleteByKey(cardId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public synchronized boolean deleteDeckById(Long deckId) {
        DBDeck deck = null;
        try {
            openReadableDb();
            DBDeckDao deckDao = daoSession.getDBDeckDao();
            deckDao.deleteByKey(deckId);
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public synchronized DBDeck getDeckById(Long deckId) {
        DBDeck deck = null;
        try {
            openReadableDb();
            DBDeckDao deckDao = daoSession.getDBDeckDao();
            deck = deckDao.queryBuilder().where(DBDeckDao.Properties.Id.eq(deckId)).unique();
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deck;
    }

    @Override
    public synchronized DBCard getCardById(Long card_id) {
        DBCard card = null;
        try {
            openReadableDb();
            DBCardDao cardDao = daoSession.getDBCardDao();
            card = cardDao.queryBuilder().where(DBCardDao.Properties.Id.eq(card_id)).unique();
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return card;
    }

    @Override
    public synchronized DBUser getUserById(Long userId) {
        DBUser user = null;
        try {
            openReadableDb();
            DBUserDao userDao = daoSession.getDBUserDao();
            user = userDao.queryBuilder().where(DBUserDao.Properties.Id.eq(userId)).unique();
            daoSession.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public synchronized void deleteDecks() {
        try {
            openWritableDb();
            DBDeckDao deckDao = daoSession.getDBDeckDao();
            deckDao.deleteAll();
            daoSession.clear();
            Log.d(TAG, "Delete all decks from the schema.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void deleteUsers() {
        try {
            openWritableDb();
            DBUserDao userDao = daoSession.getDBUserDao();
            userDao.deleteAll();
            daoSession.clear();
            Log.d(TAG, "Delete all users from the schema.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
