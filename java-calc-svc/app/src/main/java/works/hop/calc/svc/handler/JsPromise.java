package works.hop.calc.svc.handler;

import works.hop.calc.svc.api.Promise;
import works.hop.calc.svc.api.Response;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class JsPromise implements Promise {

    AtomicBoolean completed = new AtomicBoolean(false);
    private final Response resp;

    public JsPromise(Response resp) {
        this.resp = resp;
    }

    @Override
    public <T> void resolve(CompletableFuture<T> future) {
        if (!done()) {
            switch (resp.contentType()) {
                case "text/plain":
                    try {
                        resp.send((String) future.join());
                    } catch (Throwable th) {
                        resp.error(th.getMessage());
                    } finally {
                        completed.set(true);
                    }
                    break;
                case "application/json":
                default:
                    try {
                        resp.json(future.join());
                    } catch (Throwable th) {
                        resp.error(th.getMessage());
                    } finally {
                        completed.set(true);
                    }
            }
        } else {
            String error = "FATAL ERROR on resolve(): The promise is already completed";
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    @Override
    public void reject(Throwable throwable) {
        resp.status(500).error(throwable.getMessage());
        completed.set(true);
    }

    @Override
    public boolean done() {
        return completed.get();
    }
}
