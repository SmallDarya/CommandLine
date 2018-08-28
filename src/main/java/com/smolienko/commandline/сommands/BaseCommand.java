/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smolienko.commandline.сommands;

import com.smolienko.commandline.Context;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import org.springframework.context.MessageSource;

/**
 *
 * @author Pugovka
 */
public abstract class BaseCommand implements Command  {

    protected Map<String, String> parameters;
    protected Context context;
    protected MessageSource resources;

    @Override
    public void setExecutionContext(Context context) {
        this.context = context;
    }  

    @Override
    public void setMessageSource(MessageSource resourse) {
       this.resources=resourse;
    }
    
    boolean getYesNoAnswer(){
        Scanner inStream = this.context.getInStream();
        while(true){
            String answer=inStream.nextLine();
            if(answer.toLowerCase().equals("n"))
                return false;
            if(answer.toLowerCase().equals("y"))
                return true;
            this.context.printOnConsole(resources.getMessage("wrong.answer.repeat", null, "Default", Locale.getDefault()));
        }
    }
    
}
