package com.raulvintila.app.lieflashcards.Database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

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
        public final static Property RemoteId = new Property(1, Long.class, "remoteId", false, "REMOTE_ID");
        public final static Property Author = new Property(2, String.class, "author", false, "AUTHOR");
        public final static Property Icon = new Property(3, int.class, "icon", false, "ICON");
        public final static Property DateCreated = new Property(4, long.class, "dateCreated", false, "DATE_CREATED");
        public final static Property LastUpdateDate = new Property(5, long.class, "lastUpdateDate", false, "LAST_UPDATE_DATE");
        public final static Property Version = new Property(6, String.class, "version", false, "VERSION");
        public final static Property Editable = new Property(7, boolean.class, "editable", false, "EDITABLE");
        public final static Property MinAppVersion = new Property(8, String.class, "minAppVersion", false, "MIN_APP_VERSION");
        public final static Property Tags = new Property(9, String.class, "tags", false, "TAGS");
        public final static Property Published = new Property(10, boolean.class, "published", false, "PUBLISHED");
        public final static Property OriginalId = new Property(11, int.class, "originalId", false, "ORIGINAL_ID");
    };

    private DaoSession daoSession;


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
                "'REMOTE_ID' INTEGER UNIQUE ," + // 1: remoteId
                "'AUTHOR' TEXT NOT NULL ," + // 2: author
                "'ICON' INTEGER NOT NULL ," + // 3: icon
                "'DATE_CREATED' INTEGER NOT NULL ," + // 4: dateCreated
                "'LAST_UPDATE_DATE' INTEGER NOT NULL ," + // 5: lastUpdateDate
                "'VERSION' TEXT NOT NULL ," + // 6: version
                "'EDITABLE' INTEGER NOT NULL ," + // 7: editable
                "'MIN_APP_VERSION' TEXT NOT NULL ," + // 8: minAppVersion
                "'TAGS' TEXT," + // 9: tags
                "'PUBLISHED' INTEGER NOT NULL ," + // 10: published
                "'ORIGINAL_ID' INTEGER NOT NULL );"); // 11: originalId
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
 
        Long remoteId = entity.getRemoteId();
        if (remoteId != null) {
            stmt.bindLong(2, remoteId);
        }
        stmt.bindString(3, entity.getAuthor());
        stmt.bindLong(4, entity.getIcon());
        stmt.bindLong(5, entity.getDateCreated());
        stmt.bindLong(6, entity.getLastUpdateDate());
        stmt.bindString(7, entity.getVersion());
        stmt.bindLong(8, entity.getEditable() ? 1l: 0l);
        stmt.bindString(9, entity.getMinAppVersion());
 
        String tags = entity.getTags();
        if (tags != null) {
            stmt.bindString(10, tags);
        }
        stmt.bindLong(11, entity.getPublished() ? 1l: 0l);
        stmt.bindLong(12, entity.getOriginalId());
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
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // remoteId
            cursor.getString(offset + 2), // author
            cursor.getInt(offset + 3), // icon
            cursor.getLong(offset + 4), // dateCreated
            cursor.getLong(offset + 5), // lastUpdateDate
            cursor.getString(offset + 6), // version
            cursor.getShort(offset + 7) != 0, // editable
            cursor.getString(offset + 8), // minAppVersion
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // tags
            cursor.getShort(offset + 10) != 0, // published
            cursor.getInt(offset + 11) // originalId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DBDeck entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRemoteId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setAuthor(cursor.getString(offset + 2));
        entity.setIcon(cursor.getInt(offset + 3));
        entity.setDateCreated(cursor.getLong(offset + 4));
        entity.setLastUpdateDate(cursor.getLong(offset + 5));
        entity.setVersion(cursor.getString(offset + 6));
        entity.setEditable(cursor.getShort(offset + 7) != 0);
        entity.setMinAppVersion(cursor.getString(offset + 8));
        entity.setTags(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setPublished(cursor.getShort(offset + 10) != 0);
        entity.setOriginalId(cursor.getInt(offset + 11));
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
    
}
