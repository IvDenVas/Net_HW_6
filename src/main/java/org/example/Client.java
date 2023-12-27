package org.example;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("77.232.130.200", 55555);
            Client client = new Client(socket);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    BufferedWriter out;
    BufferedReader in;
    BufferedReader reader;
    Socket socket;
    String nickname;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            reader = new BufferedReader(new InputStreamReader(System.in));
            this.inputNickname();
            new Receive().start();
            new Write().start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public class Receive extends Thread {
        public void run() {
            try {
                String msg;
                while (true) {
                    msg = in.readLine();
                    if(msg.equals("NICK")) break;
                    else System.out.println(msg);
                }
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public class Write extends Thread {
        public void run() {
            while (true) {
                String msg;
                try {
                    msg = reader.readLine();
                    out.write(getNickname() + ": " + msg + "\n");
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private void inputNickname() {
        System.out.print("Choose your nickname: ");
        try {
            nickname = reader.readLine();
            out.write(nickname);
            out.flush();
            setNickname(nickname);
        } catch (IOException ignored) {
        }
    }
}
