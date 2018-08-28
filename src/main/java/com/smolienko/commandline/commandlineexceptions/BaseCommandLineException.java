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
public abstract class BaseCommandLineException extends Exception {

    private String message;

    public BaseCommandLineException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
