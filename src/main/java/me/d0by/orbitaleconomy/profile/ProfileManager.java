package me.d0by.orbitaleconomy.profile;

import me.d0by.orbitaleconomy.OrbitalEconomy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProfileManager {

    private final @NotNull Map<String, Profile> profileMap;

    public ProfileManager() {
        this.profileMap = new ConcurrentHashMap<>();
        this.reload();
    }

    public void reload() {
        this.shutdown();

        // Create profile for all online players
        Bukkit.getOnlinePlayers().stream().map(Player::getName).forEach(this::createProfile);
    }

    public void shutdown() {
        this.profileMap.clear();
    }

    /**
     * Create a profile for a player with the given name. If the given player
     * already has a profile, the existing profile will be returned.
     *
     * @param name The name of the player.
     * @return The profile of the player.
     */
    @NotNull
    public Profile createProfile(@NotNull String name) {
        if (this.profileMap.containsKey(name)) {
            return this.profileMap.get(name);
        }
        Profile profile = OrbitalEconomy.getInstance().getDatabase().loadProfile(name).join();
        this.profileMap.put(name, profile);
        return profile;
    }

    @NotNull
    public Profile getProfile(@NotNull String name) {
        if (this.profileMap.containsKey(name)) {
            return this.profileMap.get(name);
        }
        return createProfile(name);
    }

    public void removeProfile(@NotNull String name) {
        this.profileMap.remove(name);
    }

    @NotNull
    public Map<String, Profile> getProfiles() {
        return this.profileMap;
    }

}
