package com.istore.gui.controllers.dashboard;

import com.istore.Application;
import com.istore.gui.controllers.dashboard.popup.stores.DeleteStoreConfirmController;
import com.istore.gui.controllers.dashboard.popup.stores.EditStorePopupController;
import com.istore.models.Store;
import com.istore.models.User;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class StoresViewController {

    @FXML
    private TableView<Store> storesTable;
    @FXML
    private TableColumn<Store, Integer> idColumn;
    @FXML
    private TableColumn<Store, String> nameColumn;
    @FXML
    private TableColumn<Store, String> locationColumn;
    @FXML
    private TableColumn<Store, String> employeesColumn;
    @FXML
    private TableColumn<Store, Void> actionsColumn;

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController controller) {
        this.dashboardController = controller;
    }

    public void initialize() {
        setupTableColumns();
        loadStoresIntoTable();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        employeesColumn.setCellValueFactory(cellData -> {
            ObservableList<String> employees = cellData.getValue().getEmployees();
            String joinedEmployees = String.join(", ", employees);
            return new ReadOnlyStringWrapper(joinedEmployees);
        });

        actionsColumn.setCellFactory(param -> new TableCell<Store, Void>() {
            private final Button viewBtn = new Button("");
            private final Button editBtn = new Button("");
            private final Button deleteBtn = new Button("");

            {
                // Visualisation
                SVGPath viewIcon = new SVGPath();
                viewIcon.setContent("M12 9C11.2044 9 10.4413 9.31607 9.87868 9.87868C9.31607 10.4413 9 11.2044 9 12C9 12.7956 9.31607 13.5587 9.87868 14.1213C10.4413 14.6839 11.2044 15 12 15C12.7956 15 13.5587 14.6839 14.1213 14.1213C14.6839 13.5587 15 12.7956 15 12C15 11.2044 14.6839 10.4413 14.1213 9.87868C13.5587 9.31607 12.7956 9 12 9ZM12 17C10.6739 17 9.40215 16.4732 8.46447 15.5355C7.52678 14.5979 7 13.3261 7 12C7 10.6739 7.52678 9.40215 8.46447 8.46447C9.40215 7.52678 10.6739 7 12 7C13.3261 7 14.5979 7.52678 15.5355 8.46447C16.4732 9.40215 17 10.6739 17 12C17 13.3261 16.4732 14.5979 15.5355 15.5355C14.5979 16.4732 13.3261 17 12 17ZM12 4.5C7 4.5 2.73 7.61 1 12C2.73 16.39 7 19.5 12 19.5C17 19.5 21.27 16.39 23 12C21.27 7.61 17 4.5 12 4.5Z");
                viewIcon.getStyleClass().add("view-icon");
                viewBtn.setGraphic(viewIcon);

                // Modification
                SVGPath editIcon = new SVGPath();
                editIcon.setContent("M0 18V13.75L13.2 0.575C13.4 0.391667 13.621 0.25 13.863 0.15C14.105 0.0500001 14.359 0 14.625 0C14.8917 0 15.15 0.0500001 15.4 0.15C15.65 0.25 15.8667 0.4 16.05 0.6L17.425 2C17.625 2.18333 17.771 2.4 17.863 2.65C17.955 2.9 18.0007 3.15 18 3.4C18 3.66667 17.9543 3.921 17.863 4.163C17.7717 4.405 17.6257 4.62567 17.425 4.825L4.25 18H0ZM14.6 4.8L16 3.4L14.6 2L13.2 3.4L14.6 4.8Z");
                editIcon.getStyleClass().add("edit-icon");
                editBtn.setGraphic(editIcon);

                // Suppression
                SVGPath deleteIcon = new SVGPath();
                deleteIcon.setContent("M16 9V19H8V9H16ZM14.5 3H9.5L8.5 4H5V6H19V4H15.5L14.5 3ZM18 7H6V19C6 20.1 6.9 21 8 21H16C17.1 21 18 20.1 18 19V7Z");
                deleteIcon.getStyleClass().add("delete-icon");
                deleteIcon.setFill(Color.web("#FF3C3C"));
                deleteBtn.setGraphic(deleteIcon);

                viewBtn.getStyleClass().add("button-icon");
                editBtn.getStyleClass().add("button-icon");
                deleteBtn.getStyleClass().add("button-icon");

                viewBtn.setOnAction(event -> {
                    Store store = getTableView().getItems().get(getIndex());
                    dashboardController.loadStoreDetailsView();
                });
                editBtn.setOnAction(event -> {
                    Store store = getTableView().getItems().get(getIndex());
                    showEditStorePopup(store);
                });
                deleteBtn.setOnAction(event -> {
                    Store store = getTableView().getItems().get(getIndex());
                    showDeleteStoreConfirmPopup(store);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(5, viewBtn, editBtn, deleteBtn);
                    setGraphic(hbox);
                }
            }
        });
    }

    private void loadStoresIntoTable() {
        Platform.runLater(() -> {
            ObservableList<Store> stores = FXCollections.observableArrayList();
            try {
                stores.addAll(Application.getStoreService().listAllStoresWithEmployees());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            storesTable.setItems(stores);
        });
    }

    public void refreshTable() {
        loadStoresIntoTable();
    }

    private void deleteStore(int storeId) {
        try {
            Application.getStoreService().deleteStore(storeId);
            loadStoresIntoTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showCreateStore() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/create-store.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Créer un magasin");
            stage.setScene(new Scene(root));
            stage.setOnHidden(e -> refreshTable());
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showEditStorePopup(Store store) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/edit-store.fxml"));
            Parent root = loader.load();

            EditStorePopupController controller = loader.getController();
            controller.initStoreData(store);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Éditer un magasin");
            stage.setScene(new Scene(root));
            stage.setOnHidden(e -> refreshTable());
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showDeleteStoreConfirmPopup(Store storeToDelete) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/delete-store-confirm.fxml"));
            Parent root = loader.load();

            DeleteStoreConfirmController controller = loader.getController();
            controller.setOnConfirm(() -> deleteStoreById(storeToDelete.getId()));

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Confirmer la suppression");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteStoreById(int storeId) {
        try {
            Application.getStoreService().deleteStore(storeId);
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
