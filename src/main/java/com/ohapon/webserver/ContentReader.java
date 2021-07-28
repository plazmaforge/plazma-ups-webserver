package com.ohapon.webserver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ContentReader {

    private String pathToWebApp;

    public ContentReader(String pathToWebApp) {
        this.pathToWebApp = pathToWebApp;
    }

    public String readContent(String uri) throws IOException {
        String fileName = toFileName(uri);
        String content = readFileContent(fileName);
        return content;
    }

    protected String readFileContent(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
            return builder.toString();
        }
    }

    protected String toFileName(String uri) {
        return pathToWebApp + uri;
    }

}
