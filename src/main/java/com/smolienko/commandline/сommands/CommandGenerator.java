package com.smolienko.commandline.сommands;

import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.SyntaxisException;
import com.smolienko.commandline.commandlineexceptions.UnknownCommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 *
 * @author Darya Smolienko
 */
@Component
public class CommandGenerator  {
    
    @Autowired
    ApplicationContext applicationContext;
    

    public CommandGenerator() {
    }
            
    String getCommandName(String commandLine) throws SyntaxisException{
     if(commandLine==null)
            throw new SyntaxisException();
        commandLine= commandLine.trim();
        return commandLine.split(" ")[0];
    }
    
    public Command getCommandFromLine(String commandLine) throws BaseCommandLineException {
        String commandName = getCommandName(commandLine);
         Command command;
        if(applicationContext.containsBean(commandName)){
            Object commandObject=applicationContext.getBean(commandName);
            if(commandObject instanceof Command)
                command=(Command)commandObject;
            else
                throw new UnknownCommandException();
        }
        else
            throw  new UnknownCommandException();
        String parametersString= commandLine.replaceFirst(commandName, "");
        command.parseParameters(parametersString);
        return command;
    }
    
}
