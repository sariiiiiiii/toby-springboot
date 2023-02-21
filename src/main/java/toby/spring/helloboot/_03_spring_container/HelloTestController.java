package toby.spring.helloboot._03_spring_container;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@RequestMapping
public class HelloTestController {

    private final HelloTestService helloTestService;

    public HelloTestController(HelloTestService helloTestService) {
        this.helloTestService = helloTestService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(String name) {

        return helloTestService.sayHello(Objects.requireNonNull(name));
    }

}
