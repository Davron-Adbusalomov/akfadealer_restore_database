package duol.restore.akfadealer_restore_database.service;

import duol.restore.akfadealer_restore_database.config.DatabaseConfig;
import duol.restore.akfadealer_restore_database.model.Dealer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DealerService {

    // Method to fetch the list of dealers
    public List<Dealer> getDealers() {
        List<Dealer> dealers = new ArrayList<>();

        String query = "SELECT id, name, dealercode FROM akfadealer-web.dealer"; // Adjust the query based on your table schema

        try (Connection connection = DatabaseConfig.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Dealer dealer = new Dealer();
                dealer.setId(resultSet.getInt("id"));
                dealer.setName(resultSet.getString("name"));
                dealer.setCode(resultSet.getString("dealercode"));
                dealers.add(dealer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dealers;
    }

}

