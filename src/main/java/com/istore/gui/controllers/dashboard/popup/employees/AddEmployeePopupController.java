package com.istore.gui.controllers.dashboard.popup.employees;

import com.istore.Application;
import com.istore.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.stream.Collectors;

public class AddEmployeePopupController {


    @FXML private ComboBox<String> employeeComboBox;

    @FXML private Text errorText;
    @FXML private HBox errorBox;

    private List<User> availableUsersList;
    private int storeId;

    /**
     * Initialise les utilisateurs via le store
     */
    public void setStoreId(int storeId) {
        this.storeId = storeId;
        loadAvailableUsers();
    }

    /**
     * Charge les utilisateurs disponibles
     */
    private void loadAvailableUsers() {
        try {
            List<User> allUsers = Application.getUserService().listAllUsers();
            List<Integer> employedUserIds = Application.getStoreService().getEmployeesByStoreId(storeId).stream().map(User::getId).collect(Collectors.toList());
            availableUsersList = allUsers.stream()
                    .filter(user -> !employedUserIds.contains(user.getId()))
                    .collect(Collectors.toList());

            ObservableList<String> availableUsers = FXCollections.observableArrayList(
                    availableUsersList.stream()
                            .map(user -> user.getPseudo() + " - " + user.getEmail())
                            .collect(Collectors.toList())
            );

            employeeComboBox.setItems(availableUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajouter un employé.
     */
    @FXML
    private void handleAddEmployee() {
        String selectedValue = employeeComboBox.getValue();
        if (selectedValue != null && !selectedValue.isEmpty()) {
            String userEmail = selectedValue.split(" - ")[1];

            User userToAdd = availableUsersList.stream()
                    .filter(user -> user.getEmail().equals(userEmail))
                    .findFirst().orElse(null);

            if (userToAdd != null) {
                try {
                    Application.getStoreService().addEmployeeToStore(storeId, userToAdd.getId());
                    closePopup();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorText.setText("Erreur lors de l'ajout de l'employé. Veuillez réessayer.");
                    errorBox.setVisible(true);
                }
            }
        }
    }

    /**
     * Ferme la fenêtre de popup.
     */
    private void closePopup() {
        Stage stage = (Stage) employeeComboBox.getScene().getWindow();
        stage.close();
    }
}
