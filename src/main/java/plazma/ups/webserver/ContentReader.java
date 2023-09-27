package plazma.ups.webserver;

import java.io.*;

public class ContentReader {

    private String pathToWebApp;

    public ContentReader(String pathToWebApp) {
        this.pathToWebApp = pathToWebApp;
    }

    public InputStream readContent(String uri) throws IOException {
        String fileName = toFileName(uri);
        return new FileInputStream(fileName);
    }

    protected String readContent(InputStream is) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
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
