package me.Pinger.core.scoreboard;

import me.Pinger.core.Core;
import me.Pinger.core.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

public class Scoreboard {

    private final Core core;

    public Scoreboard(Core core) {
        this.core = core;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    User user = core.getManager().getUuidUserMap().get(p.getUniqueId());
                    updateScoreboard(p,user);
                    updateIP(p);
                }
                if (infoChange == 15) {
                    infoChange = -1;
                }
                infoChange++;
            }
        }.runTaskTimer(this.core, 0L, 20L);
    }

    public void createScoreboard(Player p, User uhcPlayer) {
        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = scoreboard.registerNewObjective("practice", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        p.setScoreboard(scoreboard);
        changeScoreboard(p,uhcPlayer);
    }

    public void changeScoreboard(Player p,User uhcPlayer) {
        org.bukkit.scoreboard.Scoreboard scoreboard = p.getScoreboard();
        for (Team team : scoreboard.getTeams()) {
            team.unregister();
        }
        Objective oldObj = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        oldObj.unregister();
        if (scoreboard.getObjective(DisplaySlot.BELOW_NAME) != null) {
            scoreboard.getObjective(DisplaySlot.BELOW_NAME).unregister();
        }
        Objective obj = scoreboard.registerNewObjective("practice", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§6§lCORE");

        Team bar = scoreboard.registerNewTeam(ChatColor.GREEN.toString());
        bar.addEntry("§f");
        bar.setPrefix("§f§m---------");
        bar.setSuffix("§f§m---------");
        obj.getScore("§f").setScore(10);

        Team timer = scoreboard.registerNewTeam(ChatColor.ITALIC.toString());
        timer.addEntry("§9");
        timer.setPrefix("§fGame Time: ");
        timer.setSuffix("§600:00");
        obj.getScore("§9").setScore(9);

        Team border = scoreboard.registerNewTeam(ChatColor.DARK_BLUE.toString());
        border.addEntry("§8");
        border.setSuffix("");
        obj.getScore("§8").setScore(8);

        Team newLine3 = scoreboard.registerNewTeam(ChatColor.DARK_GRAY.toString());
        newLine3.addEntry("§7");
        obj.getScore("§7").setScore(7);

        Team kill = scoreboard.registerNewTeam(ChatColor.YELLOW.toString());
        kill.addEntry("§6");
        kill.setPrefix("§fKills: ");
        kill.setSuffix("§6" + "0");
        obj.getScore("§6").setScore(6);

        Team alive = scoreboard.registerNewTeam(ChatColor.LIGHT_PURPLE.toString());
        alive.addEntry("§5");
        alive.setPrefix("§fPlayers: ");
        obj.getScore("§5").setScore(5);

        Team line = scoreboard.registerNewTeam(ChatColor.RED.toString());
        line.addEntry("§1");
        line.setPrefix("Points: ");
        line.setSuffix("§6");
        obj.getScore("§1").setScore(4);

        Team newLine2 = scoreboard.registerNewTeam(ChatColor.BOLD.toString());
        newLine2.addEntry("§4");
        obj.getScore("§4").setScore(3);

        Team website = scoreboard.registerNewTeam(ChatColor.STRIKETHROUGH.toString());
        website.addEntry("§3");
        website.setSuffix(ChatColor.GOLD + message);
        obj.getScore("§3").setScore(2);

        Team footer = scoreboard.registerNewTeam(ChatColor.DARK_AQUA.toString());
        footer.addEntry("§2");
        footer.setPrefix("§f§m---------");
        footer.setSuffix("§f§m---------");
        obj.getScore("§2").setScore(1);
    }

    public void updateScoreboard(Player p,User user) {
        org.bukkit.scoreboard.Scoreboard scoreboard = p.getScoreboard();

        Team team = scoreboard.getTeam(ChatColor.GREEN.toString());
        team.setSuffix(user.getSingular().getName());
    }
    int infoChange = -1;

    private String message = ChatColor.GOLD + "fazon.gg";

    private void updateIP(Player p) {
        org.bukkit.scoreboard.Scoreboard scoreboard = p.getScoreboard();
        Team pl = scoreboard.getTeam(ChatColor.STRIKETHROUGH.toString());
        if (infoChange == 0) {
            pl.setSuffix(ChatColor.GOLD + "fazon.gg");
            message = ChatColor.GOLD + "fazon.gg";
        } else if (infoChange == 10) {
            pl.setSuffix(ChatColor.GOLD + "@FazonGG");
            message = ChatColor.GOLD + "@FazonGG";
        }
    }

}
