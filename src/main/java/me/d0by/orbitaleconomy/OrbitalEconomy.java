package me.d0by.orbitaleconomy;

import lombok.Getter;
import me.d0by.orbitaleconomy.commands.BalanceCommand;
import me.d0by.orbitaleconomy.commands.EarnCommand;
import me.d0by.orbitaleconomy.commands.GiveCommand;
import me.d0by.orbitaleconomy.commands.SetBalanceCommand;
import me.d0by.orbitaleconomy.database.IDatabase;
import me.d0by.orbitaleconomy.database.MySQLDatabase;
import me.d0by.orbitaleconomy.hooks.VaultEconomyViolator;
import me.d0by.orbitaleconomy.profile.ProfileListener;
import me.d0by.orbitaleconomy.profile.ProfileManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class OrbitalEconomy extends JavaPlugin {

    @Getter
    private static OrbitalEconomy instance;
    private ProfileManager profileManager;
    private IDatabase database;
    private Economy economy;

    public OrbitalEconomy() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Config.reload();

        this.database = new MySQLDatabase();
        this.profileManager = new ProfileManager();
        this.economy = new VaultEconomyViolator();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ProfileListener(), this);

        getCommand("balance").setExecutor(new BalanceCommand());
        getCommand("setbalance").setExecutor(new SetBalanceCommand());
        getCommand("earn").setExecutor(new EarnCommand());
        getCommand("give").setExecutor(new GiveCommand());

        registerEconomy();
    }

    @Override
    public void onDisable() {
        unregisterEconomy();

        this.profileManager.shutdown();
        this.database.disconnect();
    }

    private void registerEconomy() {
        Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
        if (vault == null) {
            return;
        }
        Bukkit.getServicesManager().register(Economy.class, economy, vault, ServicePriority.Highest);
        Bukkit.getServicesManager().unregister(Economy.class, economy);
    }

    private void unregisterEconomy() {
        Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
        if (vault == null) {
            return;
        }
        Bukkit.getServicesManager().unregister(Economy.class, economy);
    }

}