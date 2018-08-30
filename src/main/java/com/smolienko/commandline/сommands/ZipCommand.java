package com.smolienko.commandline.сommands;

import com.smolienko.commandline.annotations.CommandDescription;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;

import com.smolienko.commandline.commandlineexceptions.DirNotExistException;
import com.smolienko.commandline.commandlineexceptions.SyntaxisException;
import com.smolienko.commandline.commandlineexceptions.ZipExecutionException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pugovka
 */
@CommandDescription(
	parameters = "[filePath] [targetDirectory]",
        name="zip",
        description = "Упаковать выбранный файл или директорию в архив."
)
@Component("zip")
public class ZipCommand extends BaseCommand {

    private static final String FILE_PATH = "fileName";

    private static final String TARGET_PATH = "targetfile";

    
    @Override
    public void execute() throws BaseCommandLineException {
        try {
            String archiveName = getArchiveName() + ".zip";
            ZipFile zipFile = new ZipFile(archiveName);
            Path archivePath = Paths.get(archiveName);
            if(!checkArchivePath(archivePath))
                return;

            ZipParameters zipParameters = new ZipParameters();
            askAboutPassword(zipParameters);

            zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            File targetFile = new File(parameters.get(FILE_PATH));
            if (targetFile.isFile()) {
                zipFile.addFile(targetFile, zipParameters);
            } else if (targetFile.isDirectory()) {
                zipFile.addFolder(targetFile, zipParameters);
            }
        } catch (ZipException | IOException ex) {
            throw new ZipExecutionException();
        }
    }

    @Override
    public void parseParameters(String parameters) throws BaseCommandLineException {
        List<String> parametersList=getParametersList(parameters);
        if(parametersList.isEmpty()||parametersList.size()>2)
            throw new SyntaxisException();
        
        Path fileName = Paths.get(parametersList.get(0));
        if (Files.notExists(fileName)) {
            throw new DirNotExistException();
        }
        this.parameters.put(FILE_PATH, fileName.toString());

        if (parametersList.size()== 2) {
           this.parameters.put(TARGET_PATH, parametersList.get(1));
        }
    }

    private boolean checkArchivePath(Path archivePath) throws IOException {
        if (Files.exists(archivePath)) {
            context.printOnConsole(resources.getMessage("file.already.exist", null, Locale.getDefault()));
            if (getYesNoAnswer()) {
                Files.delete(archivePath);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private String getArchiveName() {
        Path path = Paths.get(parameters.get(FILE_PATH));
        if (parameters.containsKey(TARGET_PATH)) {
            Path archivePath = Paths.get(parameters.get(TARGET_PATH), path.getFileName().toString().replaceAll("\\..*$", ""));
            return archivePath.toString();
        } else {
            return path.toString().replaceAll("\\..*$", "");
        }
    }

    private void askAboutPassword(ZipParameters parameters) {
        Scanner inStream = this.context.getInStream();
        context.printOnConsole(resources.getMessage("put.password.question", null, Locale.getDefault()));
        if (getYesNoAnswer()) {
            String enterPasswordMessage = resources.getMessage("enter.password", null, Locale.getDefault());
            String password;
            do {
                context.printOnConsole(enterPasswordMessage);
                password = inStream.nextLine();
                if (password.length() < 1) {
                    enterPasswordMessage = resources.getMessage("empty.password", null, Locale.getDefault()) + enterPasswordMessage;
                }
            } while (password.length() < 1);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword(password);
        }
    }
}
