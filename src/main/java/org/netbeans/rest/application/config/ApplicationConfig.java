/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Ángel
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.mycompany.cines2015.json.MovieReader.class);
        resources.add(com.mycompany.cines2015.json.MovieWriter.class);
        resources.add(com.mycompany.cines2015.rest.MovieFacadeREST.class);
        resources.add(com.mycompany.cines2015.rest.PointsFacadeREST.class);
        resources.add(com.mycompany.cines2015.rest.SalesFacadeREST.class);
        resources.add(com.mycompany.cines2015.rest.ShowTimingFacadeREST.class);
        resources.add(com.mycompany.cines2015.rest.TheaterFacadeREST.class);
        resources.add(com.mycompany.cines2015.rest.TimeslotFacadeREST.class);
    }
    
}
