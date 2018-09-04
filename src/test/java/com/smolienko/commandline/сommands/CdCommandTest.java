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



import java.nio.file.Path;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.class})
public class CdCommandTest {

    @Autowired
    CommandLine commandLine;

    @Autowired
    CommandGenerator commandGenerator;

    private static final String commandText = "cd";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    Path workDir;

    @Test
    public void changeDirTest() throws Exception {
        temporaryFolder.newFolder("newWorkDir");
        String fullCommandText= commandText+" \""+temporaryFolder.getRoot().toPath().toString()+"\"";
        Command command = commandGenerator.getCommandFromLine(fullCommandText);
        command.setExecutionContext(commandLine);
        command.execute();
        assertEquals(commandLine.getWorkingDir(), temporaryFolder.getRoot().toPath());
    }


}
