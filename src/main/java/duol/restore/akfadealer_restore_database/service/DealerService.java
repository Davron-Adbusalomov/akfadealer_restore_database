package duol.restore.akfadealer_restore_database.service;

import duol.restore.akfadealer_restore_database.config.DatabaseConfig;
import duol.restore.akfadealer_restore_database.model.Dealer;
import javafx.scene.control.Alert;

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

//    public void copyTablesToCSVs(String absolutePath) {
//        String query = """
//        COPY (
//            SELECT
//                cid AS id, reg_date AS created, update_date AS updated, status,
//                name, info, access_to_change_data, access_to_change_data_with_day, permissions, server_id, TRUE AS synced
//            FROM
//                akfadealer_web.t_dealer_role
//            WHERE
//                dealer_id = 171
//        ) TO '%st_dealer_role.csv' WITH (DELIMITER ',', ENCODING 'UTF8', FORMAT CSV, HEADER);
//
//        COPY (
//            SELECT
//                cid AS id, reg_date AS created, update_date AS updated, status,
//                reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, name, login, pass, info, server_id, TRUE AS synced
//            FROM
//                akfadealer_web.t_dealer_user
//            WHERE
//                dealer_id = 171
//        ) TO '%st_dealer_user.csv' WITH (DELIMITER ',', ENCODING 'UTF8', FORMAT CSV, HEADER);
//
//        COPY (
//            SELECT
//                cid AS id, reg_date AS created, update_date AS updated, status,
//                reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, printable_name, first_name, last_name, patronymic,
//                passport_series, passport_number, birthday, phone, info, region_id AS region_sid, series AS series_sids,
//                opening_balance, account_type, type, discount_card, currency_id AS currency_sid, currency_rate_id AS currency_rate_sid,
//                checked_type, company_name, branch, server_id, TRUE AS synced
//            FROM
//                akfadealer_web.t_dealer_client
//            WHERE
//                dealer_id = 171
//        ) TO '%st_dealer_client.csv' WITH (DELIMITER ',', ENCODING 'UTF8', FORMAT CSV, HEADER);
//
//        COPY (
//            SELECT
//                cid AS id, reg_date AS created, update_date AS updated, status,
//                reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, date, from_client_cid AS from_client_id,
//                to_client_cid AS to_client_id, type, amount, action_type, info, currency_id AS currency_sid,
//                currency_rate_id AS currency_rate_sid, bind_cid AS bind_id, last_bind, server_id, TRUE AS synced
//            FROM
//                akfadealer_web.t_payment_receipt
//            WHERE
//                dealer_id = 171
//        ) TO '%st_payment_receipt.csv' WITH (DELIMITER ',', ENCODING 'UTF8', FORMAT CSV, HEADER);
//
//        COPY (
//            SELECT
//                cid AS id, reg_date AS created, update_date AS updated, status,
//                reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, date, type, client_cid AS client_id,
//                discount_card, organization_type, server_id, TRUE AS synced
//            FROM
//                akfadealer_web.t_invoice
//            WHERE
//                dealer_id = 171
//        ) TO '%st_invoice.csv' WITH (DELIMITER ',', ENCODING 'UTF8', FORMAT CSV, HEADER);
//
//        COPY (
//            SELECT
//                cid AS id, reg_date AS created, update_date AS updated, status,
//                reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, invoice_cid AS invoice_id,
//                product_id AS product_sid, qty, alt_qty, actual_qty, rate, amount, income, action_type, currency_id AS currency_sid,
//                currency_rate_id AS currency_rate_sid, bind_cid AS bind_id, last_bind, discount_percent, discount_amount,
//                price_discount, price_discount_amount, server_id, TRUE AS synced
//            FROM
//                akfadealer_web.t_invoice_item
//            WHERE
//                dealer_id = 171
//        ) TO '%st_invoice_item.csv' WITH (DELIMITER ',', ENCODING 'UTF8', FORMAT CSV, HEADER);
//
//        COPY (
//            SELECT
//                cid AS id, reg_date AS created, update_date AS updated, status,
//                date, terminal_id AS terminal_sid, class_name, exception_msg, server_id, TRUE AS synced
//            FROM
//                akfadealer_web.t_log_history
//            WHERE
//                dealer_id = 171
//        ) TO '%st_log_history.csv' WITH (DELIMITER ',', ENCODING 'UTF8', FORMAT CSV, HEADER);
//    """;
//
//        query = String.format(query, absolutePath, absolutePath, absolutePath, absolutePath, absolutePath, absolutePath, absolutePath);
//
//        try (Connection connection = DatabaseConfig.getDatabaseConnection();
//             Statement statement = connection.createStatement()) {
//            statement.execute(query);
//            System.out.println("CSV export completed successfully.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


//    public void copyTablesToCSVs(String absolutePath) {
//        // Ensure absolutePath ends with a separator
//        if (!absolutePath.endsWith("\\")) {
//            absolutePath += "\\";
//        }
//
//        String[] queries = new String[]{
//                String.format("""
//                        COPY (
//                            SELECT
//                                cid AS id, reg_date AS created, update_date AS updated, status,
//                                name, info, access_to_change_data, access_to_change_data_with_day, permissions, server_id, TRUE AS synced
//                            FROM
//                                akfadealer_web.t_dealer_role
//                            WHERE
//                                dealer_id = 171
//                        ) TO '%st_dealer_role.csv' WITH (DELIMITER ',', ENCODING 'UTF8', FORMAT CSV, HEADER);
//                        """, absolutePath),
//
//                String.format("""
//                        COPY (
//                            SELECT
//                                cid AS id, reg_date AS created, update_date AS updated, status,
//                                reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, name, login, pass, info, server_id, TRUE AS synced
//                            FROM
//                                akfadealer_web.t_dealer_user
//                            WHERE
//                                dealer_id = 171
//                        ) TO '%st_dealer_user.csv' WITH (DELIMITER ',', ENCODING 'UTF8', FORMAT CSV, HEADER);
//                        """, absolutePath),
//
//                // Repeat for other tables
//                String.format("""
//                        COPY (
//                            SELECT
//                                cid AS id, reg_date AS created, update_date AS updated, status,
//                                reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, printable_name, first_name, last_name, patronymic,
//                                passport_series, passport_number, birthday, phone, info, region_id AS region_sid, series AS series_sids,
//                                opening_balance, account_type, type, discount_card, currency_id AS currency_sid, currency_rate_id AS currency_rate_sid,
//                                checked_type, company_name, branch, server_id, TRUE AS synced
//                            FROM
//                                akfadealer_web.t_dealer_client
//                            WHERE
//                                dealer_id = 171
//                        ) TO '%st_dealer_client.csv' WITH (DELIMITER ',', ENCODING 'UTF8', FORMAT CSV, HEADER);
//                        """, absolutePath),
//        };
//
//        // Execute each query
//        try (Connection connection = DatabaseConfig.getDatabaseConnection();
//             Statement statement = connection.createStatement()) {
//            for (String query : queries) {
//                statement.execute(query);
//            }
//            System.out.println("CSV export completed successfully.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void exportToCSV() {
        String query = "SELECT * FROM akfadealer_web.dealer";
        String filePath = "D:\\Games\\akfa_dealer_web_dev2akfadealer_web_dealer.csv"; // CSV fayl yo'li

        try (Connection conn = DatabaseConfig.getDatabaseConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             FileWriter writer = new FileWriter(filePath)) {

            // CSV faylga ustun nomlarini yozish
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                writer.append(rs.getMetaData().getColumnName(i));
                if (i < columnCount) writer.append(',');
            }
            writer.append('\n');

            // Ma'lumotlarni CSV faylga yozish
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    writer.append(rs.getString(i));
                    if (i < columnCount) writer.append(',');
                }
                writer.append('\n');
            }

            // Success Alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Successful");
            alert.setHeaderText("CSV Export");
            alert.setContentText("Data has been successfully exported to " + filePath);
            alert.showAndWait();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Error Alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export Failed");
            alert.setHeaderText("Error Exporting Data");
            alert.setContentText("An error occurred while exporting data to CSV.");
            alert.showAndWait();
        }
    }


}

