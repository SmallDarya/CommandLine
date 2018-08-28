package com.smolienko.commandline;

import com.smolienko.commandline.сommands.CommandGenerator;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.CantFindParameterException;
import com.smolienko.commandline.commandlineexceptions.DirNotExistException;
import com.smolienko.commandline.commandlineexceptions.UnknownCommandException;
import com.smolienko.commandline.commandlineexceptions.ZipExecutionException;
import com.smolienko.commandline.сommands.Command;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Darya Smolienko
 */
public  class CommandLine implements Context{

    private static Scanner in = new Scanner(System.in);
    
    private static PrintStream out = System.out;
    
    private String currenDir = System.getProperty("user.dir");
     
    
    @Override
    public  void startWork() {
       
        CommandGenerator commandProcessor = new CommandGenerator();
        Command command;
        while (true) {
            out.print(currenDir+">");
            String inputLine = in.nextLine();
            try {
                if(inputLine.isEmpty())
                    continue;
                command = commandProcessor.getCommandFromLine(inputLine);
                command.setExecutionContext(this);
                command.execute();
            } catch (UnknownCommandException ex) {
                out.println("Неизвестная комманда");
            } catch (ZipExecutionException ex) {
                out.println("Ошибка архивирования");
            } catch (CantFindParameterException ex) {
                out.println("Не могу найти обязательные параметры");
            } catch (DirNotExistException ex) {
                out.println("Указанная папка не существует");
            } catch (BaseCommandLineException ex) {
                Logger.getLogger(CommandLine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public void changeWorkingDir(Path dirPath){
        this.currenDir = dirPath.toString();
    }
    
    @Override
    public void clearAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Path getWorkingDir() {
       return Paths.get(currenDir);
    }

    @Override
    public Scanner getInStream() {
       return in;
    }

    @Override
    public void printOnConsole(String str) {
         out.println(str);
    }
}
