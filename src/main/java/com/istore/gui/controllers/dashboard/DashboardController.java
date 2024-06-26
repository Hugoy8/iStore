package com.istore.gui.controllers.dashboard;

import com.istore.Application;
import com.istore.gui.AppLauncher;
import com.istore.models.Store;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import com.istore.gui.controllers.dashboard.stores.StoreDetailsViewController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.istore.Application.getAuthService;


public class DashboardController {

    @FXML private AnchorPane contentArea;
    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;
    @FXML private Button btnUsers, btnStores, btnManagement, btnSettings;
    @FXML private VBox sideBarContainer;
    @FXML private HBox svgBtnManagement;

    /**
     * Initialise la vue.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    @FXML
    public void initialize() throws IOException {
        this.loadUsersView();

        if (!Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            this.btnManagement.setManaged(false);
            this.svgBtnManagement.setManaged(false);
            this.sideBarContainer.setSpacing(310);
        }

        retrieveUserInfos();
    }

    /**
     * Récupère les informations de l'utilisateur connecté.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    @FXML
    public void retrieveUserInfos() throws IOException {
        // Affiche le nom d'utilisateur
        usernameLabel.setText(getAuthService().getUser().getPseudo());

        // Affiche le rôle de l'utilisateur
        String role = getAuthService().getUser().getRole();
        if (role.equals("admin")) {
            roleLabel.setText("Administrateur");
            roleLabel.setTextFill(Color.web("#007AFF"));
        } else {
            roleLabel.setText("Utilisateur");
            roleLabel.setTextFill(Color.web("#818181"));
        }
    }

    /**
     * Charge la vue des utilisateurs.
     */
    @FXML
    private void loadUsersView() {
        loadView("/com/istore/views/dashboard/users-view.fxml", btnUsers);
        AppLauncher.setTitle("Utilisateurs");
    }

    /**
     * Charge la vue des magasins.
     */
    @FXML
    public void loadStoresView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/stores-view.fxml"));
            Parent storesView = loader.load();

            StoresViewController storesViewController = loader.getController();
            storesViewController.setDashboardController(this);

            contentArea.getChildren().setAll(storesView);

            highlightActiveButton(btnStores);
            AppLauncher.setTitle("Magasins");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge la vue des détails du magasin.
     * @param store Le magasin
     */
    public void loadStoreDetailsView(Store store) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/stores/store-details-view.fxml"));
            Parent detailView = loader.load();

            StoreDetailsViewController controller = loader.getController();
            controller.setDashboardController(this);
            controller.initStoreData(store);

            contentArea.getChildren().setAll(detailView);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge la vue de gestion.
     */
    @FXML
    private void loadManagementView() throws IOException {
        if (Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            loadView("/com/istore/views/dashboard/management-view.fxml", btnManagement);
            AppLauncher.setTitle("Gestion");
        } else {
            AppLauncher.showAdminError();
        }
    }

    /**
     * Charge la vue des paramètres.
     */
    @FXML
    private void loadSettingsView() {
        loadView("/com/istore/views/dashboard/settings-view.fxml", btnSettings);
        AppLauncher.setTitle("Paramètres");
    }

    /**
     * Charge une vue dans la zone de contenu.
     * @param fxmlPath Chemin vers le fichier FXML.
     * @param activeButton Bouton actif.
     */
    private void loadView(String fxmlPath, Button activeButton) {
        try {
            AnchorPane view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));

            String css = Objects.requireNonNull(this.getClass().getResource("/com/istore/styles/dashboard.css")).toExternalForm();
            view.getStylesheets().add(css);

            contentArea.getChildren().setAll(view);
            highlightActiveButton(activeButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met en surbrillance le bouton actif.
     * @param activeButton Bouton actif.
     */
    private void highlightActiveButton(Button activeButton) throws IOException {
        List<Button> buttons = Arrays.asList(btnUsers, btnStores, btnManagement, btnSettings);

        if (!Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            buttons = Arrays.asList(btnUsers, btnStores, btnSettings);
        }

        buttons.forEach(button -> {
            if (button.getGraphic() instanceof HBox) {
                HBox hbox = (HBox) button.getGraphic();
                SVGPath icon = hbox.getChildren().stream().filter(node -> node instanceof SVGPath).map(node -> (SVGPath) node).findFirst().orElse(null);

                if (icon != null) {
                    if (button == activeButton) {
                        button.getStyleClass().add("activeButton");
                        icon.setFill(Color.WHITE);
                    } else {
                        button.getStyleClass().removeAll("activeButton");
                        icon.setFill(Color.web("#818181"));
                    }
                }
            }
        });
    }

    /**
     * Déconnecte l'utilisateur et le redirige vers la page de connexion.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    @FXML
    private void handleLogout() throws IOException {
        getAuthService().setUser(null);
        AppLauncher.showLoginView();
    }
}
