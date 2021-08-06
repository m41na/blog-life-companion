package works.hop.calc.svc.api;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public interface Handler {

    Response response();

    default <T>void handle(BiFunction<Request, Response, CompletableFuture<T>> handler) {
        handle(handler, response().onComplete());
    }

    <T>void handle(BiFunction<Request, Response, CompletableFuture<T>> handler, Promise next);
}
