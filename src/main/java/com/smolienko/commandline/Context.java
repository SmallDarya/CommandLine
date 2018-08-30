package com.smolienko.commandline;

import java.nio.file.Path;
import java.util.Scanner;

/**
 *
 * @author Pugovka
 */
public interface Context {

    void changeWorkingDir(Path dirPath);
    
    Path getWorkingDir();

    void startWork();
    
    void clearAll();
    
    Scanner getInStream();
    
    void printOnConsole(String message);
    
     void formattingPrintOnConsole(String message, Object... args);
}
