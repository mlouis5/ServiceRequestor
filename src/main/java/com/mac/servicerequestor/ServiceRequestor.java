/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.servicerequestor;

import com.mac.servicerequestor.utilities.WebPath;
import com.mac.servicerequestor.utilities.exceptions.MalFormedPathException;
import java.util.Objects;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

/**
 *
 * @author Mac
 * @param <T>
 */
@Stateless
public interface ServiceRequestor<T extends Object> {

    /**
     *
     * @param resourcePath
     * @param acceptType
     * @return
     */
    default Response makeGetRequest(String resourcePath, MediaType acceptType) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(resourcePath);

        Response response = target.request(acceptType).get(Response.class);

        return response;
    }

    default Response makeGetRequest(String resourceBasePath, String resourcePath, MediaType acceptType) {
        Client client = ClientBuilder.newClient();
        //WebTarget target = client.target(resourceBasePath).path(resourcePath);
        WebTarget target = client.target(resourceBasePath);//.path(Optional.ofNullable(resourcePath).orElse(""));

        Response response = target.request(acceptType).get(Response.class);

        return response;
    }

    default Response makeGetRequest(String resourceBasePath, String resourcePath, MediaType acceptType, ClientConfig cfg) {
        Client client = ClientBuilder.newClient(cfg);
        WebTarget target = client.target(resourceBasePath).path(Optional.ofNullable(resourcePath).orElse(""));
        Response response = target.request(acceptType).get(Response.class);
        return response;
    }

    default Object makeGetRequest(String resourceBasePath, String resourcePath, Class<?> returnType, MediaType acceptType, ClientConfig cfg) {
        Client client = ClientBuilder.newClient(cfg);

        WebTarget target = client.target(resourceBasePath).path(Optional.ofNullable(resourcePath).orElse(""));

        Object requested = target.request(acceptType).get(returnType);

        return requested;
    }
    
    default Object makeGetRequest(WebPath webPath, ClientConfig cfg) throws MalFormedPathException {
        Client client = ClientBuilder.newClient(cfg);

        WebTarget target = webPath.targetFrom(client);
        return target.request(webPath.getAcceptType()).get(webPath.getReturnType());
    }

    default T makeGetRequest(Class<? extends T> returnType, MediaType acceptType, ClientConfig cfg, String basePath, String... pathTokens) {
        Client client = ClientBuilder.newClient(cfg);

        WebTarget target = client.target(basePath);
        if (Objects.nonNull(pathTokens) && pathTokens.length > 0) {
            for (String path : pathTokens) {
                if (Objects.nonNull(path) && !path.isEmpty()) {
                    target.path(path);
                }
            }
        }        
        return target.request(acceptType).get(returnType);
    }

    default Object makeGetRequest(String resourceBasePath, String resourcePath, GenericType<? extends Object> returnType, MediaType acceptType, ClientConfig cfg) {
        Client client = ClientBuilder.newClient(cfg);
        WebTarget target = client.target(resourceBasePath).path(Optional.ofNullable(resourcePath).orElse(""));
        Object requested = target.request(acceptType).get(returnType);
        return requested;
    }
}
