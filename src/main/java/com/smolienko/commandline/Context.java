package com.smolienko.commandline;

import java.nio.file.Path;
import java.util.Scanner;

/**
 *
 * Context describe a space where commands executes, like {@link CommandLine}.
 * You can implement this interface if you want to make your own programm or
 * class, which can execute commands.
 *
 * @author Darya Smolienko
 */
public interface Context {

    /**
     * This function change work directory- directory, where executs comands.
     *
     * @param  dirPath - path of the new work directory.
     */
    void changeWorkDir(Path dirPath);

    /**
     * Returns work directory.
     *
     * @return work directory.
     */
    Path getWorkingDir();

    /**
     * This function start listening of inputs tream and wait for new command.
     *
     */
    void startWork();

    /**
     * 
     * Returns input stream.
     *
     */
    Scanner getInStream();

    /**
     * 
     * The function use output stream of context and print simple message.
     * 
     * @param message - simple message.
     *
     */
    void printOnConsole(String message);

    /**
     *
     * The function use output stream of context and print formatted messages
     *
     * @param message - message for print.
     * @param args - arguments referenced by the format specifiers in the format
     * string.
     *
     */
    void formattingPrintOnConsole(String message, Object... args);
}
