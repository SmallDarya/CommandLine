/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smolienko.commandline.сommands;

import com.smolienko.commandline.Context;
import com.smolienko.commandline.commandlineexceptions.BaseCommandLineException;
import org.springframework.context.MessageSource;

/**
 *
 * @author Pugovka
 */
public interface Command {
   
   void execute() throws BaseCommandLineException;
   void parseParameters(String parameters) throws BaseCommandLineException; 
   void setExecutionContext(Context context);
   void setMessageSource(MessageSource resourse);

}
