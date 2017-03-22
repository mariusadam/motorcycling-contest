package com.ubb.mpp.motorcyclyingcontest.controller;

import com.ubb.mpp.motorcyclyingcontest.domain.User;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Marius Adam
 */
@Component
public class MainController implements Initializable{
    public Text loginMessage;
    public StackPane stackPane;
    public BorderPane borderPane;
    public TabPane tabPane;
    public MenuBar menuBar;
    public Menu admissionMenu;
    public MenuItem optionsMenuItem;
    public MenuItem candidatesMenuItem;
    public MenuItem departmentsMenuItem;
    public Menu administrationMenu;
    public MenuItem usersMenuItem;
    public MenuItem rolesMenuItem;
    public TextField emailTextField;
    public PasswordField passwordTextField;
    public VBox loginPane;

    private User currentUser;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeLogin();
        emailTextField.setText("a@b.com");
        passwordTextField.setText("abcd1234");
    }

    private void initializeLogin() {
        loginMessage.textProperty().setValue("");
        currentUser = null;
        borderPane.setVisible(false);
        loginPane.setVisible(true);
    }
//
//    @FXML
//    private void onLoginBtn_clicked(ActionEvent event) {
//        String email = emailTextField.getText();
//        String plainPasswd = passwordTextField.getText();
//        try {
//            User user = userService.getUserByEmailAndPassword(email, plainPasswd);
//            if (user == null) {
//                loginMessage.textProperty().setValue("Invalid email or password!.");
//            } else {
//                currentUser = user;
//                loginMessage.textProperty().setValue("Login success");
//                configureMenuAccess();
//                loadTabs();
//                //TODO show a default tab(maybe user details) on login
//                loginPane.setVisible(false);
//                borderPane.setVisible(true);
//                user.setLoggedIn(true);
//                user.setLastLogin(new Date());
//                userService.update(user);
//            }
//        } catch (SQLException | DuplicateEntryException | InvalidObjectException e) {
//            loginMessage.textProperty().setValue(e.getMessage());
//        }
//    }
//
//    protected void loadTabs() {
//        ArrayList<Runnable> tasks = new ArrayList<>();
//        tasks.add(() -> {
//            try {
//                getCandidatesTab();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        tasks.add(() -> {
//            try {
//                getDepartmentsTab();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        tasks.add(() -> {
//            try {
//                getOptionsTab();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        ExecutorService executor = Executors.newCachedThreadPool();
//        for (Runnable runnable : tasks) {
//            executor.execute(runnable);
//        }
//        executor.shutdown();
//        // Wait until all tabs are loaded
////        while (!executor.isTerminated()) {}
//    }
//
//    private void configureMenuAccess() {
//        boolean showAdmission = false;
//        boolean showAdministration = false;
//
//        if (acl.isAllowed(currentUser, Resource.OPTION)) {
//            showAdmission = true;
//        } else {
//            admissionMenu.getItems().remove(optionsMenuItem);
//        }
//
//        if (acl.isAllowed(currentUser, Resource.CANDIDATE)) {
//            showAdmission = true;
//        } else {
//            admissionMenu.getItems().remove(candidatesMenuItem);
//        }
//
//        if (acl.isAllowed(currentUser, Resource.DEPARTMENT)) {
//            showAdmission = true;
//        } else {
//            admissionMenu.getItems().remove(departmentsMenuItem);
//        }
//
//        if (acl.isAllowed(currentUser, Resource.USER)) {
//            showAdministration = true;
//        } else {
//            administrationMenu.getItems().remove(usersMenuItem);
//        }
//
//        if (acl.isAllowed(currentUser, Resource.ROLE)) {
//            showAdministration = true;
//        } else {
//            administrationMenu.getItems().remove(rolesMenuIteam);
//        }
//
//        if (!showAdmission) {
//            menuBar.getMenus().remove(admissionMenu);
//        }
//
//        if (!showAdministration) {
//            menuBar.getMenus().remove(administrationMenu);
//        }
//    }
//
//    @FXML
//    private void onLoginAsOtherUserMenuItem_clicked(ActionEvent event) {
//        tabsCache.clear();
//        tabPane.getTabs().clear();
//        logOut();
//        initializeLogin();
//    }
//
//    private void logOut() {
//        try {
//            currentUser.setLoggedIn(false);
//            userService.update(currentUser);
//        } catch (InvalidObjectException | DuplicateEntryException | SQLException e) {
//            DialogBox.error(e.getMessage());
//        }
//    }
//
//    @FXML
//    private void onLogoutAndExitMenuItem_clicked(ActionEvent event) {
//        logOut();
//        Platform.exit();
//    }
//
//    private Tab getCandidatesTab() throws IOException {
//        if (tabsCache.get(CANDIDATES_TAB) == null) {
//            Parent root = getFXMLLoader().load(getClass().getResourceAsStream("/view/gui/fxml/CandidateView.fxml"));
//            tabsCache.put(CANDIDATES_TAB, new Tab(CANDIDATES_TAB, root));
//        }
//
//        return tabsCache.get(CANDIDATES_TAB);
//    }
//
//    private Tab getDepartmentsTab() throws IOException {
//        if (tabsCache.get(DEPARTMENTS_TAB) == null) {
//            Parent root = getFXMLLoader().load(getClass().getResourceAsStream("/view/gui/fxml/DepartmentView.fxml"));
//            tabsCache.put(DEPARTMENTS_TAB, new Tab(DEPARTMENTS_TAB, root));
//        }
//
//        return tabsCache.get(DEPARTMENTS_TAB);
//    }
//
//    private Tab getOptionsTab() throws IOException {
//        if (tabsCache.get(OPTIONS_TAB) == null) {
//            Parent root = getFXMLLoader().load(getClass().getResourceAsStream("/view/gui/fxml/OptionView.fxml"));
//            tabsCache.put(OPTIONS_TAB, new Tab(OPTIONS_TAB, root));
//        }
//
//        return tabsCache.get(OPTIONS_TAB);
//    }
//
//    private Tab getUsersTab() throws IOException {
//        //TODO
//        Parent root = getFXMLLoader().load(getClass().getResourceAsStream("/view/gui/fxml/OptionView.fxml"));
//        return new Tab("Users", root);
//    }
//
//    private Tab getRolesTab() throws IOException {
//        //TODO
//        Parent root = getFXMLLoader().load(getClass().getResourceAsStream("/view/gui/fxml/RoleView.fxml"));
//        return new Tab("Roles", root);
//    }
//
//    private void ensureTabAdded(Tab tab) {
//        tabPane.setVisible(true);
//        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
//        int tabIndex = -1;
//        ObservableList<Tab> tabs = tabPane.getTabs();
//        for (int i = 0, len = tabs.size(); i < len; i++) {
//            if (tabs.get(i).getText().equals(tab.getText())) {
//                tabIndex = i;
//            }
//        }
//        if (tabIndex == -1) {
//            tab.closableProperty().setValue(true);
//            tabs.add(tab);
//        } else {
//            tabs.set(tabIndex, tab);
//        }
//    }
//
//    @FXML
//    void onManageOptionsMenuItem_clicked(ActionEvent event) throws IOException {
//        ensureTabAdded(getOptionsTab());
//    }
//
//    @FXML
//    void onManageCandidatesMenuItem_clicked(ActionEvent event) throws IOException {
//        ensureTabAdded(getCandidatesTab());
//    }
//
//    @FXML
//    void onManageDepartmentsMenuItem_clicked(ActionEvent event) throws IOException {
//        ensureTabAdded(getDepartmentsTab());
//    }
//
//    private FXMLLoader getFXMLLoader() {
//        return weldContainer.instance().select(FXMLLoader.class).get();
//    }
}
