package com.raulvintila.app.lieflashcards.Database.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.raulvintila.app.lieflashcards.Database.dao.DBCard;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table DBCARD.
*/
public class DBCardDao extends AbstractDao<DBCard, Long> {

    public static final String TABLENAME = "DBCARD";

    /**
     * Properties of entity DBCard.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property RemoteId = new Property(1, String.class, "remoteId", false, "REMOTE_ID");
        public final static Property Question = new Property(2, String.class, "question", false, "QUESTION");
        public final static Property Answer = new Property(3, String.class, "answer", false, "ANSWER");
        public final static Property Difficulty = new Property(4, String.class, "difficulty", false, "DIFFICULTY");
        public final static Property Last_study = new Property(5, java.util.Date.class, "last_study", false, "LAST_STUDY");
        public final static Property Current_level = new Property(6, Double.class, "current_level", false, "CURRENT_LEVEL");
        public final static Property Volatility = new Property(7, Double.class, "volatility", false, "VOLATILITY");
        public final static Property Times_studied = new Property(8, Integer.class, "times_studied", false, "TIMES_STUDIED");
        public final static Property Date_creted = new Property(9, java.util.Date.class, "date_creted", false, "DATE_CRETED");
        public final static Property DeckId = new Property(10, long.class, "deckId", false, "DECK_ID");
    };

    private DaoSession daoSession;

    private Query<DBCard> dBDeck_CardsQuery;

    public DBCardDao(DaoConfig config) {
        super(config);
    }

    public DBCardDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DBCARD' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'REMOTE_ID' TEXT UNIQUE ," + // 1: remoteId
                "'QUESTION' TEXT NOT NULL ," + // 2: question
                "'ANSWER' TEXT NOT NULL ," + // 3: answer
                "'DIFFICULTY' TEXT NOT NULL ," + // 4: difficulty
                "'LAST_STUDY' INTEGER," + // 5: last_study
                "'CURRENT_LEVEL' REAL," + // 6: current_level
                "'VOLATILITY' REAL," + // 7: volatility
                "'TIMES_STUDIED' INTEGER," + // 8: times_studied
                "'DATE_CRETED' INTEGER NOT NULL ," + // 9: date_creted
                "'DECK_ID' INTEGER NOT NULL );"); // 10: deckId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DBCARD'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DBCard entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String remoteId = entity.getRemoteId();
        if (remoteId != null) {
            stmt.bindString(2, remoteId);
        }
        stmt.bindString(3, entity.getQuestion());
        stmt.bindString(4, entity.getAnswer());
        stmt.bindString(5, entity.getDifficulty());

        java.util.Date last_study = entity.getLast_study();
        if (last_study != null) {
            stmt.bindLong(6, last_study.getTime());
        }

        Double current_level = entity.getCurrent_level();
        if (current_level != null) {
            stmt.bindDouble(7, current_level);
        }

        Double volatility = entity.getVolatility();
        if (volatility != null) {
            stmt.bindDouble(8, volatility);
        }

        Integer times_studied = entity.getTimes_studied();
        if (times_studied != null) {
            stmt.bindLong(9, times_studied);
        }
        stmt.bindLong(10, entity.getDate_creted().getTime());
        stmt.bindLong(11, entity.getDeckId());
    }

    @Override
    protected void attachEntity(DBCard entity) {
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
    public DBCard readEntity(Cursor cursor, int offset) {
        DBCard entity = new DBCard( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // remoteId
                cursor.getString(offset + 2), // question
                cursor.getString(offset + 3), // answer
                cursor.getString(offset + 4), // difficulty
                cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)), // last_study
                cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6), // current_level
                cursor.isNull(offset + 7) ? null : cursor.getDouble(offset + 7), // volatility
                cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // times_studied
                new java.util.Date(cursor.getLong(offset + 9)), // date_creted
                cursor.getLong(offset + 10) // deckId
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DBCard entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRemoteId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setQuestion(cursor.getString(offset + 2));
        entity.setAnswer(cursor.getString(offset + 3));
        entity.setDifficulty(cursor.getString(offset + 4));
        entity.setLast_study(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
        entity.setCurrent_level(cursor.isNull(offset + 6) ? null : cursor.getDouble(offset + 6));
        entity.setVolatility(cursor.isNull(offset + 7) ? null : cursor.getDouble(offset + 7));
        entity.setTimes_studied(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setDate_creted(new java.util.Date(cursor.getLong(offset + 9)));
        entity.setDeckId(cursor.getLong(offset + 10));
    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(DBCard entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(DBCard entity) {
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

    /** Internal query to resolve the "cards" to-many relationship of DBDeck. */
    public List<DBCard> _queryDBDeck_Cards(long deckId) {
        synchronized (this) {
            if (dBDeck_CardsQuery == null) {
                QueryBuilder<DBCard> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.DeckId.eq(null));
                dBDeck_CardsQuery = queryBuilder.build();
            }
        }
        Query<DBCard> query = dBDeck_CardsQuery.forCurrentThread();
        query.setParameter(0, deckId);
        return query.list();
    }

}
