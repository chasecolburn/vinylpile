package com.mindtwisted.vinylpile.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName= CollectionRecord.TABLE_NAME)
public class CollectionRecord extends BaseDaoEnabled<CollectionRecord, Integer> {

    //****************************************
    // Database field names
    //****************************************

    public static final String TABLE_NAME = "collection_records";

    public static final String FIELD_NAME_COLLECTION_ID = "collection_id";

    public static final String FIELD_NAME_RECORD_ID = "record_id";

    public static final String FIELD_NAME_SEQUENCE = "sequence";

    public static final String FIELD_NAME_CREATED_AT = "created_at";

    //****************************************
    // Database members
    //****************************************

    @DatabaseField(columnName = FIELD_NAME_COLLECTION_ID, index = true)
    public int collectionId;

    @DatabaseField(columnName = FIELD_NAME_RECORD_ID, index = true)
    public int recordId;

    @DatabaseField(columnName = FIELD_NAME_SEQUENCE, index = true)
    public long sequence;

    @DatabaseField(columnName = FIELD_NAME_CREATED_AT, index = true)
    public long createdAt;

    //****************************************
    // Public methods
    //****************************************

    public CollectionRecord() {
    }
}
