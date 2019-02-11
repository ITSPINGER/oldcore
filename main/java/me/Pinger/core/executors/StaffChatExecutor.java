package me.Pinger.core.executors;

import me.Pinger.core.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;

public class StaffChatExecutor implements CommandExecutor {

    private Core core;

    public StaffChatExecutor(Core core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("rank.staffchat")) {
            commandSender.sendMessage(core.permission);
            return true;
        } else {
            if (strings.length == 0) {
                commandSender.sendMessage(ChatColor.RED + "Please specify a message.");
                return true;
            } else {
                String sb = " ";
                for (int i = 0; i < strings.length; i++) {
                    sb = sb + strings[i] + "";
                }
                String message = ChatColor.RED + "[STAFF] [" + 2 + "]" + ChatColor.BLUE + commandSender.getName() + ": " + sb;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try (Jedis jedis = core.getJedisPool().getResource()) {
                            jedis.publish("chat", message);
                            cancel();
                        }
                    }
                }.runTaskAsynchronously(this.core);
            }
            return true;
        }
    }
}
