package toby.spring.helloboot._00_practice;

import java.util.Objects;

public class HelloFrontController {

    private final HelloService helloService;

    // HelloService 타입에 파라미터를 받아서 멤버변수에 저장
    // Dependancy Injection DI 적용 (생성자 이용)
    public HelloFrontController(HelloService helloService) {
        this.helloService = helloService;
    }

    public String hello(String name) {
//        SimpleHelloService helloService = new SimpleHelloService();

        // null이면 error 발생 null이 아니면 parameter 반환
        return helloService.sayHello(Objects.requireNonNull(name));
    }

    public String container(String container) {
        return "sari " + container;
    }

}
