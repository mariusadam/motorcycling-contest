package com.ubb.mpp.motorcyclingcontest.controller;

import com.ubb.mpp.model.User;
import com.ubb.mpp.motorcyclingcontest.repository.RepositoryException;
import com.ubb.mpp.services.crud.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    private UserService userService;

    @Autowired
    public LoginController(ApplicationContext applicationContext, UserService userService) {
        super(applicationContext);
        this.userService = userService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        emailField.setText("a@b.com");
        passwordField.setText("abcd1234");
    }

    public void handleSubmitButtonAction(ActionEvent event) throws IOException {
        try {
            User u = userService.getUserByEmailAndPassword(
                    emailField.getText(),
                    passwordField.getText()
            );
            if (u == null) {
                actionTarget.setText("Invalid email or password.");
                return;
            }

            FXMLLoader loader = getFXMLLoader();
            Parent root = loader.load(getClass().getResourceAsStream("/view/MainView.fxml"));
            ((MainController) loader.getController()).setCurrentUser(u);
            ((MainController) loader.getController()).setMainStage(mainStage);
            mainStage.setScene(new Scene(root, 800, 600));
            mainStage.sizeToScene();
        } catch (RepositoryException e) {
            actionTarget.setText(e.getMessage());
        }
    }
}
