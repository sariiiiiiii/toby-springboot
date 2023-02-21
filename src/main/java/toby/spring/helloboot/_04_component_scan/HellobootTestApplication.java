package toby.spring.helloboot._04_component_scan;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @ComponentScan
 * 컴포넌트가 붙은 클래스를 찾아서 빈으로 등록을 해줘라고 컨테이너한테 전달
 * applicationContext.register에 등록된 클래스에 @ComponentScan 어노테이션이 붙어있으면
 * 이 클라스가 있는 패키지부터 시작해서 그 하위 패키지에 @Component라는 어노테이션이 붙은 모든 클래스를 빈으로 등록을 해버린다
 *
 * 장점 : 새로운 빈을 만들어서 추가할 때, 매번 구성정보를 어디다가 등록해줄 필요 없이 @Component 어노테이션만 붙여주면 됨
 * 단점 : 항상 좋은것만은 아니다, 만약에 나중에 정말 빈으로 등록되는 클래스가 많아 지게 되면은 application을 실행했을 때 정확하게 어떤것들이 등록되는 것인가를 찾을려면 번거로울 수가 있음
 */

@Configuration
@ComponentScan
public class HellobootTestApplication {

    /**
     * 간결하게 Bean을 등록하는 방법
     * 스프링 컨테이너에 있는 컴포넌트 스캐너 (component-scanner)
     * @Component라는 어노테이션이 붙은 모든 클래스를 찾아서 빈으로 등록을 해줌
     * HelloTestController 클래스와 SimpleHelloTestService에다가 @Component 추가
     */

//    @Bean
//    public HelloTestController helloTestController(HelloTestService helloTestService) {
//        return new HelloTestController(helloTestService);
//    }
//    @Bean
//    public HelloTestService helloTestService() {
//        return new SimpleHelloTestService();
//    }

    public static void main(String[] args) {

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                WebServer webServer = serverFactory.getWebServer(servletContext -> {

                    servletContext.addServlet("dispatcherServlet",
                            new DispatcherServlet(this)
                    ).addMapping("/*");
                });
                webServer.start();
            }
        };
        applicationContext.register(HellobootTestApplication.class);
        applicationContext.refresh();
    }

}
