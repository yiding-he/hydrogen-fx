package com.hyd.fx.springboot;

import javafx.application.Application;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 只要加上这个就可以用 Spring Boot 来启动 JavaFX 应用，而且所有的
 * Controller 只要加上 @Component 都会自动纳入 ApplicationContext。
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JavaFXConfigurator.class)
public @interface EnableJavaFX {

    /**
     * JavaFX 应用的 Application 类
     */
    Class<? extends Application> value();
}
