package com.ubb.mpp.client.controller;

import com.ubb.mpp.client.ProxyClient;
import com.ubb.mpp.model.EngineCapacity;
import com.ubb.mpp.model.Race;
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
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Marius Adam
 */
@Component
public class RacesController implements Initializable {
    public CheckListView<EngineCapacity> capacityCheckList;
    public TextField nameField;
    public Pagination pagination;

    private ProxyClient client;
    private TableView<Race> mainTableView;
    private ObservableList<Race> entityObservableList;

    @Autowired
    public RacesController(ProxyClient client) {
        this.client = client;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<EngineCapacity> all = client.getEngineCapacities();
        ObservableList<EngineCapacity> enginesList = FXCollections.observableList(
                all
        );
        capacityCheckList.setItems(enginesList);

        TableColumn<Race, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Race, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Race, EngineCapacity> engineCapacity = new TableColumn<>("Eng. Cap.");
        engineCapacity.setMinWidth(100);
        engineCapacity.setCellValueFactory(new PropertyValueFactory<>("engineCapacity"));

        TableColumn<Race, Date> startTimeColumn = new TableColumn<>("Start time");
        startTimeColumn.setMinWidth(100);
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        mainTableView = new TableView<>();
        mainTableView.getColumns().addAll(idColumn, nameColumn, startTimeColumn, engineCapacity);

        entityObservableList = FXCollections.observableArrayList();
        entityObservableList.addAll(
                client.getRaces()
        );
        mainTableView.setItems(entityObservableList);

        pagination.setPageFactory(this::createPage);

        TextFields.bindAutoCompletion(
                nameField, param -> client
                        .suggestRaceNames(nameColumn.getText())
        );
    }

    private Node createPage(int pageIndex) {
        return new BorderPane(mainTableView);
    }

    public void onFilterRacesButton(ActionEvent event) {
        entityObservableList.clear();
        entityObservableList.addAll(
                client.findRaces(
                        nameField.getText(),
                        capacityCheckList.getCheckModel().getCheckedItems()
                )
        );
    }
}
