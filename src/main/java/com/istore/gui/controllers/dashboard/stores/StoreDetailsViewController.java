package com.istore.gui.controllers.dashboard.stores;

import com.istore.gui.controllers.dashboard.DashboardController;
import com.istore.models.Store;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

import static com.istore.Application.getAuthService;

public class StoreDetailsViewController {

    @FXML
    private TableView<Store> inventoryTable;
    @FXML
    private TableColumn<Store, Integer> idColumn;
    @FXML
    private TableColumn<Store, String> nameColumn;
    @FXML
    private TableColumn<Store, String> priceColumn;
    @FXML
    private TableColumn<Store, String> stockColumn;
    @FXML
    private TableColumn<Store, Void> actionsColumn;

    @FXML
    private Button inventoryButton, employeesButton;

    @FXML
    private Button inventoryAddButton, employeesAddButton;

    @FXML
    private VBox inventorySection, employeesSection;

    @FXML
    private HBox horizontalBox;

    private DashboardController dashboardController;

    /**
     * Initialisation de la vue.
     */
    public void initialize() {
        this.handleInventorySection();
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private void showStoresView() {
        if (dashboardController != null) {
            dashboardController.loadStoresView();
        }
    }

    /**
     * Permet d'afficher la section d'information et de masquer les autres sections.
     */
    @FXML
    public void handleInventorySection() {
        this.inventoryButton.setTextFill(Color.web("#007AFF"));
        this.employeesButton.setTextFill(Color.web("#818181"));

        this.inventoryAddButton.setVisible(true);
        this.inventoryAddButton.setManaged(true);

        this.employeesAddButton.setVisible(false);
        this.employeesAddButton.setManaged(false);

        this.employeesSection.setVisible(false);
        this.employeesSection.setManaged(false);

        this.inventorySection.setVisible(true);
        this.inventorySection.setManaged(true);

        this.horizontalBox.setSpacing(780);
    }

    /**
     * Permet d'afficher la section d'information et de masquer les autres sections.
     */
    @FXML
    public void handleEmployeesSection() {
        this.employeesButton.setTextFill(Color.web("#007AFF"));
        this.inventoryButton.setTextFill(Color.web("#818181"));


        this.inventoryAddButton.setVisible(false);
        this.inventoryAddButton.setManaged(false);

        this.employeesAddButton.setVisible(true);
        this.employeesAddButton.setManaged(true);

        this.employeesAddButton.setVisible(true);
        this.employeesAddButton.setManaged(true);

        this.inventorySection.setVisible(false);
        this.inventorySection.setManaged(false);

        this.horizontalBox.setSpacing(755);

    }
}
