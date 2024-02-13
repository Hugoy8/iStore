module com.istore {
    requires java.sql;
    requires jbcrypt;
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.java;


    opens com.istore to javafx.fxml;
    exports com.istore;
    exports com.istore.gui;
    opens com.istore.gui to javafx.fxml;
    exports com.istore.gui.controllers.auth;
    opens com.istore.gui.controllers.auth to javafx.fxml;
    exports com.istore.gui.controllers.dashboard;
    opens com.istore.gui.controllers.dashboard to javafx.fxml;
}