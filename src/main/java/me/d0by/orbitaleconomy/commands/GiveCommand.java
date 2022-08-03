package me.d0by.orbitaleconomy.commands;

import me.d0by.orbitaleconomy.Config;
import me.d0by.orbitaleconomy.OrbitalEconomy;
import me.d0by.orbitaleconomy.profile.Profile;
import me.d0by.orbitaleconomy.utils.Common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            Common.tell(sender, Config.NO_PERM);
            return true;
        }

        if (args.length < 2) {
            for (String line : Config.HELP) {
                Common.tell(sender, line);
            }
            return true;
        }

        Profile source = OrbitalEconomy.getInstance().getProfileManager().getProfile(player.getName());
        Profile target = OrbitalEconomy.getInstance().getProfileManager().getProfile(args[0]);
        double amount = Double.parseDouble(args[1]);
        if (amount <= 0) {
            Common.tell(sender, Config.INVALID_AMOUNT);
            return true;
        }

        if (source.getMoney() < amount) {
            Common.tell(sender, Config.NOT_ENOUGH);
            return true;
        }

        source.removeMoney(amount);
        target.addMoney(amount);
        source.save();
        target.save();
        Common.tell(sender, Config.GIVEN, amount, args[0]);
        return true;
    }

}
