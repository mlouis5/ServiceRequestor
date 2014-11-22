/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.servicerequestor.utilities;

import com.mac.servicerequestor.utilities.exceptions.MalFormedPathException;
import com.mac.servicerequestor.utilities.impl.RestfulWebPath;
import java.util.Objects;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author MacDerson
 */
public interface WebPath extends Drillable<String> {

    static WebPath of(String host, int port, String base, Class<?> returnType, String... paths) {
        WebPath wp = new RestfulWebPath();
        wp.setHost(host);
        wp.setPort(port);
        wp.setBase(base);
        wp.setReturnType(returnType);
        if (Objects.nonNull(paths) && paths.length > 0) {
            for (String path : paths) {
                wp.addToPath(path);
            }
        }
        return wp;
    }

    static WebPath of(String host, int port, String base, Class<?> returnType,
            MediaType acceptType, String... paths) {
        WebPath wp = new RestfulWebPath();
        wp.setHost(host);
        wp.setPort(port);
        wp.setBase(base);
        wp.setReturnType(returnType);
        wp.setAcceptType(acceptType);
        if (Objects.nonNull(paths) && paths.length > 0) {
            for (String path : paths) {
                wp.addToPath(path);
            }
        }
        return wp;
    }

    void setAcceptType(MediaType acceptType);
    
    WebTarget targetFrom(Client client) throws MalFormedPathException;
    
    MediaType getAcceptType();

    void setReturnType(Class<?> returnType);
    
    Class<?> getReturnType();

    boolean isWellFormed();

    String getBasePath();

    WebPath fromBase() throws MalFormedPathException;

    WebPath fromBase(String... paths) throws MalFormedPathException;

    WebPath fromBase(Class<?> returnType, String... paths) throws MalFormedPathException;

    WebPath setHost(String host);

    WebPath setPort(int port);

    WebPath setBase(String basePath);

    WebPath addToPath(String path);

    int levels();

    boolean hasQuery();

    Query getQuery();

    void setQuery(Query query);
}
