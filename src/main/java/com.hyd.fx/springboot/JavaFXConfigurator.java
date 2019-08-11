package com.hyd.fx.springboot;

import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import java.util.Map;

public class JavaFXConfigurator implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext applicationContext;

    private Class<? extends Application> applicationType;

    private String[] arguments;

    @Autowired
    private ApplicationArguments applicationArguments;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        SpringBootFXMLLoader.setApplicationContext(applicationContext);
    }

    @PostConstruct
    public void init() {
        Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(EnableJavaFX.class);

        if (!beans.isEmpty()) {
            Map.Entry<String, Object> entry = beans.entrySet().iterator().next();
            Object bean = entry.getValue();
            EnableJavaFX enableJavaFX = AnnotationUtils.findAnnotation(bean.getClass(), EnableJavaFX.class);

            if (enableJavaFX != null) {
                this.applicationType = enableJavaFX.value();
                this.arguments = applicationArguments.getSourceArgs();
            } else {
                throw new IllegalStateException(
                        "Unable to get @EnableJavaFX annotation from bean '" + entry.getKey() + "'");
            }
        }
    }

    private boolean fxAppLaunched = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!fxAppLaunched) {
            fxAppLaunched = true;
            new Thread(() -> Application.launch(applicationType, arguments)).start();
        }
    }
}
