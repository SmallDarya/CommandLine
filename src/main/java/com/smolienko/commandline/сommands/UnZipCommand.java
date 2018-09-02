package com.smolienko.commandline.сommands;

import com.smolienko.commandline.annotations.CommandDescription;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.DirNotExistException;
import com.smolienko.commandline.commandlineexceptions.SyntaxisException;
import com.smolienko.commandline.commandlineexceptions.ZipExecutionException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Darya Smolienko
 */
@CommandDescription(
	parameters = "[zipFile] [targetDirectory]",
        name="unzip",
        description = "unzip.descriptin"
)
@Component("unzip")
@Scope("prototype")
public class UnZipCommand extends BaseCommand {

    private static final String FILE_PATH = "fileName";

    private static final String TARGET_PATH = "targetfile";

    @Override
    public void execute() throws BaseCommandLineException {
         Scanner inStream = this.context.getInStream();
        try {
            String zipPath = parameters.get(FILE_PATH);
            ZipFile zipFile = new ZipFile(zipPath);
            if (!zipFile.isValidZipFile()) {
                context.printOnConsole(resources.getMessage("file.is.not.archive", null, Locale.getDefault()));
                return;
            }
            Path targetPath = getTargetPath();
           
            if (zipFile.isEncrypted()) {
                context.printOnConsole(resources.getMessage("enter.password", null, Locale.getDefault()));
                String password = inStream.nextLine();
                zipFile.setPassword(password);
            }
            zipFile.extractAll(targetPath.toString());
        } catch (ZipException e) {
           throw new ZipExecutionException();
        }
    }

    @Override
    public void parseParameters(String parametersStr) throws BaseCommandLineException {
        List<String> parametersList=getParametersList(parametersStr);
        if(parametersList.isEmpty()||parametersList.size()>2)
            throw new SyntaxisException();
        
        Path fileName = Paths.get(parametersList.get(0));
        if (Files.notExists(fileName)) {
            throw new DirNotExistException();
        }
        parameters.put(FILE_PATH, fileName.toString());

        if (parametersList.size()== 2) {
           parameters.put(TARGET_PATH, parametersList.get(1));
        }
    }

    private Path getTargetPath() {
        if (parameters.containsKey(TARGET_PATH)) {
            return Paths.get(parameters.get(TARGET_PATH));
        } else {
            return Paths.get(parameters.get(FILE_PATH)).toAbsolutePath().getParent();
        }
    }

}
