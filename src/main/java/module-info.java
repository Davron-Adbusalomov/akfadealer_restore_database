module duol.restore.akfadealer_restore_database {
    requires javafx.controls;
    requires javafx.fxml;


    opens duol.restore.akfadealer_restore_database to javafx.fxml;
    exports duol.restore.akfadealer_restore_database;
}