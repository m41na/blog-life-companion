package works.hop.calc.svc.api;

import java.util.concurrent.CompletableFuture;

public interface Promise {

    <T> void resolve(CompletableFuture<T> future);

    void reject(Throwable throwable);

    boolean done();
}
