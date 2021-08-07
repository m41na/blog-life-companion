package works.hop.calc.svc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import javax.script.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AppV3 {

    private final Server server = new Server();
    private final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

    public static void mainX(String[] args) throws Exception {
        ScriptEngine eng = new ScriptEngineManager().getEngineByName("graal.js");
        Bindings bindings = eng.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.put("polyglot.js.allowHostAccess", true);
        bindings.put("polyglot.js.allowHostClassLookup", (Predicate<String>) s -> true);
        bindings.put("AppV3", AppV3.class);
        Object fn = eng.eval(new InputStreamReader(Objects.requireNonNull(AppV3.class.getResourceAsStream("/server-v3.js"))));
        Invocable inv = (Invocable) eng;
        Object result = inv.invokeMethod(fn, "call", fn);
        System.out.println(result);
    }

    public static void main(String[] args) throws Exception {
        AppV3 app = new AppV3();
        app.initServer();
        app.initContextHandler();
        app.addJsHandler("/time", "handler");
        app.start();
    }

    public static void mainZ(String[] args) {
        String JS_CODE = "(function myFun(param){console.log('hello '+param);})";
        System.out.println("Hello Java!");
        try (Context context = Context.create()) {
            Value value = context.eval("js", JS_CODE);
            value.execute(args[0]);
        }
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

    public void addJsHandler(String pathSpec, String jsHandler) throws IOException {

        String literalFunction;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(AppV3.class.getResourceAsStream(String.format("/%s.js", jsHandler))))
        )) {
            literalFunction = reader.lines().collect(Collectors.joining());
        }

        register(new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                try (Context context = Context.newBuilder("js")
                        .allowHostAccess(HostAccess.ALL)
                        //allows access to all Java classes
                        .allowHostClassLookup(className -> true)
                        .build()) {
                    Value value = context.eval("js", literalFunction);
                    value.execute(req, resp);
                }
            }
        }, pathSpec);
    }

    private void initialize() throws Exception {
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
