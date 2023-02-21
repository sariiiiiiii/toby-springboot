package toby.spring.helloboot._00_practice;

public class SimpleHelloService implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
