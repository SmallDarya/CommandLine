package com.smolienko.commandline.сommands;

import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Darya Smolienko
 */
@Component("exit")
public class ExitCommand extends BaseCommand{
   
    @Override
    public void execute() throws BaseCommandLineException {
        System.exit(0);
    }

    @Override
    public void parseParameters(String parameters)  throws BaseCommandLineException{
        
    }
}
