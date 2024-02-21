package com.istore.gui.controllers.dashboard;

import com.istore.Application;
import com.istore.gui.AppLauncher;
import com.istore.gui.controllers.dashboard.popup.users.DeleteUserConfirmController;
import com.istore.gui.controllers.dashboard.popup.whitelist.DeleteWhitelistConfirmController;
import com.istore.models.User;
import com.istore.models.WhitelistEmail;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;

public class ManagementViewController {

    /**
     * La table des emails sur la whitelist.
     */
    @FXML private TableView<WhitelistEmail> whitelistTable;
    @FXML private TableColumn<WhitelistEmail, Integer> idColumn;
    @FXML private TableColumn<WhitelistEmail, String> emailColumn;
    @FXML private TableColumn<WhitelistEmail, Void> actionsColumn;

    @FXML private TextField emailField;
    @FXML private HBox errorBox, successBox;
    @FXML private Text errorText, successText;

    /**
     * Initialise la vue.
     */
    @FXML
    public void initialize() {
        setupTableColumns();
        try {
            loadWhitelistIntoTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet d'initialiser les colonnes de la table.
     */
    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        actionsColumn.setCellFactory(param -> new TableCell<WhitelistEmail, Void>() {
            private final Button deleteBtn = new Button();

            {
                // Crée l'icône de suppression
                SVGPath deleteIcon = new SVGPath();
                deleteIcon.setContent("M16 9V19H8V9H16ZM14.5 3H9.5L8.5 4H5V6H19V4H15.5L14.5 3ZM18 7H6V19C6 20.1 6.9 21 8 21H16C17.1 21 18 20.1 18 19V7Z");
                deleteIcon.getStyleClass().add("delete-icon");
                deleteIcon.setFill(Color.web("#FF3C3C"));
                deleteBtn.setGraphic(deleteIcon);

                deleteBtn.getStyleClass().add("button-icon");

                deleteBtn.setOnAction(event -> {
                    WhitelistEmail selectedEmail = getTableView().getItems().get(getIndex());
                    try {
                        showDeleteWhitelistConfirmPopup(selectedEmail);
                        loadWhitelistIntoTable();
                    } catch (SQLException | IOException e) {
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
                    HBox hbox = new HBox(deleteBtn);
                    setGraphic(hbox);
                }
            }
        });
    }

    /**
     * Charge les adresses e-mail de la whitelist dans la table.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    private void loadWhitelistIntoTable() throws SQLException {
        ObservableList<WhitelistEmail> whitelistEmails = FXCollections.observableArrayList(Application.getUserService().listWhitelistEmails());
        whitelistTable.setItems(whitelistEmails);
    }

    /**
     * Rafraîchit la table des e-mails whitelist.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void refreshTable() throws SQLException {
        loadWhitelistIntoTable();
    }

    /**
     * Affiche la fenêtre de confirmation de suppression de l'email de la whitelist.
     * @param whitelistEmail L'email à supprimer.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    private void showDeleteWhitelistConfirmPopup(WhitelistEmail whitelistEmail) throws IOException {
        if (Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/whitelist/delete-whitelist-confirm.fxml"));
                Parent root = loader.load();

                DeleteWhitelistConfirmController controller = loader.getController();
                controller.setOnConfirm(() -> deleteWhitelistById(whitelistEmail.getEmail()));

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
     * Supprime un whitelist par son email.
     * @param whitelistEmail L'email à supprimer.
     */
    private void deleteWhitelistById(String whitelistEmail) {
        try {
            Application.getUserService().removeEmailFromWhitelist(whitelistEmail);
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Ajoute une adresse e-mail à la liste blanche.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    @FXML
    private void handleWhitelist() throws IOException {
        if (Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            String email = emailField.getText().trim();
            try {
                Application.getUserService().addEmailToWhitelist(email);
                showErrorBox(false);
                showSuccessBox(true);
                refreshTable();

                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(event -> {
                    emailField.clear();
                    showSuccessBox(false);
                });
                pause.play();
            } catch (SQLException e) {
                showSuccessBox(false);
                showErrorBox(true);
            }
        } else {
            AppLauncher.showAdminError();
        }
    }

    /**
     * Affiche ou masque la boîte d'erreur.
     * @param show true pour afficher, false pour masquer.
     */
    private void showErrorBox(boolean show) {
        errorBox.setVisible(show);
        errorBox.setManaged(show);
        if (show) {
            errorText.setText("L'adresse e-mail est déjà dans la liste blanche.");
        }
    }

    /**
     * Affiche ou masque la boîte de succès.
     * @param show true pour afficher, false pour masquer.
     */
    private void showSuccessBox(boolean show) {
        successBox.setVisible(show);
        successBox.setManaged(show);
        if (show) {
            successText.setText("E-mail ajouté avec succès à la liste blanche !");
        }
    }
}
