package com.istore.gui.controllers.dashboard;

import com.istore.gui.AppLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DashboardController {

    @FXML
    private AnchorPane contentArea;

    @FXML
    private Button btnUsers;
    @FXML
    private Button btnStores;
    @FXML
    private Button btnManagement;
    @FXML
    private Button btnSettings;

    @FXML
    public void initialize() {
        loadUsersView();
    }

    @FXML
    private void loadUsersView() {
        loadView("/com/istore/views/dashboard/users-view.fxml", btnUsers);
    }

    @FXML
    private void loadStoresView() {
        loadView("/com/istore/views/dashboard/stores-view.fxml", btnStores);
    }

    @FXML
    private void loadManagementView() {
        loadView("/com/istore/views/dashboard/management-view.fxml", btnManagement);
    }

    @FXML
    private void loadSettingsView() {
        loadView("/com/istore/views/dashboard/settings-view.fxml", btnSettings);
    }

    private void loadView(String fxmlPath, Button activeButton) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(view);
            highlightActiveButton(activeButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void highlightActiveButton(Button activeButton) {
        List<Button> buttons = Arrays.asList(btnUsers, btnStores, btnManagement, btnSettings);
        buttons.forEach(button -> button.getStyleClass().removeAll("active-button"));
        activeButton.getStyleClass().add("active-button");
    }

    @FXML
    private void handleLogout() throws IOException {
        AppLauncher.showLoginView();
    }

}
