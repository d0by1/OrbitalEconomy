package me.d0by.orbitaleconomy.commands;

import me.d0by.orbitaleconomy.Config;
import me.d0by.orbitaleconomy.OrbitalEconomy;
import me.d0by.orbitaleconomy.profile.Profile;
import me.d0by.orbitaleconomy.utils.Common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EarnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            Common.tell(sender, Config.NO_PERM);
            return true;
        }
        int amount = Common.irand(1, 5);
        Profile profile = OrbitalEconomy.getInstance().getProfileManager().getProfile(player.getName());
        profile.addMoney(amount);
        profile.save();
        Common.tell(sender, Config.EARNED, amount);
        return true;
    }

}
