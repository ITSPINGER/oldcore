package me.Pinger.core.executors;

import me.Pinger.core.Core;
import me.Pinger.core.ranks.Rank;
import me.Pinger.core.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RankAddExecutor implements CommandExecutor {

    private Core core;

    public RankAddExecutor(Core core) {
        this.core = core;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("rank.rankadd")) {
            commandSender.sendMessage(core.permission);
            return true;
        } else {
            if (strings.length == 0) {
                commandSender.sendMessage(core.usage + "/rankadd <player> <groupName>");
                return true;
            } else {
                OfflinePlayer player = Bukkit.getOfflinePlayer(strings[0]);
                if (strings.length == 1) {
                    commandSender.sendMessage(ChatColor.RED + "Not enough arguments.");
                    return true;
                } else {
                    for (Rank rank1 : this.core.getManager().getRankMap().values()) {
                        if (strings[1].equalsIgnoreCase(rank1.getDefaultName())) {
                            Rank rank = rank1;
                                User user = this.core.getManager().getUuidUserMap().get(player.getUniqueId());
                                if (user.getRankList().contains(rank)) {
                                    commandSender.sendMessage(ChatColor.RED + "This player already has this rank.");
                                } else {
                                    user.addRank(rank);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
    }
