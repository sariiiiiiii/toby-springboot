package toby.spring.helloboot._06_springboot_application;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class HelloTestController {

    private final HelloTestService helloTestService;

    private final ApplicationContext applicationContext;

    public HelloTestController(HelloTestService helloTestService, ApplicationContext applicationContext) {
        this.helloTestService = helloTestService;
        this.applicationContext = applicationContext;

        System.out.println(applicationContext);
    }

    @GetMapping("/hello")
    public String hello(String name) {

        return helloTestService.sayHello(Objects.requireNonNull(name));
    }

}
