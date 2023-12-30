package com.mindtwisted.vinylpile.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName= Collection.TABLE_NAME)
public class Collection extends BaseDaoEnabled<Collection, Integer> {

    //****************************************
    // Database field names
    //****************************************

    public static final String TABLE_NAME = "collections";

    public static final String FIELD_NAME_ID = "id";

    public static final String FIELD_NAME_NAME = "name";

    public static final String FIELD_NAME_NOTES = "notes";

    public static final String FIELD_NAME_CREATED_AT = "created_at";

    //****************************************
    // Database members
    //****************************************

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    public int id;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    public String name;

    @DatabaseField(columnName = FIELD_NAME_NOTES)
    public String notes;

    @DatabaseField(columnName = FIELD_NAME_CREATED_AT)
    public long createdAt;

    //****************************************
    // Public methods
    //****************************************

    public Collection() {
    }
}
