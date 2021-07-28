package com.ohapon.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private static final int DEFAULT_PORT = 3000;
    private int port;
    private String webAppPath;
    private boolean processing;
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
        if (processing) {
            throw new RuntimeException("Server is started");
        }

        processing = true;

        try (ServerSocket serverSocket = new ServerSocket(3000)) {

            System.out.println("WebServer: Start");
            while (processing) {

                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

                    handle(reader, writer);

                }

            }
            System.out.println("WebServer: Finish");

        }

    }

    public void stop() {
        processing = false;
    }

    protected void handle(BufferedReader reader, BufferedWriter writer) throws IOException {
        RequestHandler handler = new RequestHandler(reader, writer, getContentReader());
        handler.handle();

    }

    protected String getWebAppDir() {
        // TODO: For test only
        if (webAppPath == null) {
            webAppPath = System.getProperty("user.dir") + "/" + "src/test/resources/WebApp";
        }
        return webAppPath;
    }

    protected ContentReader getContentReader() {
        if (contentReader == null) {
            contentReader = new ContentReader(getWebAppDir());
        }
        return contentReader;
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
