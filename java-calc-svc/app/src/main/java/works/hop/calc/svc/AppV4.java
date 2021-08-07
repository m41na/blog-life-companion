package works.hop.calc.svc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.script.*;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class AppV4 {

    private final Server server = new Server();
    private final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    private final ScriptEngine eng = new ScriptEngineManager().getEngineByName("graal.js");

    public AppV4() {
        Bindings bindings = eng.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.put("polyglot.js.allowHostAccess", true);
        bindings.put("polyglot.js.allowHostClassLookup", (Predicate<String>) s -> true);
    }

    public void initServer() {
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(8080);
        http.setIdleTimeout(30000);
        server.addConnector(http);
    }

    public void initContextHandler() {
        context.setContextPath("/");
        server.setHandler(context);
    }

    private void register(HttpServlet servlet, String pathSpec) {
        context.addServlet(new ServletHolder(servlet), pathSpec);
    }

    public void addHandler(String pathSpec, BiConsumer<HttpServletRequest, HttpServletResponse> handler) {
        register(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                handler.accept(req, resp);
            }
        }, pathSpec);
    }

    public void start() throws Exception {
        server.start();
        server.join();
    }

    public void handle(String pathSpec, BiConsumer<HttpServletRequest, HttpServletResponse> handler) {

        register(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//                eng.put("handler", handler); //this produces an error - cannot be passed from one context to another.
                Invocable inv = (Invocable) eng;
                try {
                    inv.invokeMethod(handler, "call", req, resp);
                } catch (ScriptException | NoSuchMethodException e) {
                    e.printStackTrace();
                    throw new ServletException(e);
                }
            }
        }, pathSpec);
    }
}
