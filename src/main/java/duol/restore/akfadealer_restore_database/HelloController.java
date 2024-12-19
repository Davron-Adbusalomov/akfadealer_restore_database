package duol.restore.akfadealer_restore_database;

import duol.restore.akfadealer_restore_database.config.DatabaseConfig;
import duol.restore.akfadealer_restore_database.service.DealerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public void initialize() {
        // Load dealer names into the ComboBox
        loadDealers();
    }

    @FXML
    protected void onBackupAndRestoreButtonClick() {
        String selectedDealer = dealerComboBox.getValue();
        String dealerDb = dealerDatabaseInput.getText();
        String csvPath = csvPathInput.getText();
        String sqlScript = sqlScriptInput.getText();
        statusLabel.setText("Processing backup and restore...");
        DealerService dealerService = new DealerService();
        dealerService.getDealers();
    }

    private void loadDealers() {
        ObservableList<String> dealerNames = FXCollections.observableArrayList();

        // Example: Fetch dealer names from database or add manually
        dealerNames.addAll("Dealer A", "Dealer B", "Dealer C");

        // Set the items in the ComboBox
        dealerComboBox.setItems(dealerNames);
    }
}
