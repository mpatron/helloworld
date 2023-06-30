package org.jobjects.app;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class App {
  final static Logger logger = Logger.getGlobal();
  final static String VERSION_FILE_PROPERTIES = "app-config.properties";
  static String version = "0.0.0";
  static String monOption = StringUtils.EMPTY;

  private static void loadVersion() {
    try (InputStream input = App.class.getClassLoader()
                                      .getResourceAsStream(VERSION_FILE_PROPERTIES)) {
      Properties prop = new Properties();
      if (input == null) {
        System.err.println("Jar error, unable to find " + VERSION_FILE_PROPERTIES);
        System.exit(1);
      }
      // load a properties file from class path, inside static method
      prop.load(input);
      // get the property value and print it out
      version = prop.getProperty("version");
    } catch (IOException ex) {
      ex.printStackTrace();
      System.exit(1);
    }
  }

  private static Options configParameters() {

    final Option optionOption = Option.builder("o")
                                      .longOpt("mon-option") //
                                      .desc("[a | b ] Choix de l'option.")
                                      .hasArg(true)
                                      .argName("mon-option")
                                      //.required(true)
                                      .build();

    final Option helpOption = Option.builder("v")
                                    .longOpt("version")
                                    .desc("Affiche la version.")
                                    .build();

    final Option versionOption = Option.builder("h")
                                       .longOpt("help")
                                       .desc("Affiche le message d'aide.")
                                       .build();

    final Options options = new Options();

    options.addOption(optionOption);
    options.addOption(helpOption);
    options.addOption(versionOption);

    return options;
  }

  public static void main(String[] args) throws Exception {
    LogFormatter.initializeLogging();
    System.exit(run(args));
  }

  private static int run(String[] args) throws Exception {
    int returnValue = 0;
    long t_program_start = System.currentTimeMillis();
    long t_program_end = 0;

    /*
     * Gestion des paramétres
     */
    HelpFormatter formatter = new HelpFormatter();
    String cmdLineSyntax = "$JAVA_HOME/bin/java " + App.class.getName();
    loadVersion();
    String header = "Helloworld version " + version;
    String footer = "Copyright © 2006-2023 JObjects. All Rights Reserved";

    CommandLineParser parser = new PosixParser();
    Options options = configParameters();
    try {

      CommandLine line = parser.parse(options, args);

      if (line.hasOption("o")) {
        monOption = line.getOptionValue("o");
      }

      if (line.hasOption("v")) {
        String message = "Version : " + version;
        logger.info(message);
        return 0;
      }

      if (line.hasOption("h")) {
        formatter.printHelp(cmdLineSyntax, header, options, footer);
        return 0;
      }

    } catch (ParseException pe) {
      formatter.printHelp(160, cmdLineSyntax, header, options, footer);
      if ((pe instanceof MissingOptionException) || (pe instanceof MissingArgumentException)) {
        System.err.println("Parametres manquant : " + pe.getMessage());
      }
      return 1;
    }

    String returnString = action();

    t_program_end = System.currentTimeMillis();
    logger.info("Duration : " + DurationFormatUtils.formatDuration(t_program_end - t_program_start, "HH:mm:ss.SSS")
        + ".");

    return returnValue;
  }

  private static String action() {
    String returnValue = "Hello World!";
    logger.info(returnValue);
    return returnValue;
  }
}
