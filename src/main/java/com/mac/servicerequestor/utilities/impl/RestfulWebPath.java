/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.servicerequestor.utilities.impl;

import com.mac.servicerequestor.utilities.Query;
import com.mac.servicerequestor.utilities.WebPath;
import com.mac.servicerequestor.utilities.exceptions.MalFormedPathException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author MacDerson
 */
public class RestfulWebPath implements WebPath {

    private String host;
    private int port;
    private String basePath;
    private List<String> paths;
    private int count = 0;
    private Class<?> returnType;
    private MediaType acceptType;

    @Override
    public String getBasePath() {
        return basePath;
    }

    @Override
    public WebPath setBase(String basePath) {
        if (Objects.isNull(basePath) || basePath.isEmpty()) {
            this.basePath = basePath;
        }
        return this;
    }

    @Override
    public WebPath addToPath(String path) {
        if (Objects.isNull(paths)) {
            paths = new ArrayList(1);
        }
        paths.add(path);
        return this;
    }

    @Override
    public int levels() {
        return Objects.isNull(paths) ? 0 : paths.size();
    }

    @Override
    public boolean hasQuery() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Query getQuery() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setQuery(Query query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isDrillable() {
        int levels = levels();
        return Objects.nonNull(paths) && levels > 0 && count < levels;
    }

    @Override
    public String drillDown() {
        return isDrillable() ? paths.get(count++) : null;
    }

    @Override
    public WebPath setHost(String host) {
        this.host = host;
        return this;
    }

    @Override
    public WebPath setPort(int port) {
        this.port = port;
        return this;
    }

    @Override
    public WebPath fromBase() throws MalFormedPathException {
        WebPath wp = new RestfulWebPath();
        if (!isWellFormed()) {
            throw new MalFormedPathException("Base path was not clearly defined");
        } else {
            wp.setHost(this.host).setPort(port).setBase(basePath);
        }
        return wp;
    }

    @Override
    public boolean isWellFormed() {
        return Objects.nonNull(host) && Objects.nonNull(basePath)
                && !host.isEmpty() && !basePath.isEmpty();
    }

    @Override
    public WebPath fromBase(String... paths) throws MalFormedPathException {
        WebPath wp = fromBase();
        if (Objects.nonNull(paths) && paths.length > 0) {
            for (String path : paths) {
                wp.addToPath(path);
            }
        }
        return wp;
    }

    @Override
    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    @Override
    public WebPath fromBase(Class<?> returnType, String... paths) throws MalFormedPathException {
        WebPath wp = fromBase(paths);
        wp.setReturnType(returnType);
        return wp;
    }

    @Override
    public void setAcceptType(MediaType acceptType) {
        this.acceptType = acceptType;
    }

    @Override
    public WebTarget targetFrom(Client client) throws MalFormedPathException{
        if (isWellFormed()) {
            WebTarget target = client.target(host + (isPortInRange() ? ":" + port : "") + sep() + basePath);
            while(isDrillable()){
                target.path(Optional.ofNullable(drillDown()).orElse(""));
            }
            return target;
        }
        throw new MalFormedPathException();
    }

    @Override
    public MediaType getAcceptType() {
        return Optional.ofNullable(acceptType).orElse(MediaType.APPLICATION_JSON_TYPE);
    }

    private boolean isPortInRange() {
        return true;
    }

    private String sep() {
        return "/";
    }

    @Override
    public Class<?> getReturnType() {
        return returnType;
    }
}
