package works.hop.calcapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/calc")
public class CalcController {

    @GetMapping("/plus/{left}/{right}")
    public int plus(@PathVariable int left, @PathVariable int right){
        return left + right;
    }

    @GetMapping("/minus/{left}/{right}")
    public int minus(@PathVariable int left, @PathVariable int right){
        return left - right;
    }

    @GetMapping("/times/{left}/{right}")
    public int times(@PathVariable int left, @PathVariable int right){
        return left * right;
    }

    @GetMapping("/divide/{left}/{right}")
    public float divide(@PathVariable float left, @PathVariable float right){
        return left / right;
    }
}
