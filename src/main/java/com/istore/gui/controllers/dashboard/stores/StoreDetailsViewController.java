package com.istore.gui.controllers.dashboard.stores;

import com.istore.Application;
import com.istore.gui.AppLauncher;
import com.istore.gui.controllers.dashboard.DashboardController;
import com.istore.gui.controllers.dashboard.popup.employees.AddEmployeePopupController;
import com.istore.gui.controllers.dashboard.popup.items.CreateItemPopupController;
import com.istore.gui.controllers.dashboard.popup.items.DeleteItemConfirmController;
import com.istore.gui.controllers.dashboard.popup.items.EditItemPopupController;
import com.istore.gui.controllers.dashboard.popup.employees.RemoveEmployeeConfirmController;
import com.istore.gui.controllers.dashboard.popup.stores.DeleteStoreConfirmController;
import com.istore.models.Item;
import com.istore.models.Store;
import com.istore.models.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StoreDetailsViewController {

    // Tableau de l'invenatire
    @FXML private TableView<Item> inventoryTable;
    @FXML private TableColumn<Item, Integer> idColumnInventory;
    @FXML private TableColumn<Item, String> nameColumnInventory;
    @FXML private TableColumn<Item, String> priceColumnInventory;
    @FXML private TableColumn<Item, String> stockColumnInventory;
    @FXML private TableColumn<Item, Void> actionsColumnInventory;

    // Identifiant du magasin actuel
    private int currentStoreId;

    // Tableau des employés
    @FXML private TableView<User> employeesTable;
    @FXML private TableColumn<User, Integer> idColumnEmployees;
    @FXML private TableColumn<User, String> nameColumnEmployees;
    @FXML private TableColumn<User, String> emailColumnEmployees;
    @FXML private TableColumn<User, String> roleColumnEmployees;
    @FXML private TableColumn<User, Void> actionsColumnEmployees;

    // Éléments de la vue
    @FXML private Button inventoryButton, employeesButton;
    @FXML private Button inventoryAddButton, employeesAddButton;
    @FXML private VBox inventorySection, employeesSection;
    @FXML private HBox horizontalBox;

    private DashboardController dashboardController;

    @FXML private Text storeNameText;
    @FXML private Text breadcrumbText;

    private Store currentStore;

    /**
     * Initialise les données du magasin.
     * @param store Le magasin actuel.
     */
    public void initStoreData(Store store) {
        this.currentStore = store;

        storeNameText.setText(store.getName());
        breadcrumbText.setText("Magasins / Magasin - " + store.getName());

        loadInventoryIntoTable(store.getId());
        loadEmployeesTable(store.getId());
    }

    /**
     * Définit le contrôleur du tableau de bord.
     * @param dashboardController Le contrôleur du tableau de bord.
     */
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    /**
     * Initialisation de la vue.
     */
    public void initialize() {
        this.setupInventoryTableColumns();
        this.setupEmployeeTableColumns();
        this.handleInventorySection();
    }

    /**
     * Configure les colonnes de la table de l'inventaire.
     */
    private void setupInventoryTableColumns() {
        idColumnInventory.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnInventory.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumnInventory.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumnInventory.setCellFactory(param -> new TableCell<Item, String>() {
            final Button decrementBtn = new Button("-");
            final TextField stockField = new TextField();
            final Button incrementBtn = new Button("+");

            {
                stockField.setPrefWidth(33);
                decrementBtn.setStyle("-fx-background-color: transparent;");
                incrementBtn.setStyle("-fx-background-color: transparent;");
                stockField.setStyle("-fx-border-color: #BABABA; -fx-background-color: white; -fx-background-insets: 0; -fx-background-radius: 4px; -fx-border-radius:4px;");

                decrementBtn.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    try {
                        Application.getItemService().decrementStock(item.getId());
                        refreshTable();
                    } catch (SQLException e) {
                        throw e;
                    }
                });

                incrementBtn.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    try {
                        Application.getItemService().incrementStock(item.getId());
                        refreshTable();
                    } catch (SQLException e) {
                        throw e;
                    }
                });

                stockField.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    try {
                        int newStock = Integer.parseInt(stockField.getText());
                        item.setStock(newStock);
                        Application.getItemService().updateItem(item);
                        refreshTable();
                    } catch (NumberFormatException | SQLException e) {
                        throw e;
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Item currentItem = getTableView().getItems().get(getIndex());
                    stockField.setText(String.valueOf(currentItem.getStock()));
                    HBox hbox = new HBox(decrementBtn, stockField, incrementBtn);
                    hbox.setSpacing(0);
                    setGraphic(hbox);
                }
            }
        });
        actionsColumnInventory.setCellFactory(param -> new TableCell<Item, Void>() {
            private final Button editBtn = new Button("");
            private final Button deleteBtn = new Button("");

            {

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

                editBtn.getStyleClass().add("button-icon");
                deleteBtn.getStyleClass().add("button-icon");

                editBtn.setOnAction(event -> {
                    Item selectedItem = getTableView().getItems().get(getIndex());
                    try {
                        showEditItemPopup(selectedItem);
                    } catch (IOException e) {
                        throw e;
                    }
                });
                deleteBtn.setOnAction(event -> {
                    Item selectedItem = getTableView().getItems().get(getIndex());
                    try {
                        showDeleteItemConfirmPopup(selectedItem);
                    } catch (IOException e) {
                        throw e;
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(5, editBtn, deleteBtn);
                    setGraphic(hbox);
                }
            }
        });
    }

    /**
     * Charge l'inventaire dans la table.
     * @param storeId L'identifiant du magasin.
     */
    private void loadInventoryIntoTable(int storeId) {
        Platform.runLater(() -> {
            try {
                ObservableList<Item> inventoryItems = FXCollections.observableArrayList(Application.getItemService().findItemsByStoreId(storeId)); // Supposons cette méthode existe
                inventoryTable.setItems(inventoryItems);
            } catch (SQLException e) {
                throw e;
            }
        });
    }

    /**
     * Configure les colonnes de la table des employés.
     */
    private void setupEmployeeTableColumns() {
        idColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("pseudo"));
        emailColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumnEmployees.setCellValueFactory(new PropertyValueFactory<>("role"));
        actionsColumnEmployees.setCellFactory(param -> new TableCell<User, Void>() {
            private final Button deleteBtn = new Button("");

            {

                // Suppression
                SVGPath deleteIcon = new SVGPath();
                deleteIcon.setContent("M16 9V19H8V9H16ZM14.5 3H9.5L8.5 4H5V6H19V4H15.5L14.5 3ZM18 7H6V19C6 20.1 6.9 21 8 21H16C17.1 21 18 20.1 18 19V7Z");
                deleteIcon.getStyleClass().add("delete-icon");
                deleteIcon.setFill(Color.web("#FF3C3C"));
                deleteBtn.setGraphic(deleteIcon);

                deleteBtn.getStyleClass().add("button-icon");

                deleteBtn.setOnAction(event -> {
                    User selectedUser = getTableView().getItems().get(getIndex());
                    try {
                        showRemoveEmployeeConfirmPopup(selectedUser);
                    } catch (IOException e) {
                        throw e;
                    }
                });
            }

            @Override
            protected void updateItem(Void user, boolean empty) {
                super.updateItem(user, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(5, deleteBtn);
                    setGraphic(hbox);
                }
            }
        });
    }

    /**
     * Charge les employés dans la table.
     * @param storeId L'identifiant du magasin.
     */
    private void loadEmployeesTable(int storeId) {
        Platform.runLater(() -> {
            try {
                List<User> employees = Application.getStoreService().getEmployeesByStoreId(storeId);
                ObservableList<User> observableEmployees = FXCollections.observableArrayList(employees);
                employeesTable.setItems(observableEmployees);
            } catch (SQLException e) {
                throw e;
            }
        });
    }

    /**
     * Rafraîchit la table de l'inventaire.
     */
    public void refreshTable() {
        loadInventoryIntoTable(currentStore.getId());
        loadEmployeesTable(currentStore.getId());
    }

    /**
     * Affiche la popup de création d'un nouvel item.
     * @throws IOException Exception qui gère les erreurs de changement de vue
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    @FXML
    private void showCreateItemPopup() throws IOException {
        if (Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/items/create-item.fxml"));
                Parent root = loader.load();

                CreateItemPopupController controller = loader.getController();
                int inventoryId = Application.getInventoryService().getInventoryIdByStoreId(currentStore.getId());
                controller.setInventoryId(inventoryId);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Créer un nouvel objet");
                stage.setScene(new Scene(root));
                stage.setOnHidden(e -> refreshTable());
                stage.showAndWait();
            } catch (IOException | SQLException e) {
                throw e;
            }
        } else {
            AppLauncher.showAdminError();
        }
    }

    /**
     * Affiche la fenêtre de modification d'un item.
     * @param item L'item à modifier.
     */
    private void showEditItemPopup(Item item) throws IOException {
        if (Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/items/edit-item.fxml"));
                Parent root = loader.load();

                EditItemPopupController controller = loader.getController();
                controller.initItemData(item);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Éditer un objet");
                stage.setScene(new Scene(root));
                stage.setOnHidden(e -> refreshTable());
                stage.showAndWait();
            } catch (IOException e) {
                throw e;
            }
        } else {
            AppLauncher.showAdminError();
        }
    }

    /**
     * Affiche la fenêtre de confirmation de suppression d'un objet.
     * @param itemToDelete L'objet à supprimer.
     */
    @FXML
    private void showDeleteItemConfirmPopup(Item itemToDelete) throws IOException {
        if (Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/items/delete-item-confirm.fxml"));
                Parent root = loader.load();

                DeleteItemConfirmController controller = loader.getController();
                controller.setOnConfirm(() -> deleteItemById(itemToDelete.getId()));

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Confirmer la suppression");
                stage.setScene(new Scene(root));
                stage.setOnHidden(e -> refreshTable());
                stage.showAndWait();
            } catch (IOException e) {
                throw e;
            }
        } else {
            AppLauncher.showAdminError();
        }
    }

    /**
     * Supprime un objet par son ID.
     * @param itemId L'ID de l'objet à supprimer.
     */
    private void deleteItemById(int itemId) {
        try {
            Application.getItemService().deleteItemById(itemId);
            refreshTable();
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Affiche la popup de création d'un nouvel item.
     */
    @FXML
    private void showAddEmployeePopup() throws IOException {
        if (Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/employees/add-employee.fxml"));
                Parent root = loader.load();

                AddEmployeePopupController controller = loader.getController();
                controller.setStoreId(currentStore.getId());

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Ajouter un employé");
                stage.setScene(new Scene(root));
                stage.setOnHidden(e -> refreshTable());
                stage.showAndWait();
            } catch (IOException e) {
                throw e;
            }
        } else {
            AppLauncher.showAdminError();
        }
    }

    /**
     * Supprime un employé.
     * @param user L'employé à supprimer.
     */
    private void showRemoveEmployeeConfirmPopup(User user) throws IOException {
        if (Application.getUserService().checkAdmin(Application.getAuthService().getUser())){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/istore/views/dashboard/popup/employees/remove-employee.fxml"));
                Parent root = loader.load();

                RemoveEmployeeConfirmController controller = loader.getController();
                controller.setOnConfirm(() -> removeEmployee(user.getId()));

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Supprimer un employé");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                throw e;
            }
        } else {
            AppLauncher.showAdminError();
        }
    }

    /**
     * Supprime un employé.
     * @param userId L'ID de l'employé à supprimer.
     */
    private void removeEmployee(int userId) {
        try {
            Application.getStoreService().removeEmployeeFromStore(currentStore.getId(), userId);
            loadEmployeesTable(currentStore.getId());
        } catch (SQLException e) {
            throw e;
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

        this.employeesSection.setVisible(true);
        this.employeesSection.setManaged(true);

        this.inventorySection.setVisible(false);
        this.inventorySection.setManaged(false);

        this.horizontalBox.setSpacing(755);
    }

    /**
     * Affiche la fenêtre des magasins (retour en arrière).
     * @throws IOException Exception qui gère les erreurs de changement de vue
     */
    @FXML
    public void showStoresView() throws IOException {
        this.dashboardController.loadStoresView();
    }
}
