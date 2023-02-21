package toby.spring.helloboot._01_dependency_injection;

public class HelloTestController {

    private final HelloTestService helloTestService;

    public HelloTestController(HelloTestService helloTestService) {
        this.helloTestService = helloTestService;
    }

    public String hello(String name) {
        return helloTestService.sayHello(name);
    }

}
