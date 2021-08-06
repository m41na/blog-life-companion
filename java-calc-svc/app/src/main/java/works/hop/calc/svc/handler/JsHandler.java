package works.hop.calc.svc.handler;

import works.hop.calc.svc.api.Handler;
import works.hop.calc.svc.api.Promise;
import works.hop.calc.svc.api.Request;
import works.hop.calc.svc.api.Response;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class JsHandler implements Handler {

    private final Request request;
    private final Response response;

    public JsHandler(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public Response response() {
        return this.response;
    }

    @Override
    public <T> void handle(BiFunction<Request, Response, CompletableFuture<T>> handler, Promise next) {
        CompletableFuture.runAsync(() -> next.resolve(handler.apply(request, response)))
        .exceptionally(th -> {
            next.reject(th);
            return null;
        });
    }
}
