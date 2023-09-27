package plazma.ups.webserver;

import java.io.InputStream;

public class Response {

    private HttpStatus httpStatus;

    private InputStream content;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }
}
