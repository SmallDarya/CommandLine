package com.smolienko.commandline.сommands;

import com.smolienko.commandline.annotations.CommandDescription;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.CommandLineAlgorithmException;
import com.smolienko.commandline.commandlineexceptions.SyntaxisException;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

/**
 *
 * The command write information about all commands or about one command if
 * parameterts contains name of concrete command.
 *
 * @author Darya Smolienko
 */
@CommandDescription(
        parameters = "[comandName]",
        name = "help",
        description = "help.description"
)
@Component("help")
@Scope("prototype")
public class HelpCommand extends BaseCommand {

    @Autowired
    ApplicationContext appContext;

    private static final String COMMAND_NAME = "commandName";

    public static final String COMMANDS_PACKAGE = "com.smolienko.commandline.сommands";

    @Override
    public void execute() throws BaseCommandLineException {
        try {
            if (parameters.isEmpty()) {
                showAllCommands();
            } else {
                showConcreteCommand(parameters.get(COMMAND_NAME));
            }
        } catch (ClassNotFoundException ex) {
            throw new CommandLineAlgorithmException();
        }
    }

    @Override
    public void parseParameters(String parameters) throws BaseCommandLineException {
        List<String> parametersList = getParametersList(parameters);
        if (parametersList.isEmpty()) {
            return;
        }
        if (parametersList.size() > 1) {
            throw new SyntaxisException();
        }
        this.parameters.put(COMMAND_NAME, parametersList.get(0));
    }

    /**
     * Function show information about all commands.
     *
     */
    private void showAllCommands() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(CommandDescription.class));
        for (BeanDefinition bd : scanner.findCandidateComponents(COMMANDS_PACKAGE)) {
            Class<?> cl = Class.forName(bd.getBeanClassName());
            CommandDescription annotationDescription = cl.getAnnotation(CommandDescription.class);
            if (annotationDescription != null) {
                context.formattingPrintOnConsole("%-10s %s", annotationDescription.name(), resources.getMessage(annotationDescription.description(), null, Locale.getDefault()));
                context.printOnConsole("");
            }
        }
    }

    /**
     * Function show information about concrete commands.
     *
     * @param commandName - name of command
     *
     */
    private void showConcreteCommand(String commandName) throws ClassNotFoundException {
        Object command = appContext.getBean(commandName);
        Class<?> cl = command.getClass();
        CommandDescription annotationDescription = cl.getAnnotation(CommandDescription.class);
        if (annotationDescription != null) {
            context.printOnConsole(annotationDescription.name() + " " + annotationDescription.parameters());
            context.printOnConsole("");
            context.printOnConsole(resources.getMessage(annotationDescription.description(), null, Locale.getDefault()));
        }
    }
}
