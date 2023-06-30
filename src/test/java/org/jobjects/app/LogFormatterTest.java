package org.jobjects.app;

import org.junit.jupiter.api.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogFormatterTest {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Test
    public void testLogRecord() {
        LogFormatter.initializeLogging();
        LOGGER.entering(getClass().getName(), "testLogRecord");
        LOGGER.finest("le mega fin.");
        LOGGER.finer("le plus fin.");
        LOGGER.fine("le fin.");
        LOGGER.config("mode configuration");
        LOGGER.info("dernière news !");
        LOGGER.warning("attention");
        LOGGER.severe("la boulette");
        IllegalArgumentException iae = new IllegalArgumentException("Exemple de sortie de pile d'exception...");
        LOGGER.log(Level.SEVERE, iae.getMessage(), iae);
        LOGGER.exiting(getClass().getName(), "testLogRecord");
    }
}