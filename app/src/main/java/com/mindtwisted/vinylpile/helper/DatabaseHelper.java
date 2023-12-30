package com.mindtwisted.vinylpile.helper;

import static android.content.Context.MODE_NO_LOCALIZED_COLLATORS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mindtwisted.vinylpile.R;
import com.mindtwisted.vinylpile.model.Collection;
import com.mindtwisted.vinylpile.model.CollectionRecord;
import com.mindtwisted.vinylpile.model.Record;
import com.mindtwisted.vinylpile.model.Track;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Database helper class used to manage the creation and upgrading of the database
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // Application data database name
    public static final String USER_DATABASE_NAME = "application.db";

    /** Note that updating these versions also requires an app version update */

    // Application data database version (Version 1 as of 1.0.0)
    public static final int USER_DATABASE_VERSION = 1;

    private static final Class[] USER_DATABASE_ENTITY_CLASSES = {
            Record.class,
            Track.class,
            Collection.class,
            CollectionRecord.class
    };

    // Location of application databases
    private final String mDatabasePath;

    // Private constructor to manage instances
    private DatabaseHelper(Context context) {
        super(context, USER_DATABASE_NAME, null, USER_DATABASE_VERSION, R.raw.ormlite_config);
        mDatabasePath = getDatabasePath(context);
    }

    /**
     * Generate ormlite config file for quick caching used in DAO objects.
     */
    public static void main(String[] args) {
        try {
            OrmLiteConfigUtil.writeConfigFile(new File("./app/src/main/res/raw/ormlite_config.txt"), USER_DATABASE_ENTITY_CLASSES);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**************************************************************
     *  Shared DAOs
     *************************************************************/

    // Map of shared data access objects keyed by class name
    private static final Map<Class, Dao<? extends BaseDaoEnabled, Integer>> sClassDaoMap = new HashMap<>();

    /**
     * Return reusable kanji data access object
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseDaoEnabled> Dao<T, Integer> getSharedDao(Context context, Class<T> daoClass) {
        Dao<T, Integer> dao = (Dao<T, Integer>) sClassDaoMap.get(daoClass);
        if (dao == null) {
            try {
                dao = getInstance(context).getDao(daoClass) ;
                sClassDaoMap.put(daoClass, dao);
            } catch (SQLException e) {
                Logger.e(e);
            }
        }
        return dao;
    }

    /**************************************************************
     *  Public methods
     *************************************************************/

    // Since helper reference
    private static DatabaseHelper sHelper = null;

    /**
     * Static method for obtaining helper reference. Must call release when finished.
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sHelper == null) {
            if (context != null) {
                sHelper = new DatabaseHelper(context);
            }
        }
        return sHelper;
    }

    /**
     * Returns database file path to the application database
     */
    public static String getApplicationDatabaseFilePath(Context context) {
        return context.getDatabasePath(USER_DATABASE_NAME).getAbsolutePath();
    }

    /**
     * Static methods to release the most recent helper instance.
     */
    public static synchronized void initHelper(Context context) {
        if (sHelper == null) {
            sHelper = new DatabaseHelper(context);
            sHelper.getWritableDatabase(); // Force upgrades to trigger
        }
    }

    public static void deleteContentDatabase(Context context, String database) {
        String databasePath = getDatabasePath(context);
        String databaseFilePath = databasePath + database;

        File databaseFile = new File(databaseFilePath);
        databaseFile.delete();
    }

    /**
     * Static methods to release the most recent helper instance.
     */
    public static synchronized void releaseHelper() {
        if (sHelper != null) {
            sClassDaoMap.clear();
            sHelper.close();
            sHelper = null;
        }
    }

    /**
     * Drop all application database tables and create default data
     */
    public static void resetApplicationDatabase(Context context) {
        ConnectionSource connectionSource = getInstance(context).getConnectionSource();
        createApplicationTables(connectionSource, true);
    }

    /**
     * Performs vacuum on database
     */
    public static void performDatabaseVacuum(Context context) {
        SQLiteDatabase database = getInstance(context).getWritableDatabase();
        database.execSQL("VACUUM");
    }

    /**
     * Returns application database version
     */
    public static int getUserDatabaseVersion(Context context) {
        SQLiteDatabase database = getInstance(context).getReadableDatabase();
        return database.getVersion();
    }

    /**
     * Returns database version
     */
    public static int getDatabaseVersion(Context context, String databaseName) {
        if (!databaseExists(context, databaseName)) {
            return 0;
        }
        SQLiteDatabase database = getSQLiteDatabase(context, databaseName);
        if (database != null) {
            int version = database.getVersion();
            database.close();
            return version;
        }
        return 0;
    }

    /**
     * Return true if the content database exists
     */
    private static boolean databaseExists(Context context, String databaseName) {
        String databasePath = getDatabasePath(context);
        File databaseFile = new File(databasePath + databaseName);
        return databaseFile.exists();
    }

    public static String getDatabasePath(Context context) {
        return context.getApplicationInfo().dataDir + "/databases/";
    }

    //*************************************************************
    //  Life cycle methods
    //*************************************************************

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        createApplicationTables(connectionSource, false);
    }

    /**
     * On upgrade of application database
     * Some examples:
     *  Add index example
     *      db.execSQL("DROP INDEX `COLUMN_NAME_idx`");
     *      db.execSQL("CREATE INDEX `COLUMN_NAME_idx` ON `TABLE_NAME` ( `COLUMN_NAME` )");
     *  Drop table example
     *      db.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
     *      TableUtils.createTable(connectionSource, Object.class);
     *  Alter table example
     *      db.execSQL("ALTER TABLE TABLE_NAME ADD COLUMN_NAME INTEGER DEFAULT 0");
     *      db.execSQL("ALTER TABLE TABLE_NAME ADD COLUMN_NAME BOOLEAN DEFAULT false");
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        // Do nothing
        try {
            Logger.d("onUpgrade; oldVersion=" + oldVersion + ", newVersion=" + newVersion);
            db.beginTransaction();
            // Update incrementally
            int upgradeTo = oldVersion + 1;
            while (upgradeTo <= newVersion) {
                switch (upgradeTo) {
                    case 1: // 1.0.0
                        break;
                    default:
                        break;
                }
                upgradeTo++;
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Logger.e(e);
            throw new RuntimeException("Unable to migrate from version " + oldVersion + " to " + newVersion, e);
        } finally {
            db.endTransaction();
        }
    }

    /**************************************************************
     *  Private methods
     *************************************************************/

    private static SQLiteDatabase getSQLiteDatabase(Context context, String databaseName) {
        SQLiteDatabase database = null;
        try {
            database = context.openOrCreateDatabase(getDatabasePath(context) + databaseName, MODE_NO_LOCALIZED_COLLATORS, null);
        } catch (Exception exception1) {
            Logger.e(exception1);
            try {
                database = SQLiteDatabase.openDatabase(
                        getDatabasePath(context) + databaseName, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            } catch (Exception exception2) {
                Logger.e(exception2);
            }
        }
        return database;
    }

    // Drops and creates all application specific tables
    private static void createApplicationTables(ConnectionSource connectionSource, boolean dropTables) {
        try {
            if (dropTables) {
                for (Class entityClass : USER_DATABASE_ENTITY_CLASSES) {
                    TableUtils.dropTable(connectionSource, entityClass, true);
                }
            }
            for (Class entityClass : USER_DATABASE_ENTITY_CLASSES) {
                TableUtils.createTableIfNotExists(connectionSource, entityClass);
            }
        } catch (SQLException e) {
            Logger.e(e);
            throw new RuntimeException(e);
        }
    }
}
