package plazma.ups.webserver;

import java.io.IOException;
import java.io.Writer;

public class ResponseWriter {

    private ContentReader contentReader;

    public ResponseWriter(ContentReader contentReader) {
        this.contentReader = contentReader;
    }

    public void writeResponse(Writer writer, Response response) throws IOException {
        if (response.getHttpStatus() != HttpStatus.OK) {
            handleStatus(writer, response.getHttpStatus());
            return;
        }

        String fileContent = contentReader.readContent(response.getContent());

        // write response
        handleStatus(writer, HttpStatus.OK);
        writer.write("\r\n");
        writer.write("\r\n");

        writer.write(fileContent);

    }

    public void writeErrorResponse(Writer writer, HttpStatus status) throws IOException {
        handleStatus(writer, status);
    }

    protected void handleStatus(Writer writer, HttpStatus status) throws IOException {
        writer.write("HTTP/1.1 " + status.getCode() + " " + status.getName());
    }

}
