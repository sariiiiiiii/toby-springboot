package toby.spring.helloboot._05_bean_lifecycle;

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

    // onRefresh() 될 때, 자기자신을 참조하는 ApplicationContext를 넣어줬는데
    // 여기는 main method도 아니고 바깥이기 때문에 ApplicationContext를 어떻게 받아야할까
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    public static void main(String[] args) {

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class); // 빈으로 등록된것을 스프링 컨테이너 활용 (this)
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class); // 빈으로 등록된것을 스프링 컨테이너 활용 (this)

                /**
                 * ApplicationContext 타입에 스프링 컨테이너 오브젝트를 넘겨주는 메소드 활용 (스프링 컨테이너 지정)
                 * dispatcherServlet.setApplicationContext(this); 를 주석처리해도
                 * DispatcherServlet 이 어떻게 스프링 컨테이너를 통해서 HelloTestController를 찾아가지고 가져와서 매핑정보도 사용을 하고
                 * 나중에 웹 요청이 오면 호출도 하고 이런 동작을 수행을 했다
                 * ApplicationContext를 주입한적이 없는데 왜 동작을 할까 ?
                 *  * 범인은 스프링 컨테이너다
                 * 우리 대신에 스프링 컨테이너가 DispatcherServlet 은 ApplicationContext가 필요하구나 라고 생각을 해서 주입을 해준것이다
                 * 이것을 이해할려면 Bean의 라이플사이클(생명주기)를 이해를 해야 한다
                 *
                 * DispatcherServlet 이 구현하고 있는 interface 중에서 ApplicationContextAware라는 interface가 있다
                 * ApplicationContextAware 을 보면 setApplicationContext(ApplicationContext applicationContext) 를 볼 수 있다
                 * ApplicationContextAware는 뭘하는 interface인지 설명을 보면 빈을 컨테이너가 등록하고 관리하는중에 컨테이너가 관리하는 오브젝트를
                 * 빈에다가 주입해주는 라이프사이클 메소드라는것을 알 수 있다
                 * 이 인터페이스를 구현한 클래스가 빈 오브젝트로 등록이 되면 (팩토리 메서드로 만들어지든, 설정파일로 만들어 지든, 컴포넌트 스캐너에 의해서 등록이 되든) 상관없이
                 * 컨테이너에 등록이 된 후에 이런종류의 인터페이스로 구현이 되면 스프링 컨테이너는 이거를 인터페이스의 setter 메소드를 이용해서 주입을 해준다
                 * 그래서 명시적으로 생성자에서 또는 setter 메소드를 통해서 ApplicationContext를 안집어넣어도 applicationContext를 가지고 있게 되는 것
                 */
//                dispatcherServlet.setApplicationContext(this);

                WebServer webServer = serverFactory.getWebServer(servletContext -> {

                    servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                            .addMapping("/*");
                });
                webServer.start();
            }
        };
        applicationContext.register(HellobootTestApplication.class);
        applicationContext.refresh();
    }

}
