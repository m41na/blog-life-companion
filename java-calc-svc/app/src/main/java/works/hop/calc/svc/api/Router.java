package works.hop.calc.svc.api;

import jakarta.servlet.http.HttpServlet;

public interface Router {

    void route(String path, HttpServlet route);
}
