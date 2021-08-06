package works.hop.calc.svc.api;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.calc.svc.handler.JsPromise;

public interface Response {

    HttpServletResponse response();

    Response status(int code);

    int status();

    Response contentType(String type);

    String contentType();

    Gson gson();

    default void send(String payload) {
        try {
            response().setStatus(status());
            response().setContentType(contentType());
            response().setStatus(HttpServletResponse.SC_OK);
            response().getWriter().println(payload);
        } catch (Exception e) {
            String error = "FATAL ERROR on send(): " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    default void json(Object payload) {
        try {
            response().setStatus(status());
            response().setContentType(contentType());
            response().setStatus(HttpServletResponse.SC_OK);
            response().getWriter().println(gson().toJson(payload));
        } catch (Exception e) {
            String error = "FATAL ERROR on json(): " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    default void error(String message) {
        try {
            response().setStatus(status());
            response().setContentType(contentType());
            response().setStatus(HttpServletResponse.SC_OK);
            response().getWriter().println(message);
        } catch (Exception e) {
            String error = "FATAL ERROR on json(): " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    default Promise onComplete() {
        return new JsPromise(this);
    }
}
