package toby.spring.helloboot;

public class HelloFrontController {

    public String hello(String name) {
        return "hello " + name;
    }

}
