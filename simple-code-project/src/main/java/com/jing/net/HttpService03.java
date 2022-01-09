package com.jing.net;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HttpService03 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        try {
            final ServerSocket listenSocket = new ServerSocket(8083);
            while (true) {
                final Socket acceptSocket = listenSocket.accept();
                executorService.execute(() -> {
                    handlerConnect(acceptSocket);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handlerConnect(Socket acceptSocket) {
        try {
            OutputStream outputStream = acceptSocket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write("HTTP/1.1 200 OK");
            printWriter.write("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.write("hello nio");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
