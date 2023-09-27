package plazma.ups.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class RequestParser {

    public Request parseRequest(BufferedReader reader) throws IOException {

        Request request = new Request();

        String[] lines = readLines(reader);

        if (lines.length == 0) {
            return request;
        }

        String line = lines[0];
        String[] elements = line.split(" ");
        if (elements.length < 2) {
            return request;
        }
        String methodName = elements[0];
        HttpMethod method = findHttpMethod(methodName);
        request.setHttpMethod(method);

        String path = elements[1];
        if (path.equals("/")) {
            path = "/index.html";
        }

        if (lines.length > 1) {
            Map<String, String> headers = new LinkedHashMap<>();
            line = null;
            for (int i = 1; i < lines.length; i++) {

                line = lines[i];
                elements = line.split(" ");
                if (elements.length > 1) {
                    headers.put(elements[0], elements[1]);
                }
            }
            request.setHeaders(headers);
        }

        request.setUri(path);
        return request;
    }

    protected String[] readLines(BufferedReader reader) throws IOException {
        String line = null;
        List<String> lines = new ArrayList<>();
        while (!(line = reader.readLine()).isEmpty()) {
            lines.add(line);
        }
        return lines.toArray(new String[0]);
    }

    protected HttpMethod findHttpMethod(String name) {
        try {
            return HttpMethod.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


}
