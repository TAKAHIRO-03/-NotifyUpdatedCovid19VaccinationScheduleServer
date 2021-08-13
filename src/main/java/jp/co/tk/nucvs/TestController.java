package jp.co.tk.nucvs;

import org.apache.commons.logging.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.tk.nucvs.core.log.Logger;

@RestController
public class TestController {
    
    @Logger
    private static Log log;

    @GetMapping("/hello")
    public String helloWorld() {
        log.info("GET /hello");
        return "Hello world!";
    }
    
}
