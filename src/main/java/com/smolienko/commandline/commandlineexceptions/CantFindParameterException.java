package com.smolienko.commandline.commandlineexceptions;

/**
 *
 * @author Darya Smolienko
 */
public class CantFindParameterException extends BaseCommandLineException{
    private final static String MESSAGE="Can't find parameter."; 

    public CantFindParameterException() {
        super(MESSAGE);
    }
    
}
