package toby.spring.helloboot;

import java.util.Objects;

public class HelloFrontController {

    public String hello(String name) {
        SimpleHelloService helloService = new SimpleHelloService();

        // null이면 error 발생 null이 아니면 parameter 반환
        return helloService.sayHello(Objects.requireNonNull(name));
    }

    public String container(String container) {
        return "sari " + container;
    }

}
