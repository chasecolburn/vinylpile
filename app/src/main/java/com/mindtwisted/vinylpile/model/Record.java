package com.mindtwisted.vinylpile.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName=Record.TABLE_NAME)
public class Record extends BaseDaoEnabled<Record, Integer> {

    //****************************************
    // Database field names
    //****************************************

    public static final String TABLE_NAME = "records";

    public static final String FIELD_NAME_ID = "id";

    public static final String FIELD_NAME_LABEL = "label";

    public static final String FIELD_NAME_GENRE = "genre";

    public static final String FIELD_NAME_STYLE = "style";

    public static final String FIELD_NAME_YEAR = "year";

    public static final String FIELD_NAME_COUNTRY = "country";

    public static final String FIELD_NAME_NAME = "name";

    public static final String FIELD_NAME_ARTWORK = "artwork";

    public static final String FIELD_NAME_ARTIST = "artist";

    public static final String FIELD_NAME_ALBUM = "album";

    public static final String FIELD_NAME_OWNER = "owner";

    public static final String FIELD_NAME_CREATED_AT = "created_at";

    public static final String FIELD_NAME_VIEWED_AT = "viewed_at";

    //****************************************
    // Database members
    //****************************************

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    public int id;

    @DatabaseField(columnName = FIELD_NAME_LABEL)
    public String label;

    @DatabaseField(columnName = FIELD_NAME_GENRE)
    public String genre;

    @DatabaseField(columnName = FIELD_NAME_STYLE)
    public String style;

    @DatabaseField(columnName = FIELD_NAME_YEAR)
    public int year;

    @DatabaseField(columnName = FIELD_NAME_COUNTRY)
    public String country;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    public String name;

    @DatabaseField(columnName = FIELD_NAME_ARTWORK)
    public String artwork;

    @DatabaseField(columnName = FIELD_NAME_ARTIST)
    public String artist;

    @DatabaseField(columnName = FIELD_NAME_ALBUM)
    public String album;

    @DatabaseField(columnName = FIELD_NAME_OWNER)
    public String owner;

    @DatabaseField(columnName = FIELD_NAME_CREATED_AT)
    public long createdAt;


    @DatabaseField(columnName = FIELD_NAME_VIEWED_AT)
    public long viewedAt;

    //****************************************
    // Public methods
    //****************************************

    public Record() {
    }
}
