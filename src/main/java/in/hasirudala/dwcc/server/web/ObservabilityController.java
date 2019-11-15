package in.hasirudala.dwcc.server.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ObservabilityController {
    @GetMapping("observability/throwError")
    public void throwError() {
        throw new RuntimeException("Bugsnag should be notified of this if the API key is correct");
    }

    @GetMapping("observability/sayHello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("api/yolo")
    public String yolo() { return "YOLO"; }

}