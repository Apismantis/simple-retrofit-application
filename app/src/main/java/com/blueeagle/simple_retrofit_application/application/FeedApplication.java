package com.blueeagle.simple_retrofit_application.application;

import android.app.Application;

import com.blueeagle.simple_retrofit_application.BuildConfig;
import com.squareup.leakcanary.LeakCanary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

public class FeedApplication extends Application {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this))
            return;

        // Install LeakCanary
        LeakCanary.install(this);

        // Config log level using LogBack
        configLogLevel();
    }

    public static Boolean isInDebugMode() {
        return BuildConfig.DEBUG;
    }

    private void configLogLevel() {
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
                .getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

        Level logLevel = Level.INFO;
        if (isInDebugMode()) {
            logLevel = Level.TRACE;
        }

        logger.setLevel(logLevel);
        LOG.info("Set log level to " + logLevel);
    }
}
