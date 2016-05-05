/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cines2015.client;

import com.mycompany.cines2015.entities.Movie;
import com.mycompany.cines2015.json.MovieReader;
import com.mycompany.cines2015.json.MovieWriter;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ángel
 */
@Named
@RequestScoped
public class MovieClientBean {
    
    @Inject
    MovieBackingBean bean;
    
    Client client;
    WebTarget target;
    
    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient(); //API principal para invocar servicios REST
        target = client.target("http://localhost:8080/cines2015/webresources/com.mycompany.cines2015.movie");
    }
    
    @PreDestroy
    public void destroy() {
        client.close();
    }
        
    public Movie[] getMovies() {
        //mediante request se hacen peticiones, en este caso almacena en un array todas las películas
        return target
            .request()
            .get(Movie[].class);
    }
    
    public Movie getMovie() {
        Movie m = target
            .register(MovieReader.class)
            .path("{movieId}")
            .resolveTemplate("movieId", bean.getMovieId())
            .request(MediaType.APPLICATION_JSON)
            .get(Movie.class);
        return m;
    }
    
    public void deleteMovie() {
        target.path("{movieId}")
            .resolveTemplate("movieId", bean.getMovieId())
            .request()
            .delete();
    }
    
    public void addMovie() {
        Movie m = new Movie();
        m.setId(1);
        m.setName(bean.getMovieName());
        m.setActors(bean.getActors());
        target.register(MovieWriter.class)
            .request()
            .post(Entity.entity(m, MediaType.APPLICATION_JSON));
    }
}
