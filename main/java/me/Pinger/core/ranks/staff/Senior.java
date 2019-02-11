package me.Pinger.core.ranks.staff;

import me.Pinger.core.ranks.Rank;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Senior extends Rank {

    @Override
    public List<String> getPermissions() {
        List<String> permissions = new ArrayList<>();
        permissions.add("rank.color");
        permissions.add("rank.ban");
        permissions.add("rank.staffchat");
        permissions.add("rank.fly");
        permissions.add("rank.find");
        permissions.add("rank.unban");
        permissions.add("rank.kick");
        permissions.add("rank.mute");
        permissions.add("rank.unmute");
        permissions.add("rank.globalmute");
        permissions.add("rank.report");
        permissions.add("rank.ss");
        permissions.add("rank.ipban");


        return permissions;
    }

    @Override
    public String getPrefix() {
        return ChatColor.DARK_PURPLE + "[Senior] ";
    }

    @Override
    public String getName() {
        return ChatColor.DARK_PURPLE + "Senior";
    }

    @Override
    public String getDefaultName() {
        return "senior";
    }

    @Override
    public int getPower() {
        return 6;
    }
}
