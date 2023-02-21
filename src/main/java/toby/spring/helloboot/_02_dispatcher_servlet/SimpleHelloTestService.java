package toby.spring.helloboot._02_dispatcher_servlet;

public class SimpleHelloTestService implements HelloTestService {

    @Override
    public String sayHello(String name) {
        return "practice " + name;
    }

}
