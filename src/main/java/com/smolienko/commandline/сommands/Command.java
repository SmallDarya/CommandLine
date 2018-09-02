package com.smolienko.commandline.сommands;

import com.smolienko.commandline.Context;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;

/**
 *
 * This class contains main functions for command. For creating command you 
 * need just override three methods. Class throws {@link BaseCommandLineException}.
 * Every command need some space where it will executes, a few parameters if it 
 * necessery or not and to know how to execute.
 * 
 * @author Darya Smolienko
 */
public interface Command {

     /**
     * 
     * The method describe to command what to do.
     *
     */
    void execute() throws BaseCommandLineException;

     /**
     * 
     * In this method you can parse parameters from string from command line.
     * 
     * @param  parameters  -string with parameters.
     *
     */
    void parseParameters(String parameters) throws BaseCommandLineException;

    /**
     * 
     * Set context to know where your command make changes. COmmand can work with
     * directories or change parameters of command line. You can do it, only if you 
     * know executing context.
     * 
     * @param context -executing context
     *
     */
    void setExecutionContext(Context context);

}
