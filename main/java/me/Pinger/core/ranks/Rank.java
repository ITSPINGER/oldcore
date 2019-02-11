package me.Pinger.core.ranks;

import java.util.List;

public abstract class Rank {

    /**
     * List of the permission a rank is going to have
     * What we're going to do is for each rank we're gonna put multiple String in a List and just add them to an attachement
     * @return A string list of permissions set
     */

    public abstract List<String> getPermissions();

    /**
     * A prefix the rank is going to have
     * We're using the prefix when player is typing in the chat
     * @return We're returning the prefix
     */

    public abstract String getPrefix();

    /**
     * The name the rank is going to have
     * We're using the name on players scoreboard
     * @return We're returning the name
     */

    public abstract String getName();

    /**
     * Default name of the rank
     */

    public abstract String getDefaultName();

    /**
     * The power of the rank
     * This is used when a rank is displayed we're gonna compare their values and display the one with most power
     * @return The power int
     */

    public abstract int getPower();

}
