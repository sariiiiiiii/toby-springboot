package toby.spring.helloboot._01_dependency_injection;

public class SimpleHelloTestService implements HelloTestService {

    @Override
    public String sayHello(String name) {
        return "practice " + name;
    }

}
