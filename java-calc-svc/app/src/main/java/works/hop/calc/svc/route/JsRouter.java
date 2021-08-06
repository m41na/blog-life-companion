package works.hop.calc.svc.route;

import jakarta.servlet.http.HttpServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import works.hop.calc.svc.api.Router;

public class JsRouter implements Router {

    private final ServletContextHandler context;

    public JsRouter(ServletContextHandler context) {
        this.context = context;
    }

    @Override
    public void route(String path, HttpServlet route) {
        this.context.addServlet(new ServletHolder(route), path);
    }
}