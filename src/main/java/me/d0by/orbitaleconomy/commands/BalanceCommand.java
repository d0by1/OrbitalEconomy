package me.d0by.orbitaleconomy.commands;

import me.d0by.orbitaleconomy.Config;
import me.d0by.orbitaleconomy.OrbitalEconomy;
import me.d0by.orbitaleconomy.profile.Profile;
import me.d0by.orbitaleconomy.utils.Common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            Common.tell(sender, Config.NO_PERM);
            return true;
        }

        Profile profile;
        if (args.length == 0) {
            profile = OrbitalEconomy.getInstance().getProfileManager().getProfile(player.getName());
            Common.tell(sender, Config.BALANCE, profile.getMoney());
        } else {
            profile = OrbitalEconomy.getInstance().getProfileManager().getProfile(args[0]);
            Common.tell(sender, Config.BALANCE_OTHER, args[0], profile.getMoney());
        }
        return true;
    }

}
