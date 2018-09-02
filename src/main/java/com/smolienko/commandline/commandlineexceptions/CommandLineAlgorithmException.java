package com.smolienko.commandline.commandlineexceptions;

/**
 *
 * @author Darya Smolienko
 */
public class CommandLineAlgorithmException extends BaseCommandLineException{
    private final static String MESSAGE="Command line algorithm exception"; 

    public CommandLineAlgorithmException() {
        super(MESSAGE);
    }
    
}
