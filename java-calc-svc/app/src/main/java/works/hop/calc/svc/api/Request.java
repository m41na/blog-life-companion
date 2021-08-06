package works.hop.calc.svc.api;

import java.util.List;
import java.util.Map;

public interface Request {

    byte[] body();

    String method();

    Map<String, String> headers();

    Map<String, List<String>> params();

    default <T>T param(String key){
        if(params().containsKey(key)){
            return (T) params().get(key);
        }
        return null;
    }
}
