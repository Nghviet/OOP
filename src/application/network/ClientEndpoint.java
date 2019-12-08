package application.network;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@javax.websocket.ClientEndpoint
public class ClientEndpoint {
    Session session = null;
    private MessageHandler messageHandler = null;
    String pending = null;
    private boolean connected = false;

    public ClientEndpoint(URI uri) throws DeploymentException,IOException {
        connected = true;
        WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
        webSocketContainer.connectToServer(this,uri);
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Message incomming");
        pending = message;
    }

    @OnClose
    public void onClose(CloseReason closeReason) {
        this.session = null;
    }

    public void sendMessage(String message) {
        session.getAsyncRemote().sendText(message);
    }

    public boolean isConnected() {
        return session != null;
    }

    public String getData() {
        return pending;
    }
}
