module com.example.bookclubdesktopcli {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.bookclubdesktopcli to javafx.fxml;
    exports com.example.bookclubdesktopcli;
}