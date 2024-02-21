package com.istore.gui.controllers.dashboard;

import com.istore.gui.AppLauncher;
import com.istore.gui.controllers.dashboard.popup.users.EditUserPopupController;
import com.istore.gui.controllers.dashboard.popup.users.DeleteUserConfirmController;
import com.istore.models.User;
import com.istore.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static com.istore.Application.getAuthService;

public class UsersViewController {

    /**
     * La table des utilisateurs.
     */
    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> pseudoColumn, emailColumn, roleColumn;
    @FXML private TableColumn<User, Void> actionsColumn;

    @FXML private TextField searchField;

    /**
     * Initialise la vue.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    @FXML
    public void initialize() throws SQLException {
        this.setupTableColumns();
        this.loadUsersIntoTable();
        this.setupSearchField();
    }

    /**
     * Permet d'initialiser le champ de recherche.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    private void setupSearchField() throws SQLException {
        FilteredList<User> filteredUsers = new FilteredList<>(FXCollections.observableArrayList(Application.getUserService().listAllUsers()), p -> true);

        // Ajout d'un écouteur sur la propriété text du TextField de recherche
        this.searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Filtre du pseudo
                if (user.getPseudo().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                // Filtre de l'email
                else if (user.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });

            // Ordonnance des données filtrées
            SortedList<User> sortedData = new SortedList<>(filteredUsers);
            sortedData.comparatorProperty().bind(usersTable.comparatorProperty());

            // Mise à jour de la table.
            usersTable.setItems(sortedData);
        });
    }

    /**
     * Configure les colonnes de la table.
     */
    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        pseudoColumn.setCellValueFactory(new PropertyValueFactory<>("pseudo"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        actionsColumn.setCellFactory(param -> new TableCell<User, Void>() {
            private final Button editBtn = new Button("");
            private final Button deleteBtn = new Button("");

            {
                // Crée l'icône de modification
                SVGPath editIcon = new SVGPath();
                editIcon.setContent("M0 18V13.75L13.2 0.575C13.4 0.391667 13.621 0.25 13.863 0.15C14.105 0.0500001 14.359 0 14.625 0C14.8917 0 15.15 0.0500001 15.4 0.15C15.65 0.25 15.8667 0.4 16.05 0.6L17.425 2C17.625 2.18333 17.771 2.4 17.863 2.65C17.955 2.9 18.0007 3.15 18 3.4C18 3.66667 17.9543 3.921 17.863 4.163C17.7717 4.405 17.6257 4.62567 17.425 4.825L4.25 18H0ZM14.6 4.8L16 3.4L14.6 2L13.2 3.4L14.6 4.8Z");
                editIcon.getStyleClass().add("edit-icon");
                editBtn.setGraphic(editIcon);

                // Crée l'icône de suppression
                SVGPath deleteIcon = new SVGPath();
                deleteIcon.setContent("M16 9V19H8V9H16ZM14.5 3H9.5L8.5 4H5V6H19V4H15.5L14.5 3ZM18 7H6V19C6 20.1 6.9 21 8 21H16C17.1 21 18 20.1 18 19V7Z");
                deleteIcon.getStyleClass().add("delete-icon");
                deleteIcon.setFill(Color.web("#FF3C3C"));
                deleteBtn.setGraphic(deleteIcon);

                editBtn.getStyleClass().add("button-icon");
                deleteBtn.getStyleClass().add("button-icon");

                editBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    try {
                        showEditUserPopup(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                deleteBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    try {
                        showDeleteUserConfirmPopup(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(10, editBtn, deleteBtn);
                    setGraphic(hbox);
                }
            }
        });
    }

    /**
     * Charge les utilisateurs dans la table.
     */
    private void loadUsersIntoTable() {
        Platform.runLater(() -> {
            try {
                ObservableList<User> users = FXCollections.observableArrayList(Application.getUserService().listAllUsers());
                usersTable.setItems(users);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Rafraîchit la table des utilisateurs.
     */
    public void refreshTable() {
        loadUsersIntoTable();
    }

    /**
     * Affiche la fenêtre de création de magasin.
     */
    @FXML
    private void showCreateUserPopup() throws IOException {
        if (Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/users/create-user.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Créer un Utilisateur");
                stage.setScene(new Scene(root));
                stage.setOnHidden(e -> refreshTable());
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AppLauncher.showAdminError();
        }
    }

    /**
     * Affiche la fenêtre de modification d'un utilisateur.
     * @param user L'utilisateur à modifier.
     */
    private void showEditUserPopup(User user) throws IOException {
        if (Application.getUserService().checkAdmin(Application.getAuthService().getUser()) || user.getId() == Application.getAuthService().getUser().getId()){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/users/edit-user.fxml"));
                Parent root = loader.load();

                EditUserPopupController controller = loader.getController();
                controller.initUserData(user); // Méthode pour initialiser les données de l'utilisateur dans la popup

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Éditer un Utilisateur");
                stage.setScene(new Scene(root));
                stage.setOnHidden(e -> refreshTable());
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AppLauncher.showAdminError();
        }
    }

    /**
     * Affiche la fenêtre de confirmation de suppression de l'utilisateur.
     * @param userToDelete L'utilisateur à supprimer.
     */
    private void showDeleteUserConfirmPopup(User userToDelete) throws IOException {
        if (Application.getUserService().checkAdmin(Application.getAuthService().getUser()) || userToDelete.getId() == Application.getAuthService().getUser().getId()){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/users/delete-user-confirm.fxml"));
                Parent root = loader.load();

                DeleteUserConfirmController controller = loader.getController();
                controller.setOnConfirm(() -> deleteUserById(userToDelete.getId()));

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Confirmer la suppression");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            AppLauncher.showAdminError();
        }
    }

    /**
     * Supprime un utilisateur par son ID.
     * @param userId L'ID de l'utilisateur à supprimer.
     */
    private void deleteUserById(int userId) {
        try {
            Application.getStoreService().removeEmployeeFromAllStores(userId);
            Application.getUserService().deleteUserById(userId);
            refreshTable();

            if (userId == Application.getAuthService().getUser().getId()) {
                getAuthService().setUser(null);
                AppLauncher.showLoginView();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}
