package com.animecap.system.components;

/**
 * Created by Nathaniel on 12/18/2016.
 */
public class Status {
    public int success;
    public int code;
    public Object message;
    public Status(int success, int code, Object message){
        this.code=code;
        this.success=success;
        this.message=message;
    }
    public Status(int success, Object message){
        this.success=success;
        this.message=message;
    }
}
