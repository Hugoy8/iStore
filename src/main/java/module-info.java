module com.istore {
    requires java.base;
    requires java.sql;
    requires jbcrypt;
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;

    opens com.istore.models to javafx.base, javafx.fxml;
    exports com.istore.models;
    opens com.istore to javafx.fxml;
    exports com.istore;
    exports com.istore.dao;
    exports com.istore.services;
    exports com.istore.gui;
    opens com.istore.gui to javafx.fxml;
    exports com.istore.gui.controllers.auth;
    opens com.istore.gui.controllers.auth to javafx.fxml;
    exports com.istore.gui.controllers.dashboard;
    opens com.istore.gui.controllers.dashboard to javafx.fxml;
    exports com.istore.gui.controllers.dashboard.popup.stores;
    exports com.istore.gui.controllers.dashboard.popup.users;
    exports com.istore.gui.controllers.dashboard.stores;
    opens com.istore.gui.controllers.dashboard.stores to javafx.fxml;
    opens com.istore.gui.controllers.dashboard.popup.users to javafx.fxml;
}