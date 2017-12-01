package com.eocandos.mc.tools;

public interface IException extends Comparable<IException> {

    public String getType();

    public String getErrorMessage();
    
    public String getStackTrace();

}