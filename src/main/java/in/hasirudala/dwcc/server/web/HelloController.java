package in.hasirudala.dwcc.server.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HelloController {
    @GetMapping
    public String helloGradle() {
        return "Hello World!";
    }
}