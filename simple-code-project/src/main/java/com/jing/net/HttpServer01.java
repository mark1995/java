package com.jing.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer01 {


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            while (true) {
                Socket accept = serverSocket.accept();
                handlerConnect(accept);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void handlerConnect(Socket socket) {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            // 装饰者模式
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            printWriter.write("HTTP/1.1 200 OK");
            printWriter.write("Content-Type: text/html;charset=utf-8");
            printWriter.println();
            printWriter.write("hello block ");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
