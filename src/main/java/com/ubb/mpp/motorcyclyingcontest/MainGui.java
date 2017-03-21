package com.ubb.mpp.motorcyclyingcontest;

import com.ubb.mpp.motorcyclyingcontest.config.DIConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Marius Adam
 */
public class MainGui extends Application {

    private AnnotationConfigApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        super.init();
        applicationContext = new AnnotationConfigApplicationContext(DIConfiguration.class);
    }

    @Override
    public void stop() {
        if (applicationContext != null) {
            applicationContext.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL u = getClass().getClassLoader().getResource("/view/MainView.fxml");
        InputStream fxml = new BufferedInputStream(getClass().getResourceAsStream("/view/MainView.fxml"));
        FXMLLoader loader = applicationContext.getBean(FXMLLoader.class);
        Parent root = loader.load(fxml);

        stage.setTitle("Hello World");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
