package com.smolienko.commandline.сommands;

import com.smolienko.commandline.annotations.CommandDescription;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.CommandLineAlgorithmException;
import com.smolienko.commandline.commandlineexceptions.SyntaxisException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Darya Smolienko
 */
@CommandDescription(
        parameters = "[/h]",
        name = "countfiles",
        description = "files.count.description"
)
@Component("countfiles")
@Scope("prototype")
public class CountFilesCommand extends BaseCommand {
    
 private static final String COUNT_HIDDEN = "countHidden";
    @Override
    public void execute() throws BaseCommandLineException {
        try {
            int count = 0;
            Path workingDir = context.getWorkingDir();
            boolean countHidden=false;
            if(parameters.containsKey(COUNT_HIDDEN))
                countHidden=Boolean.parseBoolean(parameters.get(COUNT_HIDDEN));
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(workingDir);
            for (Path path : directoryStream) {
                if(!countHidden){
                    if(Files.isHidden(path))
                        continue;
                }
                count++;
            }
            context.printOnConsole(resources.getMessage("files.total", null, Locale.getDefault())+String.valueOf(count));
        } catch (IOException ex) {
            throw new CommandLineAlgorithmException();
        }
    }

    @Override
    public void parseParameters(String parametersStr) throws BaseCommandLineException {
        List<String> parametersList = getParametersList(parametersStr);
        if (parametersList.size() > 1) {
            throw new SyntaxisException();
        }
        if (parametersList.size() == 1 && parametersList.get(0).equals("/h")) {
            parameters.put(COUNT_HIDDEN, "true");
        }

    }
}
