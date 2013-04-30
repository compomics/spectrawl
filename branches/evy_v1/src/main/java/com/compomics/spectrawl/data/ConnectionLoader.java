package com.compomics.spectrawl.data;

import com.compomics.spectrawl.config.PropertiesConfigurationHolder;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA. User: niels Date: 8/03/12 Time: 9:33 To change this
 * template use File | Settings | File Templates.
 */
@Component("connectionLoader")
public class ConnectionLoader {

    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(ConnectionLoader.class);
    private Connection connection;

    /**
     * If there's an open connection, close it.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Get a connection. If there's no connection, get one from the
     * DriverManager.
     *
     * @return the connection
     */
    public Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName(PropertiesConfigurationHolder.getInstance().getString("DRIVER"));
                String url = PropertiesConfigurationHolder.getInstance().getString("URL");
                String username = PropertiesConfigurationHolder.getInstance().getString("USERNAME");
                String password = PropertiesConfigurationHolder.getInstance().getString("PASSWORD");

                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return connection;
    }
}
