/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.servicerequestor.utilities;

/**
 *
 * @author MacDerson
 */
public interface Parameter<P, V> {
    
    void addMapping(P param, V value);
    
    P getParam();
    
    V getValue();
}
