package com.smolienko.commandline.commandlineexceptions;

/**
 *
 * @author Darya Smolienko
 */
public abstract class BaseCommandLineException extends Exception {

    private String message;

    public BaseCommandLineException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
