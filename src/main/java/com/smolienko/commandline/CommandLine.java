package com.smolienko.commandline;

import com.smolienko.commandline.сommands.CommandGenerator;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import com.smolienko.commandline.commandlineexceptions.CantFindParameterException;
import com.smolienko.commandline.commandlineexceptions.DirNotExistException;
import com.smolienko.commandline.commandlineexceptions.SyntaxisException;
import com.smolienko.commandline.commandlineexceptions.UnknownCommandException;
import com.smolienko.commandline.commandlineexceptions.ZipExecutionException;
import com.smolienko.commandline.сommands.Command;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 *
 * The class is realization of command line. Class has method startWork which 
 * start listening of input stream for commands. When user write command, 
 * command line appriciate this comand an creat an instance, and then execute it.
 * 
 * @author Darya Smolienko
 */
@Component
public class CommandLine implements Context {

    private static Scanner in = new Scanner(System.in);

    private static PrintStream out = System.out;

    private String currenDir = System.getProperty("user.dir");

    @Autowired
    CommandGenerator commandProcessor;

    @Autowired
    MessageSource resources;

    @Override
    public void startWork() {
        Command command;
        while (true) {
            out.print(currenDir + ">");
            String inputLine = in.nextLine();
            try {
                if (inputLine.isEmpty()) {
                    continue;
                }
                command = commandProcessor.getCommandFromLine(inputLine);
                command.setExecutionContext(this);
                command.execute();
            } catch (UnknownCommandException ex) {
                out.println(resources.getMessage("unknown.command", null, Locale.getDefault()));
            } catch (SyntaxisException ex) {
                out.println(resources.getMessage("syntaxis.exception", null, Locale.getDefault()));
            } catch (ZipExecutionException ex) {
                out.println(resources.getMessage("zip.exception", null, Locale.getDefault()));
            } catch (CantFindParameterException ex) {
                out.println(resources.getMessage("cant.find.parameter", null, Locale.getDefault()));
            } catch (DirNotExistException ex) {
                out.println(resources.getMessage("dir.not.exist", null, Locale.getDefault()));
            } catch (BaseCommandLineException ex) {
                Logger.getLogger(CommandLine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void changeWorkDir(Path dirPath) {
        this.currenDir = dirPath.toString();
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

    @Override
    public void formattingPrintOnConsole(String message, Object... args) {
        out.printf(message, args);
    }
}
