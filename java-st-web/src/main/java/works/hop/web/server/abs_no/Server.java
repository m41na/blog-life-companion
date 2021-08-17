package works.hop.web.server.abs_no;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        int PORT = args.length > 0 ? Integer.parseInt(args[0]) : 8088;

        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            while(true){
                try (Socket client = serverSocket.accept()) {
                    handleClient(client);
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void handleClient(Socket client) throws IOException {
        System.out.println("Debug: got new client " + client.toString());
        try(BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()))) {

            StringBuilder requestBuilder = new StringBuilder();
            String line;
            while (!(line = br.readLine()).isBlank()) {
                requestBuilder.append(line).append("\r\n");
            }

            String request = requestBuilder.toString();
            System.out.println(request);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
