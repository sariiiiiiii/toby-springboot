package toby.spring.helloboot._01_dependency_injection;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import toby.spring.helloboot._02_dispatcher_servlet.HelloTestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootTestApplication {

    public static void main(String[] args) {

        GenericApplicationContext applicationContext = new GenericApplicationContext();
        applicationContext.registerBean(HelloTestController.class);
        applicationContext.registerBean(SimpleHelloTestService.class);
        applicationContext.refresh();

        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext -> {

            servletContext.addServlet("spring-container", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                    // 현재 mapping 되어 있는 작업이 하드코딩 되어있음
                    // 파라미터로 넘어온 값도 하드코딩으로 되어있음
                    if ("/sari".equals(req.getRequestURI())) {
                        String name = req.getParameter("sari");
                        HelloTestController helloTestController = applicationContext.getBean(HelloTestController.class);
                        String ret = helloTestController.hello(name); // 바인딩 작업 필요

                        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                        resp.getWriter().println("spring " + ret);
                    }
                }

            }).addMapping("/sari");

        });

        webServer.start();

    }

}
