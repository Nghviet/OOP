package application.network;


import org.glassfish.tyrus.client.ClientManager;
import sun.nio.ch.Net;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Network {
    public String[] name;
    public int[] score;


    public Network() throws URISyntaxException, IOException, DeploymentException {
        ClientManager cm = ClientManager.createClient();

        cm.connectToServer(Client.class, new URI("ws://localhost:3000"));
    }

    @ClientEndpoint
    public static class Client {

        @OnOpen
        public void onOpen(Session session) {
            System.out.println(session);
        }

        @OnMessage
        public void onMessage(String message, Session session) {

        }

        @OnClose
        public void onClose(Session session, CloseReason closeReason) {

        }

    }
}
