//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package duol.restore.akfadealer_restore_database.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class CopyUtil {
    public CopyUtil() {
    }

    public static long copyFromFile(Connection connection, String filePath, String tableName) throws SQLException, IOException {
        long result = -1L;
        FileInputStream fileInputStream = null;

        try {
            CopyManager copyManager = new CopyManager((BaseConnection)connection);
            fileInputStream = new FileInputStream(filePath);
            result = copyManager.copyIn("COPY " + tableName + " FROM STDIN with (DELIMITER ',', ENCODING 'utf-8', FORMAT csv, HEADER)", fileInputStream);
        } catch (SQLException var15) {
            var15.printStackTrace();
            throw new SQLException(var15);
        } catch (IOException var16) {
            var16.printStackTrace();
            throw new IOException(var16);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                    throw new IOException(var14);
                }
            }

        }

        return result;
    }

    public static long copyToFile(Connection connection, String filePath, String tableOrQuery) throws SQLException, IOException {
        long result = -1L;
        FileOutputStream fileOutputStream = null;

        try {
            CopyManager copyManager = new CopyManager((BaseConnection)connection);
            fileOutputStream = new FileOutputStream(filePath);
            result = copyManager.copyOut("COPY " + tableOrQuery + " TO STDOUT with (DELIMITER ',', ENCODING 'utf-8', FORMAT csv, HEADER)", fileOutputStream);
        } catch (SQLException var15) {
            var15.printStackTrace();
            throw new SQLException(var15);
        } catch (IOException var16) {
            var16.printStackTrace();
            throw new IOException(var16);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                    throw new IOException(var14);
                }
            }

        }

        return result;
    }
}
