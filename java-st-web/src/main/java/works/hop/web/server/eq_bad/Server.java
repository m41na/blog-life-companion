package works.hop.web.server.eq_bad;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        int PORT = args.length > 0 ? Integer.parseInt(args[0]) : 6666;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket client = serverSocket.accept()) {
                    handleClient(client);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void handleClient(Socket client) {
        System.out.println("Debug: got new client " + client.toString());
        try (InputStream in = client.getInputStream();
             OutputStream out = client.getOutputStream()) {

            int ch;
            while ((ch = in.read()) != -1) {
                ch = Character.isLetter(ch)? (ch ^ ' ')  : ch;
                out.write(ch);
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
