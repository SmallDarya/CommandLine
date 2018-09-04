package com.smolienko.commandline.сommands;

import com.smolienko.commandline.annotations.CommandDescription;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import org.springframework.stereotype.Component;

/**
 * Special command for exit from command line.
 * 
 * @author Darya Smolienko
 */
@CommandDescription(
	parameters = "",
        name="exit",
        description = "exit.description"
)
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
