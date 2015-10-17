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

import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table DBDECK.
*/
public class DBDeckDao extends AbstractDao<DBDeck, Long> {

    public static final String TABLENAME = "DBDECK";

    /**
     * Properties of entity DBDeck.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property RemoteId = new Property(1, String.class, "remoteId", false, "REMOTE_ID");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Number_of_cards = new Property(3, long.class, "number_of_cards", false, "NUMBER_OF_CARDS");
        public final static Property Number_of_cards_per_day = new Property(4, long.class, "number_of_cards_per_day", false, "NUMBER_OF_CARDS_PER_DAY");
        public final static Property Total_new_cards = new Property(5, Long.class, "total_new_cards", false, "TOTAL_NEW_CARDS");
        public final static Property Status = new Property(6, String.class, "status", false, "STATUS");
        public final static Property Date_created = new Property(7, java.util.Date.class, "date_created", false, "DATE_CREATED");
        public final static Property Boolean_changed = new Property(8, Boolean.class, "boolean_changed", false, "BOOLEAN_CHANGED");
        public final static Property UserId = new Property(9, long.class, "userId", false, "USER_ID");
    };

    private DaoSession daoSession;

    private Query<DBDeck> dBUser_DecksQuery;

    public DBDeckDao(DaoConfig config) {
        super(config);
    }

    public DBDeckDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DBDECK' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'REMOTE_ID' TEXT UNIQUE ," + // 1: remoteId
                "'NAME' TEXT NOT NULL ," + // 2: name
                "'NUMBER_OF_CARDS' INTEGER NOT NULL ," + // 3: number_of_cards
                "'NUMBER_OF_CARDS_PER_DAY' INTEGER NOT NULL ," + // 4: number_of_cards_per_day
                "'TOTAL_NEW_CARDS' INTEGER," + // 5: total_new_cards
                "'STATUS' TEXT," + // 6: status
                "'DATE_CREATED' INTEGER NOT NULL ," + // 7: date_created
                "'BOOLEAN_CHANGED' INTEGER," + // 8: boolean_changed
                "'USER_ID' INTEGER NOT NULL );"); // 9: userId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DBDECK'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DBDeck entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String remoteId = entity.getRemoteId();
        if (remoteId != null) {
            stmt.bindString(2, remoteId);
        }
        stmt.bindString(3, entity.getName());
        stmt.bindLong(4, entity.getNumber_of_cards());
        stmt.bindLong(5, entity.getNumber_of_cards_per_day());

        Long total_new_cards = entity.getTotal_new_cards();
        if (total_new_cards != null) {
            stmt.bindLong(6, total_new_cards);
        }

        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(7, status);
        }
        stmt.bindLong(8, entity.getDate_created().getTime());

        Boolean boolean_changed = entity.getBoolean_changed();
        if (boolean_changed != null) {
            stmt.bindLong(9, boolean_changed ? 1l: 0l);
        }
        stmt.bindLong(10, entity.getUserId());
    }

    @Override
    protected void attachEntity(DBDeck entity) {
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
    public DBDeck readEntity(Cursor cursor, int offset) {
        DBDeck entity = new DBDeck( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // remoteId
                cursor.getString(offset + 2), // name
                cursor.getLong(offset + 3), // number_of_cards
                cursor.getLong(offset + 4), // number_of_cards_per_day
                cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // total_new_cards
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // status
                new java.util.Date(cursor.getLong(offset + 7)), // date_created
                cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0, // boolean_changed
                cursor.getLong(offset + 9) // userId
        );
        return entity;
    }

    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DBDeck entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRemoteId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.getString(offset + 2));
        entity.setNumber_of_cards(cursor.getLong(offset + 3));
        entity.setNumber_of_cards_per_day(cursor.getLong(offset + 4));
        entity.setTotal_new_cards(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setStatus(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDate_created(new java.util.Date(cursor.getLong(offset + 7)));
        entity.setBoolean_changed(cursor.isNull(offset + 8) ? null : cursor.getShort(offset + 8) != 0);
        entity.setUserId(cursor.getLong(offset + 9));
    }

    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(DBDeck entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    /** @inheritdoc */
    @Override
    public Long getKey(DBDeck entity) {
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

    /** Internal query to resolve the "decks" to-many relationship of DBUser. */
    public List<DBDeck> _queryDBUser_Decks(long userId) {
        synchronized (this) {
            if (dBUser_DecksQuery == null) {
                QueryBuilder<DBDeck> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.UserId.eq(null));
                dBUser_DecksQuery = queryBuilder.build();
            }
        }
        Query<DBDeck> query = dBUser_DecksQuery.forCurrentThread();
        query.setParameter(0, userId);
        return query.list();
    }


}
