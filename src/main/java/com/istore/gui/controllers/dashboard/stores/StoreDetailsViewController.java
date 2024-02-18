package com.istore.gui.controllers.dashboard.stores;

import com.istore.gui.controllers.dashboard.DashboardController;
import com.istore.models.Store;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    private void showStoresView() {
        if (dashboardController != null) {
            dashboardController.loadStoresView();
        }
    }
}
