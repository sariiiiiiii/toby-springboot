package toby.spring.helloboot._06_springboot_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
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

    /**
     * 로직을 리팩토링 기능을 이용해 run() 메소드로 빼기
     * run() 메소드의 파라미터를 Class<?> applicationClass로 설정
     * 호출하는 쪽에 클래스 지정
     *
     * 파라미터로 applicationClass을 명시해주고 나중에 이 메인메소드가 있는 클래스가 달라지더라도
     * 클래스의 이름만 run() <=에 넘겨주면 재사용 가능
     *
     * run()에 파라미터로 들어갈 메소드의 중요한 점은 ?
     * @Configuration이 붙은 클래스여야 하고 @ComponentScan과 팩토리 메서드를 가지고 스프링 컨테이너에게 Application 구성을 어떻게 할것인가라를 알려주는 정보를
     * 가지고 있는 클래스여야 한다
     *
     * run() 메소드는 재사용한다고 위에서 말했다
     * 정의되어 있는 HellobootTestApplication이 아니라 다른 어떤 메인이 되는 클래스에서도 ServletContainer를 코드에서 자동으로 띄어주면서
     * 스프링컨테이너에서 필요한 정보를 받아서 기본적인 기능을 수행할 수 있도록 만드는 스프링 컨테이너 준비작업들을 해주는데 이 메소드를 재사용 할 수 있다
     * 이 재사용 가능한 run() 메소드를 MySpringApplication 클래스에 빼놓도록 하자
     */

    public static void main(String[] args) {
        // HellobootTestApplication.class 넘여주면 조금 아쉬운게 커맨드라인에서도 실행이 가능한 메인 메소드니까 커맨더라인의 argument가 넘어올 수 있으니
        // 사용할 수 있으니까 두번째 파라미터로 넘겨주자
        // 다음 단락부터는 SpringApplication.run()으로 진행, MySpringApplication은 삭제

        /**
         * 우리가 만들어놓은 MySpringApplication이 아닌 SpringApplication.run()을 한다고 해서
         * 위에 Bean으로 등록해놓은 serverFactory 메소드와 dispatcherServlet 메소드는 아직 지우면 안된다 (빈을 찾을 수 없다면 application 실행 안됨)
         * SpringApplication.run() 에서도 이걸 가져다가 사용하기 때문
         * 근데 처음 프로젝트를 만들었을 때 예제에서는 저런게 다 없지 않았나
         * 강의 뒤에서 나온다
         *
         */

        MySpringApplication.run(HellobootTestApplication.class, args);
//        SpringApplication.run(HellobootTestApplication.class, args);
    }

}
