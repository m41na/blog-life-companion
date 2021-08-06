package works.hop.calc.svc.route;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.calc.svc.api.Request;
import works.hop.calc.svc.api.Response;
import works.hop.calc.svc.handler.JsHandler;
import works.hop.calc.svc.handler.JsRequest;
import works.hop.calc.svc.handler.JsResponse;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class GetRoute<T> extends HttpServlet {

    private final BiFunction<Request, Response, CompletableFuture<T>> handler;

    public GetRoute(BiFunction<Request, Response, CompletableFuture<T>> handler) {
        this.handler = handler;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Request request = new JsRequest(req);
        Response response = new JsResponse(resp, request);
        new JsHandler(request, response).handle(handler);
    }
}
