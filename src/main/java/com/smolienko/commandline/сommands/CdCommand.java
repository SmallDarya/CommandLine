/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smolienko.commandline.сommands;

import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.CantFindParameterException;
import com.smolienko.commandline.commandlineexceptions.DirNotExistException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pugovka
 */
@Component("cd")
public class CdCommand extends BaseCommand{
    
    private static final String DIR_PATH="newCurrentDir";

    @Override
    public void execute()  throws BaseCommandLineException {;
        String path = parameters.get(DIR_PATH);
        this.context.changeWorkingDir(Paths.get(path));
    }

    @Override
    public void parseParameters(String parameters) throws BaseCommandLineException {
        Map<String, String> parametersMap = new HashMap<>();
        if (parameters == null || parameters.isEmpty()) {
            throw new CantFindParameterException();
        }
        parameters = parameters.trim();
        Path dir = Paths.get(parameters);
        if (Files.notExists(dir)) {
            throw new DirNotExistException();
        }
        if (!Files.isDirectory(dir)) {
            throw new DirNotExistException();
        }
        parametersMap.put(DIR_PATH, dir.toString());
        this.parameters = parametersMap;
    }
}
