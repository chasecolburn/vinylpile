package com.mindtwisted.vinylpile.helper;

import android.util.Log;

@SuppressWarnings("unused")
public class Logger {

    public static final String COMMON_LOGGING_TAG = "vinylpile";

    // Filter on common log tag and the Android runtime to catch crashes.
    // Suppress all others.
    private static final String[] FILTER = new String[] {COMMON_LOGGING_TAG + ":*", "AndroidRuntime:*", "*:S"};

    // Max message length
    private static final int MAX_LOG_MESSAGE_LENGTH = 100000;

    // Line separator for building output
    private static final String LINE_SEPARATOR = "\n";

    // Log tagging format for common tags
    private static final String MESSAGE_FORMAT = "%s : %s";

    /**
     * Error log with exception.
     */
    public static void e(Throwable error) {
        e(error, null);
    }

    /**
     * Error log with exception and message.
     */
    public static void e(Throwable error, String message) {
        Log.e(COMMON_LOGGING_TAG, message, error);
    }

    /**
     * Error log with message.
     */
    public static void e(String errorMessage) {
        Log.e(COMMON_LOGGING_TAG, errorMessage);
    }

    /**
     * Debug log with message.
     */
    public static void d(String message) {
        Log.d(COMMON_LOGGING_TAG, message);
    }
}