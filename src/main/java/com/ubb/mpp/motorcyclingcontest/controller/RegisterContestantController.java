package com.ubb.mpp.motorcyclingcontest.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Marius Adam
 */
@Component
public class RegisterContestantController implements Initializable {
    public TextField teamNameField;
    public Pagination pagination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}