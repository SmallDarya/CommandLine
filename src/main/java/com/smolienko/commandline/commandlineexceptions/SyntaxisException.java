package com.smolienko.commandline.commandlineexceptions;

/**
 *
 * @author Darya Smolienko
 */
public class SyntaxisException extends BaseCommandLineException{
    private final static String MESSAGE="Syntaxis exception."; 

    public SyntaxisException() {
        super(MESSAGE);
    }
    
}
