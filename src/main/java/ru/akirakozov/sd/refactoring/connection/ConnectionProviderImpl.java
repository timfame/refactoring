package ru.akirakozov.sd.refactoring.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProviderImpl implements ConnectionProvider{
    private final String url;

    public ConnectionProviderImpl(String url) {
        this.url = url;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
