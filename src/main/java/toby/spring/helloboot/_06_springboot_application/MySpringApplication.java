package toby.spring.helloboot._06_springboot_application;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringApplication {

    public static void run(Class<?> applicationClass, String... args) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);

                WebServer webServer = serverFactory.getWebServer(servletContext -> {

                    servletContext.addServlet("dispatcherServlet", dispatcherServlet)
                            .addMapping("/*");
                });
                webServer.start();
            }
        };

        /**
         * 이 메소드를 재사용 할려면 매번 달라지는 정보를 파라미터로 넘겨주는것이 필요
         * 그것이 뭐냐면 이 메인메소드가 있는 클래스 이름이다
         * 메인메소드가 있는 HellobootTestApplication.class라고 넣었는데
         * 이거를 applicationClass라는 이름의 파라미터를 받아서 사용하도록 이 메소드를 만들어 보자
         * run() 메소드의 파라미터를 Class<?> applicationClass로 설정
         */

        applicationContext.register(applicationClass);
        applicationContext.refresh();
    }

}
