package com.smolienko.commandline.сommands;

import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.UnknownCommandException;
import com.smolienko.commandline.configuration.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 *
 * @author Darya Smolienko
 */
public class CommandGenerator  {
    
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    

    public CommandGenerator() {
    }
            
    String getCommandName(String commandLine){
     if(commandLine==null)
            throw new NullPointerException("Input line can't be null");
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
        MessageSource resources = applicationContext.getBean(MessageSource.class);
        command.setMessageSource(resources);
        String parametersString= commandLine.replaceFirst(commandName, "");
        command.parseParameters(parametersString);
        return command;
    }
    
}
