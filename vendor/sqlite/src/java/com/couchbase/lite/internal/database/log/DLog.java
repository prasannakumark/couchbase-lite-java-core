/**
 *
 * Copyright (c) 2012 Couchbase, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.couchbase.lite.internal.database.log;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public class DLog {
    public static Logger logger = new com.couchbase.lite.internal.database.log.SystemLogger();

    /**
     * A map of tags and their enabled log level
     */
    private static ConcurrentHashMap<String, Integer> enabledTags;

    /**
     * Logging tags
     */
    public static final String TAG_SQLiteConnection = "SQLiteConnection";
    public static final String TAG_SQLiteConnectionPool = "SQLiteConnectionPool";
    public static final String TAG_SQLiteDatabase = "SQLiteDatabase";
    public static final String TAG_SQLiteQueryBuilder = "SQLiteQueryBuilder";
    public static final String TAG_SQLiteQueryCursor = "SQLiteQueryCursor";

    /**
     * Logging levels -- values match up with android.util.Log
     */
    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;

    static {
        enabledTags = new ConcurrentHashMap<String, Integer>();
        enabledTags.put(DLog.TAG_SQLiteConnection, WARN);
        enabledTags.put(DLog.TAG_SQLiteConnectionPool, WARN);
        enabledTags.put(DLog.TAG_SQLiteDatabase, WARN);
        enabledTags.put(DLog.TAG_SQLiteQueryBuilder, WARN);
        enabledTags.put(DLog.TAG_SQLiteQueryCursor, WARN);
    }

    /**
     * Enable logging for a particular tag / loglevel combo
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param logLevel The loglevel to enable.  Anything matching this loglevel
     *                 or having a more urgent loglevel will be emitted.  Eg, Log.VERBOSE.
     */
    public static void enableLogging(String tag, int logLevel) {
        enabledTags.put(tag, logLevel);
    }

    /**
     * Is logging enabled for given tag / loglevel combo?
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param logLevel The loglevel to check whether it's enabled.  Will match this loglevel
     *                 or a more urgent loglevel.  Eg, if Log.ERROR is enabled and Log.VERBOSE
     *                 is passed as a paremeter, it will return true.
     * @return boolean indicating whether logging is enabled.
     */
    /* package */ static boolean isLoggingEnabled(String tag, int logLevel) {

        // this hashmap lookup might be a little expensive, and so it might make
        // sense to convert this over to a CopyOnWriteArrayList
        Integer logLevelForTag = enabledTags.get(tag);
        return logLevel >= (logLevelForTag == null ? INFO : logLevelForTag);
    }

    /**
     * Send a VERBOSE message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (logger != null && isLoggingEnabled(tag, VERBOSE)) {
            logger.v(tag, msg);
        }
    }

    /**
     * Send a VERBOSE message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (logger != null && isLoggingEnabled(tag, VERBOSE)) {
            logger.v(tag, msg, tr);
        }
    }

    /**
     * Send a VERBOSE message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param formatString The string you would like logged plus format specifiers.
     * @param args Variable number of Object args to be used as params to formatString.
     */
    public static void v(String tag, String formatString, Object... args) {
        if (logger != null && isLoggingEnabled(tag, VERBOSE)) {
            try {
                logger.v(tag, String.format(Locale.ENGLISH, formatString, args));
            } catch (Exception e) {
                logger.v(tag, String.format(Locale.ENGLISH, "Unable to format log: %s", formatString), e);
            }
        }

    }

    /**
     * Send a VERBOSE message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param formatString The string you would like logged plus format specifiers.
     * @param tr An exception to log
     * @param args Variable number of Object args to be used as params to formatString.
     */
    public static void v(String tag, String formatString, Throwable tr, Object... args) {
        if (logger != null && isLoggingEnabled(tag, VERBOSE)) {
            try {
                logger.v(tag, String.format(Locale.ENGLISH, formatString, args), tr);
            } catch (Exception e) {
                logger.v(tag, String.format(Locale.ENGLISH, "Unable to format log: %s", formatString), e);
            }
        }
    }

    /**
     * Send a DEBUG message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (logger != null && isLoggingEnabled(tag, DEBUG)) {
            logger.d(tag, msg);
        }
    }

    /**
     * Send a DEBUG message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (logger != null && isLoggingEnabled(tag, DEBUG)) {
            logger.d(tag, msg, tr);
        }
    }

    /**
     * Send a DEBUG message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param formatString The string you would like logged plus format specifiers.
     * @param args Variable number of Object args to be used as params to formatString.
     */
    public static void d(String tag, String formatString, Object... args) {
        if (logger != null && isLoggingEnabled(tag, DEBUG)) {
            try {
                logger.d(tag, String.format(Locale.ENGLISH, formatString, args));
            } catch (Exception e) {
                logger.d(tag, String.format(Locale.ENGLISH, "Unable to format log: %s", formatString), e);
            }
        }
    }

    /**
     * Send a DEBUG message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param formatString The string you would like logged plus format specifiers.
     * @param tr An exception to log
     * @param args Variable number of Object args to be used as params to formatString.
     */
    public static void d(String tag, String formatString, Throwable tr, Object... args) {
        if (logger != null && isLoggingEnabled(tag, DEBUG)) {
            try {
                logger.d(tag, String.format(Locale.ENGLISH, formatString, args, tr));
            } catch (Exception e) {
                logger.d(tag, String.format(Locale.ENGLISH, "Unable to format log: %s", formatString), e);
            }
        }
    }


    /**
     * Send an INFO message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (logger != null && isLoggingEnabled(tag, INFO)) {
            logger.i(tag, msg);
        }
    }

    /**
     * Send a INFO message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (logger != null && isLoggingEnabled(tag, INFO)) {
            logger.i(tag, msg, tr);
        }
    }

    /**
     * Send an INFO message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param formatString The string you would like logged plus format specifiers.
     * @param args Variable number of Object args to be used as params to formatString.
     */
    public static void i(String tag, String formatString, Object... args) {
        if (logger != null && isLoggingEnabled(tag, INFO)) {
            try {
                logger.i(tag, String.format(Locale.ENGLISH, formatString, args));
            } catch (Exception e) {
                logger.i(tag, String.format(Locale.ENGLISH, "Unable to format log: %s", formatString), e);
            }
        }
    }

    /**
     * Send a INFO message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param formatString The string you would like logged plus format specifiers.
     * @param tr An exception to log
     * @param args Variable number of Object args to be used as params to formatString.
     */
    public static void i(String tag, String formatString, Throwable tr, Object... args) {
        if (logger != null && isLoggingEnabled(tag, INFO)) {
            try {
                logger.i(tag, String.format(Locale.ENGLISH, formatString, args, tr));
            } catch (Exception e) {
                logger.i(tag, String.format(Locale.ENGLISH, "Unable to format log: %s", formatString), e);
            }
        }
    }

    /**
     * Send a WARN message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (logger != null && isLoggingEnabled(tag, WARN)) {
            logger.w(tag, msg);
        }
    }

    /**
     * Send a WARN message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    public static void w(String tag, Throwable tr) {
        if (logger != null && isLoggingEnabled(tag, WARN)) {
            logger.w(tag, tr);
        }
    }

    /**
     * Send a WARN message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (logger != null && isLoggingEnabled(tag, WARN)) {
            logger.w(tag, msg, tr);
        }
    }


    /**
     * Send a WARN message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param formatString The string you would like logged plus format specifiers.
     * @param args Variable number of Object args to be used as params to formatString.
     */
    public static void w(String tag, String formatString, Object... args) {
        if (logger != null && isLoggingEnabled(tag, WARN)) {
            try {
                logger.w(tag, String.format(Locale.ENGLISH, formatString, args));
            } catch (Exception e) {
                logger.w(tag, String.format(Locale.ENGLISH, "Unable to format log: %s", formatString), e);
            }
        }
    }


    /**
     * Send a WARN message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param formatString The string you would like logged plus format specifiers.
     * @param tr An exception to log
     * @param args Variable number of Object args to be used as params to formatString.
     */
    public static void w(String tag, String formatString, Throwable tr, Object... args) {
        if (logger != null && isLoggingEnabled(tag, WARN)) {
            try {
                logger.w(tag, String.format(Locale.ENGLISH, formatString, args));
            } catch (Exception e) {
                logger.w(tag, String.format(Locale.ENGLISH, "Unable to format log: %s", formatString), e);
            }
        }
    }


    /**
     * Send an ERROR message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (logger != null && isLoggingEnabled(tag, ERROR)) {
            logger.e(tag, msg);
        }
    }

    /**
     * Send a ERROR message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (logger != null && isLoggingEnabled(tag, ERROR)) {
            logger.e(tag, msg, tr);
        }
    }

    /**
     * Send a ERROR message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param formatString The string you would like logged plus format specifiers.
     * @param tr An exception to log
     * @param args Variable number of Object args to be used as params to formatString.
     */
    public static void e(String tag, String formatString, Throwable tr, Object... args) {
        if (logger != null && isLoggingEnabled(tag, ERROR)) {
            try {
                logger.e(tag, String.format(Locale.ENGLISH, formatString, args), tr);
            } catch (Exception e) {
                logger.e(tag, String.format(Locale.ENGLISH, "Unable to format log: %s", formatString), e);
            }
        }
    }

    /**
     * Send a ERROR message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param formatString The string you would like logged plus format specifiers.
     * @param args Variable number of Object args to be used as params to formatString.
     */
    public static void e(String tag, String formatString, Object... args) {
        if (logger != null && isLoggingEnabled(tag, ERROR)) {
            try {
                logger.e(tag, String.format(Locale.ENGLISH, formatString, args));
            } catch (Exception e) {
                logger.e(tag, String.format(Locale.ENGLISH, "Unable to format log: %s", formatString), e);
            }
        }
    }
}