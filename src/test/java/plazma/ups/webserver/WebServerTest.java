package plazma.ups.webserver;

import org.junit.jupiter.api.Test;
import plazma.ups.webserver.WebServer;

import java.io.IOException;
import java.text.DecimalFormat;

public class WebServerTest {

    @Test
    public void testSendMessage() throws IOException {
        //float x = 123456789012345678901234567890.1234567890f;
        //double y = 123456789012345678901234567890.1234567890d;

        float x = 12345.4567f;
        double y = 123456789012.456789d;

        DecimalFormat format = new DecimalFormat("#.####");
        System.out.println(x);
        System.out.println(y);
        System.out.println(format.format(x));
        System.out.println(format.format(y));

        WebServer server = new WebServer();

        Thread thread = new Thread(() -> {
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Client client = new Client();
//        String response = client.sendMessage("Hi!");
//        server.stop();
//
//        assertEquals("Echo: Hi!", response);

    }

}
