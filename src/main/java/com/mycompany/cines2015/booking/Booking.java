/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cines2015.booking;

import com.mycompany.cines2015.entities.Movie;
import com.mycompany.cines2015.entities.ShowTiming;
import java.io.Serializable;
import java.util.*;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import javax.persistence.*;
/**
 *
 * @author Ángel
 */
@Named  //clase EL inyectable
@FlowScoped("booking")  //bean de tipo Flow
@PersistenceContext  //una entidad siempre se maneja en contexto de persistencia


public class Booking implements Serializable{
    
    //la clase Flow Scoped de soporte a Booking.xhtml
    
    int movieId;
    EntityManager em;
    String startTime;
    int startTimeId;
    private double cantidad;
    private String tarjeta = "";
    private Date fecha = new Date();

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public Booking(){
        
    }
    
    //MÉTODO QUE NOS DEVUELVE EL NOMBRE DE LA PELÍCULA A PARTIR DE SU ID
    public String getMovieName() {
        try {
            //accede a la clase entidad Movie, mediante persistenceContext y así acceder
            //a la base de datos de las películas
            return em.createNamedQuery("Movie.findById", Movie.class)
                .setParameter("id", movieId)
                .getSingleResult()
                .getName();
        } catch (NoResultException e) {
            return "";
        }
    }
    
    public String getStartTime() {
        return startTime;
    }
    
    public void setStartTime(String startTime) {
        //TOKENIZAR POR COMAS Y GUARDAR LAS DOS VARIABLES
        StringTokenizer tokens = new StringTokenizer(startTime, ",");
        startTimeId = Integer.parseInt(tokens.nextToken());
        this.startTime = tokens.nextToken();
    }
    
    public int getStartTimeId() {
        return startTimeId;
    }
    
    //Encuentra el primer cine que proyecte la película
    //seleccionada en la hora seleccionada
    public String getTheater() {
        try {
            List<ShowTiming> list
                = em.createNamedQuery("ShowTiming.findByMovieAndTimingId",
                ShowTiming.class)
                .setParameter("movieId", movieId)
                .setParameter("timingId", startTimeId)
                .getResultList();
            if (list.isEmpty()) {
                return "none"; //si la lista esta vacía será necesario seleccionar otra hora
            }
            return list
                .get(0)
                .getTheaterId()
                .getId()
                .toString();
            //si la lista no esta vacía seleccinamos el primer resultado de cine y pasamos a la 
            //ventana de confirmación
        } catch (NoResultException e) {
            return "none";
        }
    }
}
