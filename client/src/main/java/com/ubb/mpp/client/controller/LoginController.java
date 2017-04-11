package com.ubb.mpp.client.controller;

import com.ubb.mpp.client.MotoContestClient;
import motocontest.wsdl.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import motocontest.wsdl.LoginResponse;
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

    private MotoContestClient client;

    @Autowired
    public LoginController(ApplicationContext applicationContext, MotoContestClient client) {
        super(applicationContext);
        this.client = client;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        emailField.setText("a@b.com");
        passwordField.setText("abcd1234");
    }

    public void handleSubmitButtonAction(ActionEvent event) throws IOException {
        LoginResponse r = client.loginResponse(
                emailField.getText(),
                passwordField.getText()
        );
        if (r.getUser() == null) {
            actionTarget.setText(r.getMessage());
            return;
        }

        FXMLLoader loader = getFXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("/view/MainView.fxml"));
        User user = new User();
        user.setEmail(user.getEmail());

        ((MainController) loader.getController()).setCurrentUser(user);
        ((MainController) loader.getController()).setMainStage(mainStage);
        mainStage.setScene(new Scene(root, 800, 600));
        mainStage.sizeToScene();
    }
}
