package com.test.demo.service.impl.exception;

public class PasswordEncodeException extends Exception {

    public PasswordEncodeException(){}

    public PasswordEncodeException(String msg){super(msg);}

    public PasswordEncodeException(String msg, Exception nested){super(msg, nested);}
}
