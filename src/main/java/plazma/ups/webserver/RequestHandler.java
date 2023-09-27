package plazma.ups.webserver;

import java.io.*;

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
        Response response = createResponse(request);

        writeResponse(writer, response);

    }

    protected Response createResponse(Request request) {
        Response response = new Response();

        if (request.getHttpMethod() == null || request.getUri() == null) {
            response.setHttpStatus(HttpStatus.BAD_REQUEST);
            return response;
        }

        String path = request.getUri();
        try {
            InputStream is = contentReader.readContent(path);
            response.setContent(is);
            response.setHttpStatus(HttpStatus.OK);
        } catch (IOException e) {
            response.setHttpStatus(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    protected void writeResponse(BufferedWriter writer, Response response) throws IOException {

        ResponseWriter responseWriter = new ResponseWriter(contentReader);

        if (response.getHttpStatus() != HttpStatus.OK) {
            responseWriter.writeErrorResponse(writer, response.getHttpStatus());
            return;
        }

        responseWriter.writeResponse(writer, response);

    }

}
