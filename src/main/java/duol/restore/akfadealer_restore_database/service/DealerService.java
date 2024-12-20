package duol.restore.akfadealer_restore_database.service;

import duol.restore.akfadealer_restore_database.config.CopyUtil;
import duol.restore.akfadealer_restore_database.config.DatabaseConfig;
import duol.restore.akfadealer_restore_database.model.Dealer;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DealerService {
    private static Map<String, String> tableMap = new HashMap<>();
    static {
        tableMap.put("dealer_role", "t_dealer_role");
        tableMap.put("dealer_user", "t_dealer_user");
        tableMap.put("dealer_client", "t_dealer_client");
        tableMap.put("payment_receipt", "t_payment_receipt");
        tableMap.put("invoice", "t_invoice");
        tableMap.put("invoice_item", "t_invoice_item");
        tableMap.put("log_history", "t_log_history");
    }

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

    public void exportToCSV(String path, Long dealerId) throws SQLException, IOException {
        for (Map.Entry<String, String> entry: tableMap.entrySet()) {
            File file = new File(path + "/" + entry.getKey() + "_" + dealerId + "_" + LocalDate.now() + ".csv");
            CopyUtil.copyToFile(DatabaseConfig.getDatabaseConnection(), file.getAbsolutePath(), "(select * from akfadealer_web."+entry.getValue()+" where dealer_id=101)");
            System.out.println(entry.getKey().toUpperCase(Locale.ROOT) + " CSV export completed successfully.");
        }
    }
}

