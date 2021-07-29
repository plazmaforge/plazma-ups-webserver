package com.ohapon.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private static final int DEFAULT_PORT = 3000;
    private int port;
    private String webAppPath;
    private boolean isStarting;
    private ContentReader contentReader;

    public WebServer() {
        this(DEFAULT_PORT);
    }

    public WebServer(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getWebAppPath() {
        return webAppPath;
    }

    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    public void start() throws IOException {
        if (isStarting) {
            throw new RuntimeException("Server is started");
        }

        isStarting = true;
        contentReader = new ContentReader(getWebAppDir());

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("WebServer: Start");
            while (isStarting) {

                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    RequestHandler handler = new RequestHandler(reader, writer, contentReader);
                    handler.handle();

                }

            }
            System.out.println("WebServer: Finish");

        }

    }

    public void stop() {
        isStarting = false;
    }

    protected String getWebAppDir() {
        // TODO: For test only
        //if (webAppPath == null) {
        //    webAppPath = System.getProperty("user.dir") + "/" + "src/test/resources/WebApp";
        //}
        return webAppPath;
    }

    public static void main(String[] args) {
        WebServer server = new WebServer();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
