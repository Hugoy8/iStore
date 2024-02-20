module com.istore {
    // Dépendances requises
    requires java.base;
    requires java.sql;
    requires jbcrypt;
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;

    // Ouvertures de modules
    opens com.istore to javafx.fxml;
    opens com.istore.models to javafx.base, javafx.fxml;
    opens com.istore.gui to javafx.fxml;
    opens com.istore.gui.controllers.auth to javafx.fxml;
    opens com.istore.gui.controllers.dashboard to javafx.fxml;
    opens com.istore.gui.controllers.dashboard.popup.stores to javafx.fxml;
    opens com.istore.gui.controllers.dashboard.popup.users to javafx.fxml;
    opens com.istore.gui.controllers.dashboard.popup.items to javafx.fxml;
    opens com.istore.gui.controllers.dashboard.popup.employees to javafx.fxml;
    opens com.istore.gui.controllers.dashboard.popup.whitelist to javafx.fxml;
    opens com.istore.gui.controllers.dashboard.stores to javafx.fxml;

    // Exportations de modules
    exports com.istore;
    exports com.istore.models;
    exports com.istore.dao;
    exports com.istore.services;
    exports com.istore.gui;
    exports com.istore.gui.controllers.auth;
    exports com.istore.gui.controllers.dashboard;
    exports com.istore.gui.controllers.dashboard.stores;
    exports com.istore.gui.controllers.dashboard.popup.stores;
    exports com.istore.gui.controllers.dashboard.popup.users;
    exports com.istore.gui.controllers.dashboard.popup.items;
    exports com.istore.gui.controllers.dashboard.popup.employees;
    exports com.istore.gui.controllers.dashboard.popup.whitelist;
}
