package com.ubb.mpp.client;

import com.ubb.mpp.client.controller.LoginController;
import com.ubb.mpp.services.ContestServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @author Marius Adam
 */
public class MainGui extends Application {

    private ApplicationContext applicationContext;
    private ContestServer server;

    @Override
    public void init() throws Exception {
        super.init();
        applicationContext = new AnnotationConfigApplicationContext("com.ubb.mpp.client");
        server = applicationContext.getBean("proxyServer", ContestServer.class);
        System.out.println("Obtained a reference to server");
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
