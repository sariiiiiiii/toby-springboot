package toby.spring.helloboot._07_testcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan
public class HellobootTestApplication {

    @Bean
    public ServletWebServerFactory serverFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    public static void main(String[] args) {
        /**
         * 우리가 만들어놓은 MySpringApplication이 아닌 프로젝트 생성시 기본적으로 제공하는 SpringApplication.run()을 한다고 해서
         * 위에 팩토리 메서드 Bean으로 등록해놓은 serverFactory 메소드와 dispatcherServlet 메소드는 아직 지우면 안된다 (빈을 찾을 수 없다면서 application 실행 안됨)
         * SpringApplication.run() 에서도 이걸 가져다가 사용하기 때문
         * 근데 처음 프로젝트를 만들었을 때 예제에서는 저런게 다 없지 않았나
         * 강의 뒤에서 나온다
         */
        SpringApplication.run(HellobootTestApplication.class, args);
    }

}
