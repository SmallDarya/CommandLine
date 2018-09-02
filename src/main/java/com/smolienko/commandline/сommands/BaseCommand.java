package com.smolienko.commandline.сommands;

import com.smolienko.commandline.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;


/**
 * Class contains main information about command and some base methods.
 * 
 * @author Darya Smolienko
 */
public abstract class BaseCommand implements Command  {

     /**
     * The parameters that contained every comand, it can be empry.
     */
    protected Map<String, String> parameters =new HashMap<>();
    
     /**
     * Context for executing command.
     */
    protected Context context;
    
     /**
     * Mesages for locale
     */
    @Autowired
    protected MessageSource resources;

    @Override
    public void setExecutionContext(Context context) {
        this.context = context;
    }  

     /**
     * It is a simple dialog. The function return users ansfer on yes/no question
     * 
     * @return  users answer
     */
    protected boolean getYesNoAnswer(){
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
    
    protected List<String>getParametersList(String str){
          List<String>  matchList =   new ArrayList<String>();
     if (str == null || str.isEmpty()) {
            return matchList;
        }
        str = str.trim().replaceAll("[\\s]{2,}", " ");
    
        Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
        Matcher regexMatcher = regex.matcher(str);
        while (regexMatcher.find()) {
              matchList.add(regexMatcher.group().replaceAll("\"|\'", ""));
        }
        return matchList;
    }
    
}
