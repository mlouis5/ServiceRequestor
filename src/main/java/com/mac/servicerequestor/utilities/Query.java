/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.servicerequestor.utilities;

import java.util.List;

/**
 *
 * @author MacDerson
 */
public interface Query extends Drillable<Parameter<String, String>> {
    
    void addParameter(Parameter<String, String> param);
    void addParameters(List<Parameter<String, String>> params);
    Parameter deleteParameter(Parameter<String, String> param);
    void clearparameters();    
    int length();
}
