package toby.spring.helloboot._04_component_scan;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@RequestMapping
@MyComponent // spring container에 들어가는 component야 라고 선언, 메타 어노테이션 활용 (커스텀 어노테이션)
public class HelloTestController {

    /**
     * @Component라는 어노테이션은 재밌는 특징을 하나 가지고 있다
     * 이 어노테이션을 직접 빈 클래스로 사용할 클래스에 붙여도 되지만 이 어노테이션을 메타 어노테이션으로 가지고 있는 어노테이션을 붙여도
     * 컴포넌트라는 어노테이션을 붙인것과 동일한 효과를 볼 수 있음
     * 메타 어노테이션이란 ? 어노테이션위에 붙은 어노테이션
     * 보통 어노테이션은 클래스나 메소드앞에 붙는다 근데 어노테이션도 자바코드로 만들어진것이니까 어노테이션 위에다가 또 어노테이션을 붙일 수 있다
     * 어노테이션을 직접 만들어 보자
     */

    /**
     * 근데 굳이 @Component를 쓰면 되는데 @MyComponent라는 것을 만들어서 사용을 할까 ?
     * 스테레오타입의 즉, 기본타입의 어노테이션을 쓰게 되면 웹 계층의 무슨 작업을 하는 컴포넌트인지 명시적으로 알수가 있다
     * 우리는 스프링이 제공하는 스테레오타입의 어노테이션을 사용하고 있었다
     * Controller 계층의 @Controller 비즈니스 로직을 담당하는 @Service를 사용하고 있었다
     */

    /**
     * @Controller와 @RestController의 좋은점은
     * 클래스 레벨에 어노테이션으로 @RequestMapping을 안적어줘도 DispatcherServlet이 이 안에 매핑정보가 담겨있구나라고 판단을 해서 메소드를 다 뒤져준다
     */

    private final HelloTestService helloTestService;

    public HelloTestController(HelloTestService helloTestService) {
        this.helloTestService = helloTestService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(String name) {

        return helloTestService.sayHello(Objects.requireNonNull(name));
    }

}
