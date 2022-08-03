package me.d0by.orbitaleconomy.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.d0by.orbitaleconomy.Config;
import me.d0by.orbitaleconomy.profile.Profile;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CompletableFuture;

public class MySQLDatabase implements IDatabase {

    private HikariDataSource dataSource;

    public MySQLDatabase() {
        this.connect();

        CompletableFuture.runAsync(this::prepareTable);
    }

    @Override
    public void connect() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://%s:%s/%s".formatted(Config.MYSQL_HOST, Config.MYSQL_PORT, Config.MYSQL_DATABASE));
        config.setUsername(Config.MYSQL_USERNAME);
        config.setPassword(Config.MYSQL_PASSWORD);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("autoReconnect", "true");
        config.addDataSourceProperty("characterEncoding", "UTF-8");
        config.addDataSourceProperty("useSSL", "false");

        this.dataSource = new HikariDataSource(config);
        this.dataSource.setMaximumPoolSize(5);
        this.dataSource.setMinimumIdle(1);
    }

    @Override
    public void disconnect() {
        if (this.dataSource != null) {
            this.dataSource.close();
        }
    }

    @Override
    public CompletableFuture<Profile> loadProfile(@NotNull String name) {
        return CompletableFuture.supplyAsync(() -> {
            Profile profile = new Profile(name);
            try (Connection c = this.dataSource.getConnection();
                 PreparedStatement ps = c.prepareStatement("SELECT `money` FROM `economy_profiles` WHERE `name` = ?")
            ) {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    profile.setMoney(rs.getDouble("money"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return profile;
        });
    }

    @Override
    public void saveProfile(@NotNull Profile profile) {
        try (Connection c = this.dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "INSERT INTO `economy_profiles` (`name`, `money`) " +
                             "VALUES (?, ?) ON DUPLICATE KEY UPDATE `money` = ?"
             )
        ) {
            ps.setString(1, profile.getName());
            ps.setDouble(2, profile.getMoney());
            ps.setDouble(3, profile.getMoney());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareTable() {
        try (Connection c = dataSource.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS `economy_profiles` (" +
                             "`name` VARCHAR(16) NOT NULL, " +
                             "`money` DOUBLE NOT NULL, " +
                             "PRIMARY KEY (`name`)" +
                             ")")
        ) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
