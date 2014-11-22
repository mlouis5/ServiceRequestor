/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.servicerequestor.utilities.exceptions;

/**
 *
 * @author MacDerson
 */
public class MalFormedPathException extends Exception{
    
    private String msg;
    
    public MalFormedPathException(){}
    
    public MalFormedPathException(String msg){
        this.msg = msg;
    }
    
    @Override
    public String toString(){
        return msg;
    }
}
