package com.ubb.mpp.motorcyclingcontest;

import com.ubb.mpp.motorcyclingcontest.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Marius Adam
 */
public class MainGui extends Application {

    private ApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        super.init();
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
        Parent root = loader.load(getClass().getResourceAsStream("/view/LoginView.fxml"));
        ((LoginController)loader.getController()).setMainStage(stage);

        stage.setTitle("Motorcycling Contest");
        stage.setScene(new Scene(root));
        stage.setWidth(600);
        stage.setHeight(400);
        stage.show();
    }
}
