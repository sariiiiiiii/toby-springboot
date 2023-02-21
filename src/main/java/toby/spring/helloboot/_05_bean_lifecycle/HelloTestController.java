package toby.spring.helloboot._05_bean_lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class HelloTestController implements ApplicationContextAware {

    private final HelloTestService helloTestService;

    private final ApplicationContext applicationContext;

    // 조금도 스프링 스럽게 ApplicationContextAware를 구현해서 setter 메소드를 확인해보는것이 아니라
    // 생성자 시점에 주입해주기
    public HelloTestController(HelloTestService helloTestService, ApplicationContext applicationContext) {
        this.helloTestService = helloTestService;
        this.applicationContext = applicationContext;

        System.out.println(applicationContext);
    }

    @GetMapping("/hello")
    public String hello(String name) {

        return helloTestService.sayHello(Objects.requireNonNull(name));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        /**
         * 이 메소드가 호출이되면 주입받은 것을 화면에 출력을 해보자
         * 이 메소드는 언제 호출이 되냐면 스프링 컨테이너가 초기화 되는 시점에 이 작업이 일어난다
         * 서버를 띄우기만 해도 호출이 되는것을 볼수가 있다
         */

        System.out.println("applicationContext = " + applicationContext);
    }

}
