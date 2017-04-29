package com.ubb.mpp.client.controller;

import com.ubb.mpp.client.ProxyClient;
import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.model.Race;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Marius Adam
 */
@Component
public class RegisterContestantController implements Initializable {
    public Pagination pagination;
    public TextField nameField;
    public TextField contestantName;
    public TextField contestantTeam;
    public TextField teamName;
    public CheckListView<Race> contestantRaces;

    private ObservableList<Race> raceObservableList;
    private TableView mainTableView;
    private ObservableList<Contestant> entityObservableList;
    private ProxyClient client;

    @Autowired
    public RegisterContestantController(ProxyClient client) {
        this.client = client;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFields.bindAutoCompletion(
                contestantTeam,
                param -> client.suggestTeamNames(param.getUserText())
        );

        raceObservableList = FXCollections.observableArrayList(
                client.getRaces()
        );
        contestantRaces.setItems(raceObservableList);
    }

    public void onRegisterContestantButton(ActionEvent actionEvent) {
        client.registerContestant(
                contestantName.getText(),
                contestantTeam.getText(),
                new ArrayList<Race>(contestantRaces.getCheckModel().getCheckedItems())
        );
    }

    public void onRegisterTeamButton(ActionEvent actionEvent) {

    }
}