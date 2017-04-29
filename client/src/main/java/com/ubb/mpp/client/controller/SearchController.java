package com.ubb.mpp.client.controller;

import com.ubb.mpp.client.ProxyClient;
import com.ubb.mpp.model.Contestant;
import com.ubb.mpp.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Marius Adam
 */
@Component
public class SearchController implements Initializable, Updatable {
    public Pagination pagination;
    public TextField teamNameField;

    private TableView<Contestant> mainTableView;
    private ObservableList<Contestant> entityObservableList;

    private ProxyClient client;

    @Autowired
    public SearchController(ProxyClient client) {
        this.client = client;
        this.client.addWindow(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Contestant, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Contestant, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Contestant, Team> teamColumn = new TableColumn<>("Team name");
        teamColumn.setMinWidth(100);
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));

        mainTableView = new TableView<>();
        mainTableView.getColumns().addAll(idColumn, nameColumn, teamColumn);

        entityObservableList = FXCollections.observableArrayList();
        entityObservableList.addAll(client.getContestants());
        mainTableView.setItems(entityObservableList);

        pagination.setPageFactory(this::createPage);
        pagination.setPageCount(1);

        TextFields.bindAutoCompletion(
                teamNameField,
                param -> client.suggestTeamNames(param.getUserText())
        );
    }

    private Node createPage(Integer integer) {
        return new BorderPane(mainTableView);
    }

    public void onSearchButton(ActionEvent event) {
        entityObservableList.clear();
        entityObservableList.addAll(
                client.findContestants(teamNameField.getText())
        );
    }

    @Override
    public void update() {
        entityObservableList.clear();
        entityObservableList.addAll(
                client.findContestants(teamNameField.getText())
        );
    }
}