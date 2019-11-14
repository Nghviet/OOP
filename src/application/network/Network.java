package application.network;

import java.io.*;
import java.net.Socket;

public class Network {

    private Socket socket           = null;
    private DataInputStream  input  = null;
    private DataOutputStream output = null;

    public Network() throws IOException, ClassNotFoundException, InterruptedException {
        socket = new Socket("localhost", 3000);
        System.out.println("Connected");

        input  = new DataInputStream(System.in);
        output = new DataOutputStream(socket.getOutputStream());

        output.writeUTF("1234567789");
        output.writeUTF("over");

        input.close();
        output.close();
        socket.close();
    }

    //get all highscore in server
    public void update() throws IOException {
        socket = new Socket("localhost", 3000);

        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        output.writeUTF("update");
        String line = "";
        while(!line.equals("over")) {
            try {
                line = input.readUTF();
                System.out.println(line);
            } catch (IOException io) {
                System.out.println(io);
            }
        }

        output.writeUTF("over");

        input.close();
        output.close();
        socket.close();
    }

    public void post(String name,int score) throws IOException {
        socket = new Socket("localhost", 3000);

        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        output.writeUTF("post");
        String line = "";
        while(!line.equals("over")) {
            try {
                line = input.readUTF();
                System.out.println(line);
            } catch (IOException io) {
                System.out.println(io);
            }
        }

        output.writeUTF("over");

        input.close();
        output.close();
        socket.close();
    }
}