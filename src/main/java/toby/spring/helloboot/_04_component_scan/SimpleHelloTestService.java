package toby.spring.helloboot._04_component_scan;

import org.springframework.stereotype.Component;

@Component
public class SimpleHelloTestService implements HelloTestService {

    @Override
    public String sayHello(String name) {
        return "practice " + name;
    }

}
