package org.example;


import java.io.*;
import java.net.Socket;

public class Client extends Thread {
    BufferedWriter out;
    BufferedReader in;
    BufferedReader reader;
    Socket socket;
    String nickname;

    public Client(Socket socket,String nickname) throws IOException {
        this.socket = socket;
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.nickname = nickname;

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void run() {
        while (true){
            try {

                receive();
                write();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void receive() {
        System.out.println("Введите никнейм: ");
        try {
            String msg = reader.readLine();
            if (getNickname().equals("NICK")) {
                setNickname(msg);
                out.write(msg);
                out.flush();
            } else {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write() throws IOException {

        String msg = nickname + ": " + reader.readLine();
        out.write(msg);
        out.flush();
    }
}
