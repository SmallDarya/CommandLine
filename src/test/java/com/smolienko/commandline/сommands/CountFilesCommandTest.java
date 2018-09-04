package com.smolienko.commandline.сommands;

import com.smolienko.commandline.CommandLine;

import com.smolienko.commandline.Main;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.class})
public class CountFilesCommandTest {

    @Autowired
    CommandLine commandLine;

    @Autowired
    CommandGenerator commandGenerator;

    private static final String text1 = "countfiles";

    private static final String text2 = "countfiles /h";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    Path workDir;

    @Before
    public void createFiles() {
        try {

            temporaryFolder.newFile("first.txt");
            temporaryFolder.newFile( "second.txt");
            File third = temporaryFolder.newFile("third.txt");
            Files.setAttribute(third.toPath(),"dos:hidden",true);
            temporaryFolder.newFile("fourth.txt");
            temporaryFolder.newFolder("fifth");

            commandLine.changeWorkDir(temporaryFolder.getRoot().toPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void countFiles() throws Exception {
        Command command = commandGenerator.getCommandFromLine(text1);
        command.setExecutionContext(commandLine);
        command.execute();
    }

    @Test
    public void countFilesWithHidden() throws Exception {
        Command command = commandGenerator.getCommandFromLine(text2);
        command.setExecutionContext(commandLine);
        command.execute();
    }

}
