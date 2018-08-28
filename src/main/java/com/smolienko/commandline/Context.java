/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smolienko.commandline;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.ResourceBundle;
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
    
}
