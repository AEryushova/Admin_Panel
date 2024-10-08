package ru.adminlk.clinica.utils.dbUtils.dbUtils;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class DataBaseManager {
    private static final Map<String, DataSource> DATA_SOURCE_MAP = new HashMap<>();

    private DataBaseManager() {
    }

    protected static Connection getConnection(String dataSourceKey) throws SQLException {
        DataSource dataSource = DATA_SOURCE_MAP.computeIfAbsent(dataSourceKey, DataBaseManager::createDataSourceForKey);
        return dataSource.getConnection();
    }

    private static DataSource createDataSourceForKey(String key) {
        return switch (key) {
            case "cab_lab_db" -> createDataSource(
                    DataBaseConfig.CAB_LAB_DB_URL,
                    DataBaseConfig.CAB_LAB_DB_USERNAME,
                    DataBaseConfig.CAB_LAB_DB_PASSWORD
            );
            case "platform_db" -> createDataSource(
                    DataBaseConfig.PLATFORM_DB_URL,
                    DataBaseConfig.PLATFORM_DB_USERNAME,
                    DataBaseConfig.PLATFORM_DB_PASSWORD
            );
            case "lod_db" -> createDataSource(
                    DataBaseConfig.LOG_DB_URL,
                    DataBaseConfig.LOG_DB_USERNAME,
                    DataBaseConfig.LOG_DB_PASSWORD
            );
            default -> throw new IllegalArgumentException("Unsupported datasource key: " + key);
        };
    }

    private static DataSource createDataSource(String url, String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    protected static QueryRunner queryRunner(String dataSourceKey) {
        DataSource dataSource = getDataSource(dataSourceKey);
        if (dataSource == null) {
            throw new IllegalArgumentException("Datasource not found for key: " + dataSourceKey);
        }
        return new QueryRunner(dataSource);
    }

    private static DataSource getDataSource(String dataSourceKey) {
        return DATA_SOURCE_MAP.get(dataSourceKey);
    }
}
