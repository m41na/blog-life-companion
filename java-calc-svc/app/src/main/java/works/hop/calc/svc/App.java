package works.hop.calc.svc;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import works.hop.calc.svc.api.Request;
import works.hop.calc.svc.api.Response;
import works.hop.calc.svc.api.Router;
import works.hop.calc.svc.route.*;
import works.hop.calc.svc.service.CalcService;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class App {

    private static final Server server = new Server();
    private static final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    private static final Router router = new JsRouter(context);

    public static void initServer() {
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(8080);
        http.setIdleTimeout(30000);
        server.addConnector(http);
    }

    public static void initContextHandler(String ctxPath) {
        context.setContextPath(ctxPath);
        server.setHandler(context);

        ServletHolder holder = context.addServlet(org.eclipse.jetty.servlet.DefaultServlet.class, "/tmp/*");
        holder.setInitParameter("resourceBase", "/tmp");
        holder.setInitParameter("pathInfoOnly", "true");
    }

    public static void main(String[] args) throws Exception {
        CalcService cal = new CalcService();
        initServer();
        initContextHandler("/");
        addHandler("get", "/plus", (request, response) -> {
            Integer left = request.param("left");
            Integer right = request.param("right");
            return CompletableFuture.supplyAsync(() -> cal.plus(left, right));
        });
        addHandler("get", "/minus", (request, response) -> {
            Integer left = request.param("left");
            Integer right = request.param("right");
            return CompletableFuture.supplyAsync(() -> cal.minus(left, right));
        });
        addHandler("get", "/times", (request, response) -> {
            Integer left = request.param("left");
            Integer right = request.param("right");
            return CompletableFuture.supplyAsync(() -> cal.times(left, right));
        });
        addHandler("get", "/divide", (request, response) -> {
            Integer left = request.param("left");
            Integer right = request.param("right");
            return CompletableFuture.supplyAsync(() -> cal.divide(left, right));
        });

        server.start();
        server.join();
    }

    public static <T>void addHandler (String method, String path, BiFunction<Request, Response, CompletableFuture<T>> handler) {
        switch (method) {
            case "post":
                context.addServlet(new ServletHolder(new PostRoute<>(handler)), path);
                break;
            case "put":
            case "patch":
                context.addServlet(new ServletHolder(new PutRoute<>(handler)), path);
                break;
            case "delete":
                context.addServlet(new ServletHolder(new DeleteRoute<>(handler)), path);
                break;
            case "get":
            default:
                context.addServlet(new ServletHolder(new GetRoute<>(handler)), path);
                break;
        }
    }

    public String getGreeting() {
        return "Hello World!";
    }
}
