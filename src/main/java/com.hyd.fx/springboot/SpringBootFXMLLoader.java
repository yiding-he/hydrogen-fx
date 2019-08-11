package com.hyd.fx.springboot;

import com.hyd.fx.Fxml;
import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;

public class SpringBootFXMLLoader extends FXMLLoader {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringBootFXMLLoader.applicationContext = applicationContext;
        Fxml.setUsingSpringBoot(true);
    }

    public SpringBootFXMLLoader() {
        setControllerFactory(applicationContext::getBean);
    }
}
