package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int port = 3800;
        DataInputStream in;
        DataOutputStream out;
        ServerSocket server;
        Socket socket;
        Thread thread;
        try {
            server = new ServerSocket(port);
            socket = server.accept();
            System.out.println("connected");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Runnable readLine = new ReadLine(in);
            thread = new Thread(readLine);
            thread.start();
            Scanner input = new Scanner(System.in);
            while (true){
                String line = input.nextLine();
                out.writeUTF(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


class ReadLine implements Runnable{
    DataInputStream in;
    public ReadLine( DataInputStream in){
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (true){
                System.out.println(in.readUTF());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}