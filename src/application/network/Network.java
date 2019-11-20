package application.network;

import java.io.*;
import java.net.Socket;

public class Network {

    private Socket socket           = null;
    private DataInputStream  input  = null;
    private DataOutputStream output = null;

    public String[] name = new String[10];
    public int[] score = new int[10];

    public Network() throws IOException {
    }

    public void update() throws IOException {
        socket = new Socket("localhost", 3000);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        output.writeUTF("update");
        String line;
        for(int i=0;i<10;i++) {
            line = input.readUTF();
            String[] split = line.split("\\s");
            name[i] = split[0];
            score[i] = Integer.parseInt(split[1]);
        }

        for(int i=0;i<10;i++) System.out.println(name[i]+" "+score[i]);

        input.close();
        output.close();
        socket.close();
    }

    public void add(String name,int score) throws IOException {
        socket = new Socket("localhost", 3000);

        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        output.writeUTF("add");
        output.writeUTF("over");

        input.close();
        output.close();
        socket.close();
    }
}