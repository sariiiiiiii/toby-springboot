package toby.spring.helloboot._03_spring_container;

public class SimpleHelloTestService implements HelloTestService {

    @Override
    public String sayHello(String name) {
        return "practice " + name;
    }

}
