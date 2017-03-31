package com.ubb.mpp.motorcyclingcontest.controller;

import com.ubb.mpp.motorcyclingcontest.domain.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
public class MainController extends BaseController implements Initializable {
    @FXML
    public TabPane tabPane;
    private User currentUser;
    private int searchTabsCount = 0;
    private int registerTabsCount = 0;
    private int listRacesTabsCount = 0;

    @Autowired
    public MainController(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        tabPane.getTabs().clear();
        try {
            onListRacesMenuItem(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLogoutMenuItem(ActionEvent event) throws IOException {
        FXMLLoader loader = getFXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("/view/LoginView.fxml"));
        ((LoginController) loader.getController()).setMainStage(mainStage);
        mainStage.setScene(new Scene(root));
        mainStage.setWidth(600);
        mainStage.setHeight(400);
    }

    public void onCloseMenuItem(ActionEvent event) {
        Platform.exit();
    }

    public void onListRacesMenuItem(ActionEvent event) throws IOException {
        Parent root = getFXMLLoader()
                .load(getClass().getResourceAsStream("/view/Races.fxml"));
        Tab tab = new Tab("List Races " + ++listRacesTabsCount, root);
        tabPane.getTabs().add(tab);
    }

    public void onSearchMenuItem(ActionEvent event) throws IOException {
        Parent root = getFXMLLoader()
                .load(getClass().getResourceAsStream("/view/SearchView.fxml"));
        tabPane.getTabs().add(
                new Tab("Search " + ++searchTabsCount, root)
        );
    }

    public void onRegisterContestant(ActionEvent event) throws IOException {
        Parent root = getFXMLLoader()
                .load(getClass().getResourceAsStream("/view/RegisterContestantView.fxml"));
        tabPane.getTabs().add(
                new Tab("Register Contestant " + ++registerTabsCount, root)
        );
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
