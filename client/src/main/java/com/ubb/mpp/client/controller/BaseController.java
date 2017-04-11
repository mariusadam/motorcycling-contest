package com.ubb.mpp.client.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Marius Adam
 */
public abstract class BaseController implements Initializable {
    protected ApplicationContext applicationContext;
    protected Stage mainStage;

    public BaseController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    protected FXMLLoader getFXMLLoader() {
        return applicationContext.getBean(FXMLLoader.class);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
