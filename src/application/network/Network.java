package application.network;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Network {
    private List<Score> scores = new ArrayList<>();
    private ClientEndpoint client;
    private boolean isConnected = false;
    private URI server = new URI("ws://localhost:3000");
    public Network() throws URISyntaxException {
        System.out.println("Network init");
        update();
    }
    public void update() {
        if(client == null) {
            try{
                client = new ClientEndpoint(server);
                isConnected = true;
            } catch(Exception e) {
                return;
            }
        }

        if(!client.isConnected())
            try {
                client = new ClientEndpoint(server);
                isConnected = true;
            } catch(Exception e) {
                return;
            }

        client.sendMessage("get");
        System.out.println("Sended");
        int n = 0;
        while(client.getData() == null) {
            n = (n + 1) % 2;
            System.out.println(client.getData());
        }

        System.out.println("Resolve");
        String data = client.getData();

        scores = new ArrayList<>();
        String[] lines = data.split("\\s?\\n");
        for(String line:lines) scores.add(new Score(line));
    }

    public boolean isHighscore(int score) {
        if(score > scores.get(9).getScore()) return true;
        else return false;
    }

    public void addHighscore(String name,int score) {
        scores.remove(9);
        scores.add(new Score(name,score));
        Collections.sort(scores);

        String data = null;
        for(Score s:scores) {
            if(data!=null)
            data += "\n" + s.data();
            else data = s.data();
        }

        client.sendMessage("update");
        client.sendMessage(data);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public List<Score> getScores() {
        return scores;
    }
}
