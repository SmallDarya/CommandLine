/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smolienko.commandline.commandlineexceptions;

/**
 *
 * @author Pugovka
 */
public class DirNotExistException extends BaseCommandLineException{
    private final static String MESSAGE="Dir not exist."; 

    public DirNotExistException() {
        super(MESSAGE);
    }
    
}
