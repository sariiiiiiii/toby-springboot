package toby.spring.helloboot._02_dispatcher_servlet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @RequestMapping을 넣은 이유는
 * DispatchServlet이 매핑 정보를 만들때에 기본적으로 클래스레벨에 있는 정보를 먼저 참고를 하고
 * 메소드 레벨에 붙어있는 정보를 거기다가 추가를 한다
 *   - @RequestMapping("/myapp")에다가 메소드레벨에 @GetMapping("/hello")를 해주면 /myapp/hello가 되는것이다
 *   - 뒤에 정보는 안넣어도됨
 */
@RequestMapping
public class HelloTestController {

    private final HelloTestService helloTestService;

    public HelloTestController(HelloTestService helloTestService) {
        this.helloTestService = helloTestService;
    }

    /**
     * @GetMapping 어노테이션을 넣으면 dispatcherServlet은 무엇을 할까?
     * dispatcherServlet은 servletContainer인 ApplicationContext를 생성자로 받았다
     * 그걸 가지고 dispatcherServlet이 무슨 작업을 해놓느냐면
     * Bean을 다 뒤져서 이중에서 웹요청을 처리할 수 있는 Mapping정보를 가지고 있는 클래스를 찾는다
     * 클래스에 @GetMapping이나 @RequestMapping같은것이 붙어있으면 이거는 웹 요청을 처리할수 있도록 만들어진 웹 컨트롤러구나라고 판단을 하고
     * 그 안에 있는 요청정보들을 추출한다
     * /hello에다가 GET이면 hello메소드를 추출해서 이걸 사용할 mapping table를 만들어 놓는다
     * 그리고 그 이후에 웹 요청이 들어오면 그걸 참고해서 이걸 담당할 bean object와 메소드를 확인한다
     */
    @GetMapping("/hello") // GET method로 들어온 /hello url이라는것을 알 수 있음
    @ResponseBody
    public String hello(String name) {

        /**
         * Controller 메소드가 String을 return 하면 이거를 DispatcherServlet이 처리하는 여러가지 방법이 있다
         * 그중에서 가장 기본은 String으로 return된 문자열을 보고 이거는 view라고 불리는 일종의 HTML 템플릿을 찾아서 view를 리턴해줘라라는게 String으로 return되는 가장 기본방식
         *   - HelloSpring을 return하기되면 HelloSpring으로 된 이름의 view를 체크한다
         *
         */

        return helloTestService.sayHello(Objects.requireNonNull(name));
    }

}
