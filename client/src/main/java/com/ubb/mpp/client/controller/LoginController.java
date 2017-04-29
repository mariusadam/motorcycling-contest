package com.ubb.mpp.client.controller;

import com.ubb.mpp.client.ProxyClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Marius Adam
 */
@Component
public class LoginController extends BaseController{
    public PasswordField passwordField;
    public TextField emailField;
    public Text actionTarget;

    @Autowired
    public LoginController(ApplicationContext applicationContext, ProxyClient client) {
        super(applicationContext, client);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        emailField.setText("a@b.com");
        passwordField.setText("abcd1234");
    }

    public void handleSubmitButtonAction(ActionEvent event) throws IOException {
        client.login(emailField.getText(), passwordField.getText());

        FXMLLoader loader = getFXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("/view/MainView.fxml"));

        ((BaseController) loader.getController()).setMainStage(mainStage);
        mainStage.setScene(new Scene(root, 800, 600));
        mainStage.sizeToScene();
    }
}
