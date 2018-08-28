/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smolienko.commandline.сommands;

import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.CantFindParameterException;
import com.smolienko.commandline.commandlineexceptions.DirNotExistException;
import com.smolienko.commandline.commandlineexceptions.SyntaxisException;
import com.smolienko.commandline.commandlineexceptions.ZipExecutionException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pugovka
 */
@Component("zip")
public class UnZipCommand extends BaseCommand {

    private static final String FILE_PATH = "fileName";

    private static final String TARGET_PATH = "targetfile";

    @Override
    public void execute() throws BaseCommandLineException {
        Scanner inStream = this.context.getInStream();
        try {
            String archiveName = getArchiveName()+ ".zip";
            ZipFile zipFile = new ZipFile(archiveName);
            Path archivePath= Paths.get(archiveName);
            if (Files.exists(Paths.get(archiveName))) {
                context.printOnConsole(resources.getMessage("file.already.exist", null, Locale.getDefault()));
                if (getYesNoAnswer()) {
                    Files.deleteIfExists(archivePath);
                }
                else return;
            }

            ZipParameters zipParameters = new ZipParameters();
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
                zipParameters.setEncryptFiles(true);
                zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
                zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
                zipParameters.setPassword(password);
            }
            
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
        Map<String, String> parametersMap = new HashMap<>();
        if (parameters == null || parameters.isEmpty()) {
            throw  new CantFindParameterException();
        }

        parameters = parameters.trim();
        String[] splitedParameters = parameters.split(" ");
        if (splitedParameters.length > 2) {
              throw  new SyntaxisException();
        }
        Path fileName = Paths.get(splitedParameters[0]);
        if (Files.notExists(fileName)) {
           throw new DirNotExistException();
        }
        parametersMap.put(FILE_PATH, fileName.toString());

        if (splitedParameters.length == 2) {
            parametersMap.put(TARGET_PATH, splitedParameters[1]);
        }
        this.parameters = parametersMap;
    }

    private String getArchiveName() {
        String zipFileName;
        if (parameters.containsKey(TARGET_PATH)) {
            zipFileName = parameters.get(TARGET_PATH);
        } else {
            zipFileName = parameters.get(FILE_PATH);
        }
        Path path = Paths.get(zipFileName);
        if (Files.isDirectory(path)) {
            zipFileName = path.getFileName().toString();
        } else {
            zipFileName = path.getFileName().toString().replaceAll("\\..*$", "");
        }
        return zipFileName;
    }
}
