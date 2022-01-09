package com.jing.netty;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

public class Server {

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(8084);
        try {
            httpServer.run();
        } catch (InterruptedException | CertificateException | SSLException e) {
            e.printStackTrace();
        }
    }
}
