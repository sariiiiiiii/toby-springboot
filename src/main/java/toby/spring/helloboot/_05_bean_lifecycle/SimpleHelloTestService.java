package toby.spring.helloboot._05_bean_lifecycle;

import org.springframework.stereotype.Service;

@Service
public class SimpleHelloTestService implements HelloTestService {

    @Override
    public String sayHello(String name) {
        return "practice " + name;
    }

}
