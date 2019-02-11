package me.Pinger.core;

import me.Pinger.core.events.AsyncPlayerJoin;
import me.Pinger.core.executors.RankAddExecutor;
import me.Pinger.core.executors.StaffChatExecutor;
import me.Pinger.core.manager.Manager;
import me.Pinger.core.sql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.sql.SQLException;
import java.util.logging.Level;

public class Core extends JavaPlugin {

    private Manager manager;

    private JedisPool jedisPool;
    private MySQL mySQL;

    public final String permission = "§cError » You do not have permission to execute this command!";
    public final String usage = "§cUsage » ";

    public void onEnable() {
        loadConfig();
        setMySQL(new MySQL(getConfig().getString("host"),getConfig().getInt("port"),getConfig().getString("database"),getConfig().getString("username"),getConfig().getString("password")));
        jedisPool = new JedisPool(getConfig().getString("redis.host"),getConfig().getInt("redis.port"));
        loadModules();
        registerCommands();
        registerEvents();
        loadDatabases();
    }

    /**
     * Loads databased
     * Opens the connection for SQL and setups the tables
     * Registers REDIS/JEDIS
     */

    public void loadDatabases() {
        mySQL.openConnection();
        mySQL.openTables();
        try {
            new BukkitRunnable() {
                @Override
                public void run() {
                        JedisPubSub jedisPubSub = new JedisPubSub() {
                            @Override
                            public void onMessage(String channel, String message) {
                                if (channel.equalsIgnoreCase("chat")) {
                                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                                        if (player1.hasPermission("rank.staffchat")) {
                                            player1.sendMessage(message);
                                        }
                                    }
                                }
                            }
                        };
                        jedisPool.getResource().subscribe(jedisPubSub, "chat");
                        cancel();
                }
            }.runTaskAsynchronously(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onDisable() {
        // Close the connection whenever the server stops
        jedisPool.getResource().close();
        jedisPool.close();
        try {
            getMySQL().getConnection().close();
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.WARNING,"[MYSQL] Failed to close the connection: " + e.getMessage());
        }
    }


    /**
     * Registers all of the commands for ingame
     */

    private void registerCommands() {
        getCommand("staffchat").setExecutor(new StaffChatExecutor(this));
        getCommand("rankadd").setExecutor(new RankAddExecutor(this));
    }

    /**
     * Adds config defaults for redis and SQL
     */

    private void loadConfig() {
        getConfig().addDefault("redis.host","localhost");
        getConfig().addDefault("redis.port",6379);
        getConfig().addDefault("host","localhost");
        getConfig().addDefault("port",3306);
        getConfig().addDefault("database","core");
        getConfig().addDefault("username","root");
        getConfig().addDefault("password","");
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerJoin(this),this);
    }


    private void loadModules() {
        this.manager = new Manager(this);
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    private void setMySQL(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public Manager getManager() {
        return manager;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }
}
