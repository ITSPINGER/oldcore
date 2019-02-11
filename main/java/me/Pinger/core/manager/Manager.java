package me.Pinger.core.manager;

import me.Pinger.core.Core;
import me.Pinger.core.ranks.Rank;
import me.Pinger.core.ranks.staff.Senior;
import me.Pinger.core.ranks.staff.Staff;
import me.Pinger.core.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Manager {

    private Core core;

    public Manager(Core core) {
        this.core = core;
        loadRanks();
    }

    private void loadRanks() {
        rankMap.put("staff",new Staff());
        rankMap.put("senior",new Senior());
        rankMap.put("manager",new me.Pinger.core.ranks.staff.Manager());
    }

    private Map<String,Rank> rankMap = new HashMap<>();

    private Map<UUID,User> uuidUserMap = new HashMap<>();

    public Map<String, Rank> getRankMap() {
        return rankMap;
    }

    public Map<UUID, User> getUuidUserMap() {
        return uuidUserMap;
    }
}
