package com.smolienko.commandline.сommands;

import com.smolienko.commandline.annotations.CommandDescription;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.CommandLineAlgorithmException;
import com.smolienko.commandline.commandlineexceptions.SyntaxisException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;

/**
 *
 * @author Darya Smolienko
 */
@CommandDescription(
        parameters = "[/h] [/az|/za]",
        name = "fileslist",
        description = "files.list.description"
)
@Component("fileslist")
@Scope("prototype")
public class FilesListCommand extends BaseCommand {
    
    private static final String COUNT_HIDDEN = "countHidden";
    private static final String SORT_METHOD = "sortMethod";
    
    @Override
    public void execute() throws BaseCommandLineException {
        List<String> fileNames = new ArrayList<>();
        boolean countHidden = false;
        if (parameters.containsKey(COUNT_HIDDEN)) {
            countHidden = Boolean.parseBoolean(parameters.get(COUNT_HIDDEN));
        }
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(context.getWorkingDir())) {
            for (Path path : directoryStream) {
                if (!countHidden) {
                   DosFileAttributes attr = Files.readAttributes(path, DosFileAttributes.class);
                    if( attr.isHidden())
                        continue;
                }
                fileNames.add(path.getFileName().toString());
            }
        } catch (IOException ex) {
            throw new CommandLineAlgorithmException();
        }
        
        String sortMethod = "az";
        if (parameters.containsKey(SORT_METHOD)) {
            sortMethod = parameters.get(SORT_METHOD);
        }
        if (sortMethod.equals("az")) {
            fileNames.sort(new AZComparator());
        } else {
            fileNames.sort(new ZAComparator());
        }
        
        fileNames.stream().forEach((fileName) -> {
            context.printOnConsole(fileName);
        });
    }
    
    @Override
    public void parseParameters(String parametersStr) throws BaseCommandLineException {
        List<String> parametersList = getParametersList(parametersStr);
        if (parametersList.size() > 2) {
            throw new SyntaxisException();
        }
        if (parametersList.contains("/h")) {
            parameters.put(COUNT_HIDDEN, "true");
        }

        if (parametersList.contains("/az")) {
            if (parametersList.contains("/za")) {
                throw new SyntaxisException();
            }
            parameters.put(SORT_METHOD, "az");
        } else if (parametersList.contains("/za")) {
            if (parametersList.contains("/az")) {
                throw new SyntaxisException();
            }
            parameters.put(SORT_METHOD, "za");
        }

    }
    
    class AZComparator implements Comparator<String> {
        
        @Override
        public int compare(String fileName1, String fileName2) {
            return fileName1.compareTo(fileName2);
        }
    }
    
    class ZAComparator implements Comparator<String> {
        
        @Override
        public int compare(String fileName1, String fileName2) {
            return fileName2.compareTo(fileName1);
        }
    }
}
