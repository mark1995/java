package com.jing.net;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpService02 {
    public static void main(String[] args) {
        try {
            ServerSocket listenSocket = new ServerSocket(8082);
            while (true) {
                Socket acceptSocket = listenSocket.accept();
                new Thread(() -> {
                    handlerConnect(acceptSocket);
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handlerConnect(Socket acceptSocket) {
        OutputStream socketOutput = null;
        try {
            socketOutput = acceptSocket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(socketOutput, true);
            printWriter.write("HTTP/1.1 200 OK");
            printWriter.write("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.write("hello mutil threads");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
