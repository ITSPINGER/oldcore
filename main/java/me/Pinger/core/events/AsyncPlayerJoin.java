package me.Pinger.core.events;

import me.Pinger.core.Core;
import me.Pinger.core.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class AsyncPlayerJoin implements Listener {

    private Core core;

    public AsyncPlayerJoin(Core core) {
        this.core = core;
    }

    @EventHandler
     public void onJoin(AsyncPlayerPreLoginEvent e) {
        UUID uuid = e.getUniqueId();
        User user = new User(e.getUniqueId(),core);
        core.getManager().getUuidUserMap().put(uuid,user);
        if (user.exists()) {
            user.loadUser();
        } else {
            return;
        }
    }
}
