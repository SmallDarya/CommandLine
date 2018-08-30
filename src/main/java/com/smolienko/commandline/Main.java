package com.smolienko.commandline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Pugovka
 */
@SpringBootApplication
public class Main implements CommandLineRunner {
    
    @Autowired
    CommandLine commandLine;
            
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Main.class);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {    
        commandLine.startWork();
    }
}
