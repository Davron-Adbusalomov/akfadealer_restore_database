module duol.restore.akfadealer_restore_database {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens duol.restore.akfadealer_restore_database to javafx.fxml;
    exports duol.restore.akfadealer_restore_database;
}