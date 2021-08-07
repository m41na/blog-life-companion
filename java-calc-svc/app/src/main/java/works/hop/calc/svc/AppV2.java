package works.hop.calc.svc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.function.BiConsumer;

public class AppV2 {

    private static final Server server = new Server();
    private static final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

    private static void initServer() {
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(8080);
        http.setIdleTimeout(30000);
        server.addConnector(http);
    }

    private static void initContextHandler() {
        context.setContextPath("/");
        server.setHandler(context);
    }

    private static void register(HttpServlet servlet, String pathSpec) {
        context.addServlet(new ServletHolder(servlet), pathSpec);
    }

    private static void addHandler(String pathSpec, BiConsumer<HttpServletRequest, HttpServletResponse> handler) {
        register(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                handler.accept(req, resp);
            }
        }, pathSpec);
    }

    private static void start() throws Exception {
        server.start();
        server.join();
    }

    public static void main(String[] args) throws Exception {
        initServer();
        initContextHandler();

        addHandler("/", (req, resp) -> {
            resp.setStatus(200);
            PrintWriter out;
            try {
                out = resp.getWriter();
                out.println("Hello World servlet");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        });

        addHandler("/time", (req, resp) -> {
            resp.setStatus(200);
            PrintWriter out;
            try {
                out = resp.getWriter();
                out.println(LocalDate.now().toString());
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        });

        start();
    }
}
