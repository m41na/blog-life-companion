package works.hop.calc.svc.handler;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.calc.svc.api.Request;
import works.hop.calc.svc.api.Response;

public class JsResponse implements Response {

    private final HttpServletResponse response;
    private final Gson gson = new Gson();
    private int status = 200;
    private String contentType = "application/json";

    public JsResponse(HttpServletResponse response, Request request) {
        this.response = response;
        if (request.headers().containsKey("Accept")) {
            this.contentType(request.headers().get("Accept"));
        }
    }

    @Override
    public HttpServletResponse response() {
        return this.response;
    }

    @Override
    public Response status(int code) {
        this.status = code;
        return this;
    }

    @Override
    public int status() {
        return this.status;
    }

    @Override
    public Response contentType(String type) {
        this.contentType = type;
        return this;
    }

    @Override
    public String contentType() {
        return this.contentType;
    }

    @Override
    public Gson gson() {
        return this.gson;
    }
}
