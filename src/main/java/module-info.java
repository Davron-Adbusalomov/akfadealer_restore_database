module duol.restore.akfadealer_restore_database {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;


    opens duol.restore.akfadealer_restore_database to javafx.fxml;
    exports duol.restore.akfadealer_restore_database;
}