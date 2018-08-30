package com.smolienko.commandline.сommands;

import com.smolienko.commandline.annotations.CommandDescription;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author Darya Smolienko
 */
@CommandDescription(
	parameters = "Без параметров.",
        name="countfiles",
        description = "Выводит количество файлов и папок в текущей директории."
)
@Component("countfiles")
public class CountFilesCommand extends BaseCommand{
   
    @Override
    public void execute() throws BaseCommandLineException {
        try {
            int count = 0;
            Path workingDir = context.getWorkingDir();
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(workingDir);
            for (Path path : directoryStream) {
                count++;
            }
            context.printOnConsole(String.valueOf(count));
        } catch (IOException ex) {
            Logger.getLogger(CountFilesCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void parseParameters(String parameters)  throws BaseCommandLineException{
        
    }
}
