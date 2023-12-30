package com.mindtwisted.vinylpile.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName= Track.TABLE_NAME)
public class Track extends BaseDaoEnabled<Track, Integer> {

    //****************************************
    // Database field names
    //****************************************

    public static final String TABLE_NAME = "tracks";

    public static final String FIELD_NAME_ID = "id";

    public static final String FIELD_NAME_SIDE = "side";

    public static final String FIELD_NAME_NAME = "name";

    public static final String FIELD_NAME_RPM = "rpm";

    public static final String FIELD_NAME_TIME = "time";

    public static final String FIELD_NAME_RATING = "rating";

    public static final String FIELD_NAME_NOTES = "notes";

    //****************************************
    // Database members
    //****************************************

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    public int id;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    public String name;

    @DatabaseField(columnName = FIELD_NAME_SIDE)
    public String side;

    @DatabaseField(columnName = FIELD_NAME_RPM, index = true)
    public String rpm;

    @DatabaseField(columnName = FIELD_NAME_TIME)
    public int time;

    @DatabaseField(columnName = FIELD_NAME_RATING)
    public int rating;

    @DatabaseField(columnName = FIELD_NAME_NOTES)
    public String notes;

    //****************************************
    // Public methods
    //****************************************

    public Track() {
    }
}
