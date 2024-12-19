package duol.restore.akfadealer_restore_database;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField dealerDatabaseInput;

    @FXML
    private TextField csvPathInput;

    @FXML
    private ComboBox<String> dealerComboBox;

    @FXML
    private TextArea sqlScriptInput;

    @FXML
    private Label statusLabel;

    @FXML
    protected void onBackupAndRestoreButtonClick() {
        String selectedDealer = dealerComboBox.getValue();
        String dealerDb = dealerDatabaseInput.getText();
        String csvPath = csvPathInput.getText();
        String sqlScript = sqlScriptInput.getText();

        statusLabel.setText("Processing backup and restore...");
        // Implement your logic for backup and restore here
    }
}
