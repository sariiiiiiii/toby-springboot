package toby.spring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@SpringBootApplication
public class HellobootApplication {

	/**
	 * @SpringbootApplication과 SpringApplication.run()을 주석처리하고 시작되니 일반 메인메소드와 별 다를것이 없다
	 * 톰캣이 띄어지는것도 아니고 8080 포트에 요청을 보내도 서버가 존재하지 않다고 에러가 뜰 것이다
	 */

	public static void main(String[] args) {
		//		SpringApplication.run(HellobootApplication.class, args);

		/**
		 * spring container
		 * spring container를 대표하는 interface -> application context (application context가 결국 spring container를 말하는 것)
		 *   - 이 안에 빈이 어떤것이 들어갈 것인가, 리소스에 접근하는 방법, 내부에 이벤트를 전달하고 구독하는 방법
		 *   - spring container는 object를 직접 만들어서 넣어주는것도 가능하긴한데 일반적으로 어떤 클래스를 이용해서 Bean 오브젝트를 생성할 것인가, 메타정보를 넣어주는 방식
		 *
		 * new GenetericApplicationContext() -> 코드에 의해서 손쉽게 쓸수있게 만들어진 spring container
		 */

		GenericApplicationContext applicationContext = new GenericApplicationContext();
		applicationContext.registerBean(HelloFrontController.class); // spring container가 어떤 클래스로 Bean을 만들것인가
		applicationContext.refresh(); // 처음 자기가 가지고 있는 구성정보(spring container)를 초기화 작업, application context가 빈 오브젝트를 만들어줌


		/**
		 * springboot에서 내장되어있는 tomcat을 쓰기위한 도구
		 * ServletWebServerFactory.getWebserver();를 하면 Webserver를 반환받는것을 볼 수 있는데
		 * springboot에서 추상화하여 tomcat, jetty 등등 특정서버에 종속되지 않게 하기 위해 추상적으로 만들어놓음
		 *
		 * webServer.start()로 실행시키면 404 error가 뜨는것을 볼 수 있다 즉, springboot application을 실행시켰을 때처럼 404가 뜨는것을 보니
		 * servlet container가 실행되는 것을 볼 수 있음
		 */
		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		WebServer webServer = serverFactory.getWebServer(servletContext -> { // servlet container를 만드는 생섬함수 (Webserver 생성)

			/**
			 * servlet context에 servlet 등록
			 */
			servletContext.addServlet("hello", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					/**
					 * 요청
					 */
					// name이란 parameter를 받았을 때 사용
					String name = req.getParameter("name");

					/**
					 * 응답
					 */
					// /hello로 요청이 들어왔을 때 응답 실행
					// 웹 응답의 3가지 요소
//					resp.setStatus(200); // 상태코드
					resp.setStatus(HttpStatus.OK.value()); // 상태코드
//					resp.setHeader("Content-Type", "text/plain"); // header content type
					resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE); // header content type
					resp.getWriter().println("Hello " + name); // body
				}
			}).addMapping("/hello"); // /hello로 들어오는 url이 있으면 이 servlet으로 mapping이 되어서 익명클래스에 object를 처리를 하겠다

			/**
			 * 프론트 컨트롤러로 전환
			 * 실질적인 웹 애플리케이션 로직을 담담하는 부분은 다른 오브젝트한테 위임을 해야됨 (원래 프론트 컨트롤러가 그렇게 진행됨)
			 * 프론트컨트롤러에서 로직을 위임해보자
			 */
			HelloFrontController helloFrontController = new HelloFrontController();

			servletContext.addServlet("front-controller", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					// 인증, 보안, 다국어, 공통 기능
					// servlet container의 mapping 기능을 front controller가 이제 해야됨
					if (req.getRequestURI().equals("/sari") && req.getMethod().equals(HttpMethod.GET.name())) { // GET MAPPING을 위해 req.getMethod()를 꺼내와서 http method 비교
						String name = req.getParameter("name");

						resp.setStatus(HttpStatus.OK.value()); // 200일시 생략 가능
						resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println("sari " + name);
					} else if (req.getRequestURI().equals("/user")) {
						// 프론트 컨트롤러에서 수행로직 위임하기
						String user = req.getParameter("user"); // web 요청을 알고 직접 엑세스하는 프론트 컨트롤러같은 코드에서 이걸 처리하는 object에게 평범한 타입으로 바인딩해서 넘겨주는 작업 = 바인딩
						String body = helloFrontController.hello(user); // 로직을 위임해서 받은 return 값

						resp.setStatus(HttpStatus.OK.value()); // 200일시 생략 가능
						resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println(body);
					} else {
						resp.setStatus(HttpStatus.NOT_FOUND.value());
					}

				}
			}).addMapping("/user"); // 슬래시 밑으로 들어오는 모든 요청을 이 servlet이 처리하겠다라고 servlet container에게 등록 (Front Controller의 일을 하겠다)

			/**
			 * spring container
			 */
			servletContext.addServlet("spring-container", new HttpServlet() {
				@Override
				protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
					if (req.getRequestURI().equals("container")) {
						String name = req.getParameter("container");

						// 모든 Bean에는 이름이나 클래스 타입을 사용할 수 있음
						HelloFrontController helloFrontController = applicationContext.getBean(HelloFrontController.class); // type이 뒤에서 .class로 지정해놓은 타입
						String ret = helloFrontController.container(name);

						resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
						resp.getWriter().println("container " + ret);
					}
				};
			}).addMapping("/*");

		});

		webServer.start(); // tomcat servlet container 실행

	}

}
