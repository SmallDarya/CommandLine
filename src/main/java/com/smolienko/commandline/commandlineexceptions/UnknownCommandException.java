package com.smolienko.commandline.commandlineexceptions;

/**
 *
 * @author Darya Smolienko
 */
public class UnknownCommandException extends BaseCommandLineException{
    private final static String MESSAGE="Unknown command"; 

    public UnknownCommandException() {
        super(MESSAGE);
    }
    
}
