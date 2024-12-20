package duol.restore.akfadealer_restore_database;

import duol.restore.akfadealer_restore_database.config.DatabaseConfig;
import duol.restore.akfadealer_restore_database.model.Dealer;
import duol.restore.akfadealer_restore_database.service.DealerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AppController {

    @FXML
    private TextField dealerDatabaseInput;

    @FXML
    private ComboBox<Dealer> dealerComboBox;

    @FXML
    private TextArea sqlScriptInput;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField csvPathInput;

    @FXML
    private Button browseButton;

    @FXML
    public void initialize() {
        // Load dealer names into the ComboBox
        loadDealers();
        browseButton.setOnAction(event -> selectFolder());
    }

    @FXML
    protected void onBackupAndRestoreButtonClick() throws SQLException, IOException {
        Dealer selectedDealer = dealerComboBox.getValue();
        String dealerDb = dealerDatabaseInput.getText();
        String csvPath = csvPathInput.getText();
        String sqlScript = sqlScriptInput.getText();
        statusLabel.setText("Processing backup and restore...");


        if (selectedDealer != null){
            DealerService dealerService = new DealerService();
            dealerService.makeLastSyncNull(selectedDealer.getId());

//          copy to csv
            dealerService.exportToCSV(csvPath, selectedDealer.getId());
        }


    }

    private void loadDealers() {
        DealerService dealerService = new DealerService();

        // Fetch dealers from the service
        List<Dealer> dealers = dealerService.getDealers();

        // Convert to an ObservableList
        ObservableList<Dealer> dealerList = FXCollections.observableArrayList(dealers);

        // Set the items in the ComboBox
        dealerComboBox.setItems(dealerList);

        // Use a StringConverter to display only the dealer name
        dealerComboBox.setConverter(new StringConverter<Dealer>() {
            @Override
            public String toString(Dealer dealer) {
                return dealer != null ? dealer.getName() : "";
            }

            @Override
            public Dealer fromString(String string) {
                // Not needed in this case
                return null;
            }
        });
    }
    private void selectFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");

        // Optional: Set initial directory
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Open the folder chooser dialog
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            // Set the selected folder path to the TextField
            csvPathInput.setText(selectedDirectory.getAbsolutePath());
        }
    }


}
