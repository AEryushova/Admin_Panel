package admin.utils.dbUtils;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class DataSourceManager {
    private static Map<String, DataSource> dataSourceMap = new HashMap<>();

    private DataSourceManager() {
    }

    public static Connection getConnection(String dataSourceKey) throws SQLException {
        DataSource dataSource = dataSourceMap.computeIfAbsent(dataSourceKey, key -> {
            switch (key) {
                case "cab_lab_db":
                    return createDataSource(
                            DatabaseConfig.CAB_LAB_DB_URL,
                            DatabaseConfig.CAB_LAB_DB_USERNAME,
                            DatabaseConfig.CAB_LAB_DB_PASSWORD
                    );
                case "platform_db":
                    return createDataSource(
                            DatabaseConfig.PLATFORM_DB_URL,
                            DatabaseConfig.PLATFORM_DB_USERNAME,
                            DatabaseConfig.PLATFORM_DB_PASSWORD
                    );
                case "medical_record_db":
                    return createDataSource(
                            DatabaseConfig.MEDICAL_RECORD_DB_URL,
                            DatabaseConfig.MEDICAL_RECORD_DB_USERNAME,
                            DatabaseConfig.MEDICAL_RECORD_DB_PASSWORD
                    );
                default:
                    throw new IllegalArgumentException("Unsupported datasource key: " + key);
            }
        });

        return dataSource.getConnection();
    }

    private static DataSource createDataSource(String url, String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    public static QueryRunner queryRunner(String dataSourceKey) {
        return new QueryRunner(getDataSource(dataSourceKey));
    }

    private static DataSource getDataSource(String dataSourceKey) {
        return dataSourceMap.get(dataSourceKey);
    }
}

