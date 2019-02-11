package me.Pinger.core.sql;

import org.bukkit.Bukkit;

import javax.persistence.PrePersist;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class MySQL {

    private Connection connection;

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public MySQL(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    /**
     * Opens the SQL connection
     */

    public void openConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.WARNING,"[MYSQL] Failed to load sql: " + e.getMessage());
        }
    }

    /**
     * If a table name called "player_data" doens't exist,it will create one...
     * Same with 'player_groups"
     */

    public void openTables() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `player_data`(uuid VARCHAR(36),`id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,`color` VARCHAR(36));");
            preparedStatement.executeUpdate();
            preparedStatement.close();

            PreparedStatement preparedStatement1 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `player_groups`(id BIGINT(4),rank VARCHAR(108));");
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.WARNING,"[MYSQL] FAILED TO CREATE TABLES: " + e.getMessage());
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Connection getConnection() {
        return connection;
    }
}
