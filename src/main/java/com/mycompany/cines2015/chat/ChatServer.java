/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cines2015.chat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Ángel
 */ 
@ServerEndpoint("/websocket") /*la clase va a utilizar un WebSocket y la URI donde se va a publicar*/
public class ChatServer {
        
    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    
    @OnOpen /*Metodo para cuando se abre la sesión*/
    public void onOpen(Session peer) { /*METODO A LLAMAR CUANDO SE ABRE LA CONEXIÓN*/
        peers.add(peer);
    }
    
    @OnClose /*Metodo para cuando se cierra la sesión*/
    public void onClose(Session peer) { /*METODO A LLAMAR CUANDO SE CIERRA LA CONEXIÓN*/
        peers.remove(peer);
    }
    
    @OnMessage
    public void message(String message, Session client) throws IOException, EncodeException {
        /*METODO QUE RECIBE EL MENSAJE WEBSOCKETS ENTRANTE*/
        /*PARÁMETRO MENSAJE ES EL QUE SE RECIBE*/
        /*CLIENT ES QUIEN HA ENVIADO EL MENSAJE*/
        for (Session peer : peers) {
            peer.getBasicRemote().sendText(message);
        }
        /*Para enviarselo a todos los clientes, si se quiere especificar uno en concreto utilizar 
        el parámetro client*/
    }
}

