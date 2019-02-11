package me.Pinger.core.user;

import me.Pinger.core.Core;
import me.Pinger.core.ranks.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.permissions.PermissionAttachment;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class User {

    private UUID uuid;
    private Core core;

    private List<Rank> rankList = new ArrayList<>();

    private int bans;
    private int mutes;
    private int kicks;

    private String color;

    private int id;

    public User(UUID uuid, Core core) {
        this.uuid = uuid;
        this.core = core;
    }

    /**
     * Checks if the player exists in the database
     * @return True if exist,false if it doesnt.
     */

    public boolean exists() {
         try {
             PreparedStatement preparedStatement = this.core.getMySQL().getConnection().prepareStatement("SELECT * FROM player_data WHERE uuid = ?;");
             preparedStatement.setString(1,this.uuid.toString());
             preparedStatement.executeQuery();

             ResultSet resultSet = preparedStatement.getResultSet();
             if (resultSet.isBeforeFirst()) {
                 while (resultSet.next()) {
                     return true;
                 }
             }
             preparedStatement.close();
             resultSet.close();
         } catch (SQLException e) {
             Bukkit.getLogger().log(Level.WARNING,"[MYSQL] Failed to select from the database for the player: " + this.uuid);
             Bukkit.getLogger().log(Level.WARNING,"[MYSQL] Reason: " + e.getMessage());
         }
         return false;
    }

    public boolean loadUser() {
        try {
            PreparedStatement preparedStatement = this.core.getMySQL().getConnection().prepareStatement("SELECT * FROM player_data WHERE uuid = ?;");
            preparedStatement.setString(1,this.uuid.toString());
            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    this.color = resultSet.getString("color");
                    this.id = resultSet.getInt("id");
                }
            }
            preparedStatement.close();
            resultSet.close();

            PreparedStatement preparedStatement1 = this.core.getMySQL().getConnection().prepareStatement("SELECT * FROM player_groups WHERE id = ?");
            preparedStatement1.setInt(1,id);
            preparedStatement1.executeQuery();

            ResultSet resultSet1 = preparedStatement1.getResultSet();
            if (resultSet1.isBeforeFirst()) {
                while (resultSet1.next()) {
                    Rank rank = this.core.getManager().getRankMap().get(resultSet.getString("ranks"));
                    rankList.add(rank);
                }
            }
            preparedStatement1.close();
            resultSet1.close();
        } catch (SQLException e) {
            Bukkit.getLogger().log(Level.WARNING,"[MYSQL] Failed to select from the database for the player: " + this.uuid);
            Bukkit.getLogger().log(Level.WARNING,"[MYSQL] Reason: " + e.getMessage());
        }
        PermissionAttachment permissionAttachment = Bukkit.getPlayer(uuid).addAttachment(this.core);
        for (Rank rank : rankList) {
            for (String permission : rank.getPermissions()) {
                permissionAttachment.setPermission(permission,true);
            }
        }
        return false;
    }

    public void createUser() {
        try {
            PreparedStatement preparedStatement = this.core.getMySQL().getConnection().prepareStatement("INSERT INTO player_data_ WHERE uuid = ?;");
            preparedStatement.setString(1,this.uuid.toString());
            preparedStatement.setInt(2,id);
            preparedStatement.setString(3,"WHITE");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects the rank with most votes
     * Using this when player is typing or on scoreboard so it doesn't display multiple ranks
     * @return The rank
     */

    public Rank getSingular() {
        return rankList.stream().sorted((o1, o2) -> Integer.compare(o1.getPower(),o2.getPower())).toArray(Rank[]::new)[0];
    }

    /**
     * Adds a rank to the rankList ArrayList
     * @param rank The rank that gets added
     */

    public void addRank(Rank rank) {
        rankList.add(rank);
        Bukkit.getPlayer(uuid).sendMessage(ChatColor.GREEN + "Updated your permissions.");
    }

    /**
     * Removes a rank from the rankList ArrayList
     * @param rank The removed rank
     */

    public void removeRank(Rank rank) {
        rankList.remove(rank);
        Bukkit.getPlayer(uuid).sendMessage(ChatColor.GREEN + "Updated your permissions.");
    }

    /**
     * Gets the uuid of the user
     * @return UUID returned
     */

    public UUID getUniqueId() {
        return uuid;
    }

    /**
     * Sets a color for the user (Used in
     * @param color
     */

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * List of ranks that the user has
     * @return hm
     */

    public List<Rank> getRankList() {
        return rankList;
    }

    public int getId() {
        try {
            PreparedStatement preparedStatement = this.core.getMySQL().getConnection().prepareStatement("SELECT * FROM player_data WHERE uuid = ?;");
            preparedStatement.setString(1,this.uuid.toString());
            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
