module com.istore {
    requires java.sql;
    requires jbcrypt;
    requires javafx.controls;
    requires javafx.fxml;


    opens com.istore to javafx.fxml;
    exports com.istore;
    exports com.istore.gui;
    opens com.istore.gui to javafx.fxml;
}