package works.hop.calc.svc.handler;

import jakarta.servlet.http.HttpServletRequest;
import works.hop.calc.svc.api.Request;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsRequest implements Request {

    private final HttpServletRequest request;
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, List<String>> params = new HashMap<>();

    public JsRequest(HttpServletRequest request) {
        this.request = request;
        for (Iterator<String> iterator = request.getHeaderNames().asIterator(); iterator.hasNext(); ) {
            String header = iterator.next();
            headers.put(header, request.getHeader(header));
        }
        this.request.getParameterMap().forEach((key, values) -> {
            this.params.put(key, List.of(values));
        });
    }

    @Override
    public byte[] body() {
        try {
            int readBytes = -1;
            int lengthOfBuffer = request.getContentLength();
            InputStream input = request.getInputStream();
            byte[] buffer = new byte[lengthOfBuffer];
            ByteArrayOutputStream output = new ByteArrayOutputStream(lengthOfBuffer);
            while ((readBytes = input.read(buffer, 0, lengthOfBuffer)) != -1) {
                output.write(buffer, 0, readBytes);
            }
            return output.toByteArray();
        } catch (Exception e) {
            String error = "FATAL ERROR ii body(): " + e.getMessage();
            throw new RuntimeException(error, e);
        }
    }

    @Override
    public String method() {
        return this.request.getMethod().toLowerCase();
    }

    @Override
    public Map<String, String> headers() {
        return this.headers;
    }

    @Override
    public Map<String, List<String>> params() {
        return this.params;
    }
}
