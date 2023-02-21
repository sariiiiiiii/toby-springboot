package toby.spring.helloboot._05_bean_lifecycle;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface MyComponent {

    /**
     * 어노테이션을 만들때 꼭 필요한 메타 어노테이션 2가지
     * @Retention() => 이 어노테이션이 언제까지 살아있을 것인가, 어디까지 유지될 것인가
     * @Target() => 어노테이션을 지정할 타겟
     */

}
