package com.smolienko.commandline.commandlineexceptions;

/**
 *
 * @author Pugovka
 */
public class ZipExecutionException extends BaseCommandLineException{
    private final static String MESSAGE="Zip exception"; 

    public ZipExecutionException() {
        super(MESSAGE);
    }
    
}
