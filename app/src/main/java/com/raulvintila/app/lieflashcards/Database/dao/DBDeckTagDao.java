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

import com.raulvintila.app.lieflashcards.Database.dao.DBDeckTag;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table DBDECK_TAG.
*/
public class DBDeckTagDao extends AbstractDao<DBDeckTag, Long> {

    public static final String TABLENAME = "DBDECK_TAG";

    /**
     * Properties of entity DBDeckTag.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Text = new Property(1, String.class, "text", false, "TEXT");
        public final static Property DeckId = new Property(2, long.class, "deckId", false, "DECK_ID");
    };

    private Query<DBDeckTag> dBDeck_TagsQuery;

    public DBDeckTagDao(DaoConfig config) {
        super(config);
    }
    
    public DBDeckTagDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'DBDECK_TAG' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'TEXT' TEXT NOT NULL ," + // 1: text
                "'DECK_ID' INTEGER NOT NULL );"); // 2: deckId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'DBDECK_TAG'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DBDeckTag entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getText());
        stmt.bindLong(3, entity.getDeckId());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public DBDeckTag readEntity(Cursor cursor, int offset) {
        DBDeckTag entity = new DBDeckTag( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // text
            cursor.getLong(offset + 2) // deckId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DBDeckTag entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setText(cursor.getString(offset + 1));
        entity.setDeckId(cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(DBDeckTag entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(DBDeckTag entity) {
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
    
    /** Internal query to resolve the "tags" to-many relationship of DBDeck. */
    public List<DBDeckTag> _queryDBDeck_Tags(long deckId) {
        synchronized (this) {
            if (dBDeck_TagsQuery == null) {
                QueryBuilder<DBDeckTag> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.DeckId.eq(null));
                dBDeck_TagsQuery = queryBuilder.build();
            }
        }
        Query<DBDeckTag> query = dBDeck_TagsQuery.forCurrentThread();
        query.setParameter(0, deckId);
        return query.list();
    }

}