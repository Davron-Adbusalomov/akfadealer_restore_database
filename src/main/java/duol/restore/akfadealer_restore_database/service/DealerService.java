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
        tableMap.put("dealer_role", "(SELECT \n" +
                " cid AS id, reg_date AS created, update_date AS updated, status, \n" +
                " name, info, access_to_change_data, access_to_change_data_with_day, permissions, server_id, TRUE AS synced \n" +
                " FROM \n" +
                " akfadealer_web.t_dealer_role\n" +
                " WHERE\n" +
                " dealer_id=%d)");
        tableMap.put("dealer_user", "(SELECT \n" +
                " cid AS id, reg_date AS created, update_date AS updated, status, \n" +
                " reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, name, login, pass, info, server_id, TRUE AS synced\n" +
                " FROM \n" +
                " akfadealer_web.t_dealer_user\n" +
                " WHERE\n" +
                " dealer_id=%d)");
        tableMap.put("dealer_client", "(SELECT \n" +
                " cid AS id, reg_date AS created, update_date AS updated, status, \n" +
                " reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, printable_name, first_name, last_name, patronymic, passport_series, passport_number, birthday, phone, info, region_id AS region_sid, series AS series_sids, opening_balance, account_type, type, discount_card, currency_id AS currency_sid, checked_type, company_name, branch, server_id, TRUE AS synced\n" +
                " FROM \n" +
                " akfadealer_web.t_dealer_client\n" +
                " WHERE\n" +
                " dealer_id=%d)");
        tableMap.put("payment_receipt", "(SELECT \n" +
                " cid AS id, reg_date AS created, update_date AS updated, status, \n" +
                " reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, date, from_client_cid AS from_client_id, to_client_cid AS to_client_id, type, amount, action_type, info, currency_id AS currency_sid, bind_cid AS bind_id, last_bind, server_id, TRUE AS synced\n" +
                " FROM \n" +
                " akfadealer_web.t_payment_receipt\n" +
                " WHERE\n" +
                " dealer_id=%d)");
        tableMap.put("invoice", "(SELECT \n" +
                " cid AS id, reg_date AS created, update_date AS updated, status, \n" +
                " reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, date, type, client_cid AS client_id, discount_card, organization_type, server_id, TRUE AS synced\n" +
                " FROM \n" +
                " akfadealer_web.t_invoice\n" +
                " WHERE\n" +
                " dealer_id=%d)");
        tableMap.put("invoice_item", "(SELECT \n" +
                " cid AS id, reg_date AS created, update_date AS updated, status, \n" +
                " reg_user_cid AS created_user_id, edit_user_cid AS updated_user_id, invoice_cid AS invoice_id, product_id AS product_sid, qty, alt_qty, actual_qty, rate, amount, income, action_type, currency_id AS currency_sid, bind_cid AS bind_id, last_bind, discount_percent, discount_amount, price_discount, price_discount_amount, server_id, TRUE AS synced\n" +
                " FROM \n" +
                " akfadealer_web.t_invoice_item\n" +
                " WHERE\n" +
                " dealer_id=%d)");
        tableMap.put("log_history", "(SELECT \n" +
                " cid AS id, reg_date AS created, update_date AS updated, status, \n" +
                " date, terminal_id AS terminal_sid, class_name, exception_msg, server_id, TRUE AS synced\n" +
                " FROM \n" +
                " akfadealer_web.t_log_history\n" +
                " WHERE\n" +
                " dealer_id=%d)");
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
            System.out.println(entry.getKey().toUpperCase(Locale.ROOT) + " CSV export STARTING .....");
            File file = new File(path + "/" + entry.getKey() + "_" + dealerId + "_" + LocalDate.now() + ".csv");
            CopyUtil.copyToFile(DatabaseConfig.getDatabaseConnection(), file.getAbsolutePath(), String.format(entry.getValue(), dealerId));
            System.out.println(entry.getKey().toUpperCase(Locale.ROOT) + " CSV export completed successfully.");
        }
    }
}

