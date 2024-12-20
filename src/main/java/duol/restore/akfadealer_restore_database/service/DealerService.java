package duol.restore.akfadealer_restore_database.service;

import duol.restore.akfadealer_restore_database.config.CopyUtil;
import duol.restore.akfadealer_restore_database.config.DatabaseConfig;
import duol.restore.akfadealer_restore_database.model.Dealer;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DealerService {

    public List<Dealer> getDealers() {
        List<Dealer> dealers = new ArrayList<>();

        String query = "SELECT id, name, dealercode FROM akfadealer_web.dealer";

        try (Connection connection = DatabaseConfig.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Dealer dealer = new Dealer();
                dealer.setId(resultSet.getLong("id"));
                dealer.setName(resultSet.getString("name"));
                dealer.setCode(resultSet.getString("dealercode"));
                dealers.add(dealer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dealers;
    }

    public void makeLastSyncNull(Long dealerId) {
        String query = "UPDATE akfadealer_web.dealer SET last_synced_date = NULL WHERE id = ?";

        try (Connection connection = DatabaseConfig.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, dealerId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void exportToCSV(String path) throws SQLException, IOException {
        File file = new File(path + "/invoice_item.csv");
        CopyUtil.copyToFile(DatabaseConfig.getDatabaseConnection(), file.getAbsolutePath(), "(select * from akfadealer_web.t_invoice_item where dealer_id=101)");
        System.out.println("CSV export completed successfully.");
    }
}

