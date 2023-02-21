package toby.spring.helloboot._07_testcode;

import org.springframework.stereotype.Service;

@Service
public class SimpleHelloTestService implements HelloTestService {

    @Override
    public String sayHello(String name) {
        return "practice " + name;
    }

}
