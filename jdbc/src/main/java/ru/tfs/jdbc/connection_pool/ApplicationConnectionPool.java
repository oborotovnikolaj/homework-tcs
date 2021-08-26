package ru.tfs.jdbc.connection_pool;

import java.sql.SQLException;

public class ApplicationConnectionPool {

    private static final String URL = "jdbc:postgresql://192.168.99.100:5432/postgres";
    private static final String USER = "me";
    private static final String PASSWORD = "1234";

    private static ConnectionPool instance;

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            try {
                instance = BasicConnectionPool.create(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException("Не удалость создать пулл потоков", e);
            }
        }
        return instance;
    }


}