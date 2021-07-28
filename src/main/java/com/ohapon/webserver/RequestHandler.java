package com.ohapon.webserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        String[] lines = readLines(reader);

        if (lines.length == 0) {
            handleStatus(writer, HttpStatus.BAD_REQUEST);
            return;
        }

        String line = lines[0];
        String[] elements = line.split(" ");
        if (elements.length < 2) {
            handleStatus(writer, HttpStatus.BAD_REQUEST);
            return;
        }
        String method = elements[0];

        if (!method.startsWith("GET")) {
            handleStatus(writer, HttpStatus.INTERNAL_SERVER_ERROR);
            return;
        }

        String path = elements[1];
        if (path.equals("/")) {
            path = "/index.html";
        }

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

    protected String[] readLines(BufferedReader reader) throws IOException {
        String line = null;
        List<String> lines = new ArrayList<>();
        while (!(line = reader.readLine()).isEmpty()) {
            lines.add(line);
        }
        return lines.toArray(new String[0]);
    }

    protected void handleStatus(BufferedWriter writer, HttpStatus status) throws IOException {
        writer.write("HTTP/1.1 " + status.getCode() + " " + status.getName());
    }

}
