package application.network;


import org.glassfish.tyrus.client.ClientManager;
import sun.nio.ch.Net;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

public class Network {
    public String[] names;
    public int[] scores;
    private ClientEndpoint client;
    private boolean isConnected = false;

    public Network() throws URISyntaxException {
        try {
            client = new ClientEndpoint(new URI("ws://localhost:3000"));
            isConnected = true;
        } catch (DeploymentException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

    }
    public void update() {
        if(!isConnected) return;
        client.sendMessage("update");
        String data = client.getData();
        while(data == null) data = client.getData();
    }

    public boolean isHighscore(int score) {
        if(score > scores[9]) return true;
        else return false;
    }

    public void sendScore(String name,int score) {
        if(!isConnected) return;
        client.sendMessage("add " + name + " " + score);
    }

    public boolean isConnected() {
        return isConnected;
    }
}
