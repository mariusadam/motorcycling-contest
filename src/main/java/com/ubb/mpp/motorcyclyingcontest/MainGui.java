package com.ubb.mpp.motorcyclyingcontest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Marius Adam
 */
public class MainGui extends Application {

    private ApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        super.init();
        String basePackage = getClass().getPackage().getName();
        System.out.print(basePackage);
        applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
    }

    @Override
    public void stop() {
        System.out.println("Application shutdown");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = applicationContext.getBean(FXMLLoader.class);
        Parent root = loader.load(getClass().getResourceAsStream("/view/MainView.fxml"));

        stage.setTitle("Hello World");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
