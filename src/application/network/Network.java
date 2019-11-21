package application.network;


import org.glassfish.tyrus.client.ClientManager;
import sun.nio.ch.Net;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

public class Network {
    public String[] name;
    public int[] score;
    private Client client;

    public Network() throws URISyntaxException, IOException, DeploymentException {
        client = new Client(new URI("ws://localhost:3000"));
    }

    @ClientEndpoint
    public class Client {
        Session session = null;
        private MessageHandler messageHandler = null;
        String pending = null;
        private boolean connected = false;

        public Client(URI uri) {
            connected = true;
            try {
                WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
                webSocketContainer.connectToServer(this,uri);
            } catch (DeploymentException e) {
                connected = false;
                return;
            } catch (IOException e) {
                connected = false;
                return;
            }
        }

        @OnOpen
        public void onOpen(Session session) {
            System.out.println(session.isOpen());
            this.session = session;
        }

        @OnMessage
        public void onMessage(String message) {
            System.out.println(message);
            pending = message;
        }

        @OnClose
        public void onClose(CloseReason closeReason) {
            this.session = null;
        }

        public void sendMessage(String message) {
            session.getAsyncRemote().sendText(message);
        }
    }
}
