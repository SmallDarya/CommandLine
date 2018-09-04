package com.smolienko.commandline.сommands;

import com.smolienko.commandline.annotations.CommandDescription;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.DirNotExistException;
import com.smolienko.commandline.commandlineexceptions.SyntaxisException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;

/**
 * Cd command change the work directory of context to another. For using this
 * command you have to write directory path in parameters. 
 * 
 * @author Darya Smolienko
 */

@CommandDescription(
	parameters = "[filepath]",
        name="cd",
        description = "change.dir.description"
)
@Component("cd")
@Scope("prototype")
public class CdCommand extends BaseCommand {

    private static final String DIR_PATH = "newCurrentDir";

    @Override
    public void execute() throws BaseCommandLineException {;
        String path = parameters.get(DIR_PATH);
        this.context.changeWorkDir(Paths.get(path));
    }

    @Override
    public void parseParameters(String parametersStr) throws BaseCommandLineException {
        List<String> parametersList=getParametersList(parametersStr);
        if(parametersList.isEmpty()||parametersList.size()>1)
            throw new SyntaxisException();
        
        String path =parametersList.get(0);
       if(path.isEmpty())
            throw new SyntaxisException();
        Path dir = Paths.get( path);
        if (Files.notExists(dir)) {
            throw new DirNotExistException();
        }
        if (!Files.isDirectory(dir)) {
            throw new DirNotExistException();
        }
        parameters.put(DIR_PATH, dir.toString());
    }
}
