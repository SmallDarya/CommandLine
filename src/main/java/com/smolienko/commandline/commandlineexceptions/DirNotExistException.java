package com.smolienko.commandline.commandlineexceptions;

/**
 *
 * @author Pugovka
 */
public class DirNotExistException extends BaseCommandLineException{
    private final static String MESSAGE="Dir not exist."; 

    public DirNotExistException() {
        super(MESSAGE);
    }
    
}
