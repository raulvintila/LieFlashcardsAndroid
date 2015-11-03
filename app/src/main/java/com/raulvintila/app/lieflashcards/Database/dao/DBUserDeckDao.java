package com.raulvintila.app.lieflashcards.Database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table DBUSER_DECK.
*/
public class DBUserDeckDao extends AbstractDao<DBUserDeck, Long> {

    public static final String TABLENAME = "DBUSER_DECK";

    /**
     * Properties of entity DBUserDeck.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property RemoteId = new Property(1, Long.class, "remoteId", false, "REMOTE_ID");
        public final static Property Stack = new Property(2, String.class, "stack", false, "STACK");
        public final static Property Status = new Property(3, String.class, "status", false, "STATUS");
        public final static Property Position = new Property(4, int.class, "position", false, "POSITION");
        public final static Property CardsPerDay = new Property(5, int.class, "cardsPerDay", false, "CARDS_PER_DAY");
        public final static Property UserId = new Property(6, long.class, "userId", false, "USER_ID");
        public final static Property DeckId = new Property(7, long.class, "deckId", false, "DECK_ID");
    };

    private DaoSession daoSession;

    private Query<DBUserDeck> dBUser_UserDecksQuery;

    public DBUserDeckDao(DaoConfig config) {
        super(config);
    }
    
    public DBUserDeckDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DBUSER_DECK' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'REMOTE_ID' INTEGER UNIQUE ," + // 1: remoteId
                "'STACK' TEXT," + // 2: stack
                "'STATUS' TEXT NOT NULL ," + // 3: status
                "'POSITION' INTEGER NOT NULL UNIQUE ," + // 4: position
                "'CARDS_PER_DAY' INTEGER NOT NULL ," + // 5: cardsPerDay
                "'USER_ID' INTEGER NOT NULL ," + // 6: userId
                "'DECK_ID' INTEGER NOT NULL );"); // 7: deckId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DBUSER_DECK'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DBUserDeck entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long remoteId = entity.getRemoteId();
        if (remoteId != null) {
            stmt.bindLong(2, remoteId);
        }
 
        String stack = entity.getStack();
        if (stack != null) {
            stmt.bindString(3, stack);
        }
        stmt.bindString(4, entity.getStatus());
        stmt.bindLong(5, entity.getPosition());
        stmt.bindLong(6, entity.getCardsPerDay());
        stmt.bindLong(7, entity.getUserId());
        stmt.bindLong(8, entity.getDeckId());
    }

    @Override
    protected void attachEntity(DBUserDeck entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public DBUserDeck readEntity(Cursor cursor, int offset) {
        DBUserDeck entity = new DBUserDeck( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // remoteId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // stack
            cursor.getString(offset + 3), // status
            cursor.getInt(offset + 4), // position
            cursor.getInt(offset + 5), // cardsPerDay
            cursor.getLong(offset + 6), // userId
            cursor.getLong(offset + 7) // deckId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DBUserDeck entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRemoteId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setStack(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setStatus(cursor.getString(offset + 3));
        entity.setPosition(cursor.getInt(offset + 4));
        entity.setCardsPerDay(cursor.getInt(offset + 5));
        entity.setUserId(cursor.getLong(offset + 6));
        entity.setDeckId(cursor.getLong(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(DBUserDeck entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(DBUserDeck entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "userDecks" to-many relationship of DBUser. */
    public List<DBUserDeck> _queryDBUser_UserDecks(long userId) {
        synchronized (this) {
            if (dBUser_UserDecksQuery == null) {
                QueryBuilder<DBUserDeck> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.UserId.eq(null));
                dBUser_UserDecksQuery = queryBuilder.build();
            }
        }
        Query<DBUserDeck> query = dBUser_UserDecksQuery.forCurrentThread();
        query.setParameter(0, userId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getDBDeckDao().getAllColumns());
            builder.append(" FROM DBUSER_DECK T");
            builder.append(" LEFT JOIN DBDECK T0 ON T.'DECK_ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected DBUserDeck loadCurrentDeep(Cursor cursor, boolean lock) {
        DBUserDeck entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        DBDeck deck = loadCurrentOther(daoSession.getDBDeckDao(), cursor, offset);
         if(deck != null) {
            entity.setDeck(deck);
        }

        return entity;    
    }

    public DBUserDeck loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<DBUserDeck> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<DBUserDeck> list = new ArrayList<DBUserDeck>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<DBUserDeck> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<DBUserDeck> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
