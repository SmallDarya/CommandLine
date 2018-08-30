package com.smolienko.commandline.сommands;

import com.smolienko.commandline.Context;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;

/**
 *
 * @author Pugovka
 */
public interface Command {

    void execute() throws BaseCommandLineException;

    void parseParameters(String parameters) throws BaseCommandLineException;
    
    

    void setExecutionContext(Context context);

}
