package com.smolienko.commandline.сommands;

import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.SyntaxisException;
import com.smolienko.commandline.commandlineexceptions.UnknownCommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The class generate command from string. It appriciate command name and get
 * bean from application context.
 *
 * @author Darya Smolienko
 */
@Component
public class CommandGenerator {

    @Autowired
    ApplicationContext applicationContext;

    public CommandGenerator() {
    }

    /**
     * Parse string input and get command name.
     *
     * @param commandLine - string view of command
     * @return command name
     *
     */
    String getCommandName(String commandLine) throws SyntaxisException {
        if (commandLine == null) {
            throw new SyntaxisException();
        }
        commandLine = commandLine.trim();
        return commandLine.split(" ")[0];
    }

    /**
     * Parse string input and instance of concrete command
     *
     * @param commandLine - string view of command
     * @return command
     *
     */
    public Command getCommandFromLine(String commandLine) throws BaseCommandLineException {
        String commandName = getCommandName(commandLine);
        Command command;
        if (applicationContext.containsBean(commandName)) {
            Object commandObject = applicationContext.getBean(commandName);
            if (commandObject instanceof Command) {
                command = (Command) commandObject;
            } else {
                throw new UnknownCommandException();
            }
        } else {
            throw new UnknownCommandException();
        }
        String parametersString = commandLine.replaceFirst(commandName, "");
        command.parseParameters(parametersString);
        return command;
    }
}
