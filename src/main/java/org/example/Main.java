package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("10.99.50.58",55555);
        Client client = new Client(socket, "NICK");
        client.start();
        if(socket.isConnected()) System.out.println("Server connected!");

    }
}