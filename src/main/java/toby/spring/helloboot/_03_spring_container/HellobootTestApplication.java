package toby.spring.helloboot._03_spring_container;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

// configuration 정보 즉, 구성정보를 가지고 있는 클래스다, 스프링컨테이너가 보고 이안에 @Bean 어노테이션이 붙은 팩토리 메소드가 있겠구나 그럼 Bean object를 만들면 되겠네
@Configuration
public class HellobootTestApplication {

    // HelloTestController 타입에 팩토리 메소드를 만들어서 spring container가 쓰겠다
    // helloTestController는 생성되는 시점에 HelloTestService 타입에 오브젝트를 주입을 해줘야된다
    // 그럼 이거는 어떻게 가져올 것인가 몇가지 방법이 있긴함
    // 어차피 이 팩토리메서드는 스프링컨테이너가 호출을 할텐데 스프링 컨테이너한테 자바코드로 helloTestController를 만들텐데
    // 그에 필요한 오브젝트를 파라미터로 넘겨줘라고 하면 됨
    @Bean
    public HelloTestController helloTestController(HelloTestService helloTestService) {
        return new HelloTestController(helloTestService);
    }

    // HelloTestService 타입에 팩토리 메소드를 만들어서 spring container가 쓰겠다
    @Bean
    public HelloTestService helloTestService() {
        return new SimpleHelloTestService();
    }

    public static void main(String[] args) {

        // 기존의 사용하던 GenericWebApplicationContext는 자바코드로 만든 configuration 정보를 읽을수가 없다
//        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext() {
        // AnnotationConfigWebApplicationContext는 어노테이션이 붙은 자바 config 코드를 이용해서 구성정보를 불러오는 ApplicationContext
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh(); // GenericWebApplicationContext에서도 onRefresh 훅메소드를 확장해서 추가적인 작업을 하기 때문에 생략하면 안됨

                // onRefresh()가 되는 시점에 servletContainer가 초기화되는 작업
                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                WebServer webServer = serverFactory.getWebServer(servletContext -> {

                    servletContext.addServlet("dispatcherServlet",
                            // applicationContext 변수를 servletContainer 초기화할 때 사용했는데
                            // 지금은 확장하고자하는 클래스내부에서 applicationContext를 참조하게 해야됨
                            new DispatcherServlet(this)
//                            new DispatcherServlet(applicationContext)
                    ).addMapping("/*");
                });
                webServer.start();
            }
        };
//        applicationContext.registerBean(HelloTestController.class);
//        applicationContext.registerBean(SimpleHelloTestService.class);
        applicationContext.register(HellobootTestApplication.class); // 자바코드로 구성된 클래스를 등록, 여기서 출발해서 빈 오브젝트를 등록해줘
        applicationContext.refresh(); // spring container의 초기화 작업은 refresh() 메소드에서 일어난다 (템플릿 메소드로 만들어져있음)
    }

}
