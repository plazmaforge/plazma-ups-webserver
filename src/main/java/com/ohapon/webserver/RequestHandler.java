package com.ohapon.webserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class RequestHandler {

    private BufferedReader reader;
    private BufferedWriter writer;
    private ContentReader contentReader;

    public RequestHandler(BufferedReader reader, BufferedWriter writer, ContentReader contentReader) {
        this.reader = reader;
        this.writer = writer;
        this.contentReader = contentReader;
    }

    public void handle() throws IOException {

        RequestParser requestParser = new RequestParser();
        Request request = requestParser.parseRequest(reader);
        if (request.getHttpMethod() == null || request.getUri() == null) {
            handleStatus(writer, HttpStatus.BAD_REQUEST);
            return;
        }

        String path = request.getUri();
        String fileContent = null;
        try {
            fileContent = contentReader.readContent(path);
        } catch (IOException e) {
            handleStatus(writer, HttpStatus.NOT_FOUND);
            return;
        }

        // write response
        handleStatus(writer, HttpStatus.OK);
        writer.newLine();
        writer.newLine();

        writer.write(fileContent);

    }

    protected void handleStatus(BufferedWriter writer, HttpStatus status) throws IOException {
        writer.write("HTTP/1.1 " + status.getCode() + " " + status.getName());
    }

}
