package com.mindtwisted.vinylpile;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.mindtwisted.vinylpile.helper.DatabaseHelper;
import com.mindtwisted.vinylpile.helper.Logger;

import java.lang.ref.WeakReference;

/**
 * Kanji Study application class
 */
public class MainApplication extends Application {

    private static WeakReference<Context> sApplicationContext;

    public static Context getGlobalContext() {
        return sApplicationContext == null ? null : sApplicationContext.get();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sApplicationContext = new WeakReference<>(getBaseContext());
    }

    /**
     * Called when application has started.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // Disable ormlite logs
//        com.j256.ormlite.logger.Logger.setGlobalLogLevel(Level.OFF);

        // Set the database helper class
        OpenHelperManager.setOpenHelperClass(DatabaseHelper.class);

        // Open database connection pools
        DatabaseHelper.initHelper(this);

        // Enable logging
        System.setProperty("log.tag." + Logger.COMMON_LOGGING_TAG, "VERBOSE");

        // Throw logs whenever we access the local storage or network on the UI thread
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());

        // Throw logs when we leak
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()
                .penaltyLog()
                .build());

        // Initialize preferences
//        Preferences.initPreferences(this);

        // Apply DayNight theme
//        AppCompatDelegate.setDefaultNightMode(Preferences.getNightMode());
    }

    /**
     * Called when application is terminating.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        // Release database connection pools
        DatabaseHelper.releaseHelper();
    }
}