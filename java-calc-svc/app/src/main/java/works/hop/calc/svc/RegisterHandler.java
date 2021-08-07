package works.hop.calc.svc;

import jakarta.servlet.http.HttpServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

public interface RegisterHandler {

    void register(ServletContextHandler context, HttpServlet servlet, String pactSpec);
}
