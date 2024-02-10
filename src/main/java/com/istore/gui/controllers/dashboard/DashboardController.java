package com.istore.gui.controllers.dashboard;

import com.istore.gui.AppLauncher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class DashboardController {

    @FXML
    private AnchorPane contentArea;

    @FXML private Button btnUsers, btnStores, btnManagement, btnSettings;


    String usersIconSVGPath = "M11.5 3.83331C12.5166 3.83331 13.4917 4.23718 14.2106 4.95607C14.9294 5.67496 15.3333 6.64998 15.3333 7.66665C15.3333 8.68331 14.9294 9.65833 14.2106 10.3772C13.4917 11.0961 12.5166 11.5 11.5 11.5C10.4833 11.5 9.50829 11.0961 8.7894 10.3772C8.07051 9.65833 7.66665 8.68331 7.66665 7.66665C7.66665 6.64998 8.07051 5.67496 8.7894 4.95607C9.50829 4.23718 10.4833 3.83331 11.5 3.83331ZM11.5 13.4166C15.7358 13.4166 19.1666 15.1321 19.1666 17.25V19.1666H3.83331V17.25C3.83331 15.1321 7.26415 13.4166 11.5 13.4166Z";
    String storesIconSVGPath = "M3.04192 5.33333H19.3752C20.0169 5.33333 20.5419 4.80833 20.5419 4.16667C20.5419 3.525 20.0169 3 19.3752 3H3.04192C2.40025 3 1.87525 3.525 1.87525 4.16667C1.87525 4.80833 2.40025 5.33333 3.04192 5.33333ZM20.7286 7.43333C20.6236 6.89667 20.1452 6.5 19.5852 6.5H2.83192C2.27192 6.5 1.79358 6.89667 1.68858 7.43333L0.521915 13.2667C0.381915 13.99 0.930249 14.6667 1.66525 14.6667H1.87525V20.5C1.87525 21.1417 2.40025 21.6667 3.04192 21.6667H12.3752C13.0169 21.6667 13.5419 21.1417 13.5419 20.5V14.6667H18.2086V20.5C18.2086 21.1417 18.7336 21.6667 19.3752 21.6667C20.0169 21.6667 20.5419 21.1417 20.5419 20.5V14.6667H20.7519C21.4869 14.6667 22.0352 13.99 21.8952 13.2667L20.7286 7.43333ZM11.2086 19.3333H4.20858V14.6667H11.2086V19.3333Z";
    String managementIconSVGPath = "M10.7222 13.9048V20H4.5C4.5 18.3834 5.15555 16.8331 6.32245 15.69C7.48934 14.5469 9.07199 13.9048 10.7222 13.9048ZM10.7222 13.1429C8.14389 13.1429 6.05556 11.0971 6.05556 8.57143C6.05556 6.04571 8.14389 4 10.7222 4C13.3006 4 15.3889 6.04571 15.3889 8.57143C15.3889 11.0971 13.3006 13.1429 10.7222 13.1429ZM17.7222 16.1905H18.5V20H12.2778V16.1905H13.0556V15.4286C13.0556 14.8224 13.3014 14.241 13.739 13.8123C14.1766 13.3837 14.7701 13.1429 15.3889 13.1429C16.0077 13.1429 16.6012 13.3837 17.0388 13.8123C17.4764 14.241 17.7222 14.8224 17.7222 15.4286V16.1905ZM16.1667 16.1905V15.4286C16.1667 15.2265 16.0847 15.0327 15.9389 14.8898C15.793 14.7469 15.5952 14.6667 15.3889 14.6667C15.1826 14.6667 14.9848 14.7469 14.8389 14.8898C14.6931 15.0327 14.6111 15.2265 14.6111 15.4286V16.1905H16.1667Z";
    String settingsIconSVGPath = "M8.86461 21.0834L8.48128 18.0167C8.27364 17.9368 8.07814 17.841 7.89477 17.7292C7.71141 17.6174 7.53157 17.4976 7.35523 17.3698L4.50419 18.5677L1.86877 14.0156L4.33648 12.1469C4.32051 12.0351 4.31252 11.9274 4.31252 11.8239V11.1771C4.31252 11.0729 4.32051 10.965 4.33648 10.8531L1.86877 8.9844L4.50419 4.43231L7.35523 5.63023C7.53093 5.50245 7.71461 5.38266 7.90627 5.27085C8.09794 5.15905 8.28961 5.06321 8.48128 4.98335L8.86461 1.91669H14.1354L14.5188 4.98335C14.7264 5.06321 14.9222 5.15905 15.1062 5.27085C15.2902 5.38266 15.4698 5.50245 15.6448 5.63023L18.4959 4.43231L21.1313 8.9844L18.6636 10.8531C18.6795 10.965 18.6875 11.0729 18.6875 11.1771V11.823C18.6875 11.9271 18.6716 12.0351 18.6396 12.1469L21.1073 14.0156L18.4719 18.5677L15.6448 17.3698C15.4691 17.4976 15.2854 17.6174 15.0938 17.7292C14.9021 17.841 14.7104 17.9368 14.5188 18.0167L14.1354 21.0834H8.86461ZM11.5479 14.8542C12.4743 14.8542 13.265 14.5268 13.9198 13.8719C14.5747 13.217 14.9021 12.4264 14.9021 11.5C14.9021 10.5736 14.5747 9.78301 13.9198 9.12814C13.265 8.47328 12.4743 8.14585 11.5479 8.14585C10.6056 8.14585 9.8108 8.47328 9.16361 9.12814C8.51641 9.78301 8.19314 10.5736 8.19378 11.5C8.19378 12.4264 8.51737 13.217 9.16457 13.8719C9.81176 14.5268 10.6062 14.8542 11.5479 14.8542Z";

    @FXML
    public void initialize() {
        applyIconToButton(btnUsers, usersIconSVGPath, false);
        applyIconToButton(btnStores, storesIconSVGPath, false);
        applyIconToButton(btnManagement, managementIconSVGPath, false);
        applyIconToButton(btnSettings, settingsIconSVGPath, false);

        loadUsersView();
    }

    @FXML
    private void loadUsersView() {
        loadView("/com/istore/views/dashboard/users-view.fxml", btnUsers);
        AppLauncher.setTitle("Utilisateurs");
    }

    @FXML
    private void loadStoresView() {
        loadView("/com/istore/views/dashboard/stores-view.fxml", btnStores);
        AppLauncher.setTitle("Magasins");
    }

    @FXML
    private void loadManagementView() {
        loadView("/com/istore/views/dashboard/management-view.fxml", btnManagement);
        AppLauncher.setTitle("Gestion");
    }

    @FXML
    private void loadSettingsView() {
        loadView("/com/istore/views/dashboard/settings-view.fxml", btnSettings);
        AppLauncher.setTitle("Paramètres");
    }

    private void loadView(String fxmlPath, Button activeButton) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource(fxmlPath));

            String css = this.getClass().getResource("/com/istore/styles/dashboard.css").toExternalForm();
            view.getStylesheets().add(css);

            contentArea.getChildren().setAll(view);
            highlightActiveButton(activeButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void applyIconToButton(Button button, String svgPathData, boolean isActive) {
        SVGPath svgIcon = new SVGPath();
        svgIcon.setContent(svgPathData);
        svgIcon.setFill(isActive ? Color.WHITE : Color.web("#818181")); // Couleur active ou inactive
        button.setGraphic(svgIcon);
    }

    private void highlightActiveButton(Button activeButton) {
        List<Button> buttons = Arrays.asList(btnUsers, btnStores, btnManagement, btnSettings);
        buttons.forEach(button -> {
            SVGPath icon = (SVGPath) button.getGraphic();
            if (button == activeButton) {
                button.getStyleClass().add("active-button");
                icon.setFill(Color.WHITE); // Couleur pour bouton actif
            } else {
                button.getStyleClass().removeAll("active-button");
                icon.setFill(Color.web("#818181")); // Couleur pour bouton inactif
            }
        });
    }

    @FXML
    private void handleLogout() throws IOException {
        AppLauncher.showLoginView();
    }

}
