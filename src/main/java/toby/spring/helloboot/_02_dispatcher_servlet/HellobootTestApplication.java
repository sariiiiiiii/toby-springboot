package toby.spring.helloboot._02_dispatcher_servlet;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class HellobootTestApplication {

    public static void main(String[] args) {

//        GenericApplicationContext applicationContext = new GenericApplicationContext();

        // DispatcherServlet에 넘겨줄때는 GenericWebApplication을 전달해줘야됨
        GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
        applicationContext.registerBean(HelloTestController.class);
        applicationContext.registerBean(SimpleHelloTestService.class);
        applicationContext.refresh();

        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> {

            servletContext.addServlet("dispatcherServlet",
                    // spring이 처음 나왔을 때부터 있었던 front controller의 많은 기능을 수행해주는 servlet class 활용
                    // 앞에서 만들었던 GenericWebApplication 전달 (GenericApplication 아님)
                    // DispatcherServlet에 applicationContext를 넘겨서 DispatcherServlet이 mapping을 하다가
                    // 작업을 위임할 요청을 Dispatch할 오브젝트를 찾아야하는데 그때사용할 servletContainer를 전달
                    new DispatcherServlet(applicationContext)
                    ).addMapping("/*");

        });

        webServer.start();

    }

}
