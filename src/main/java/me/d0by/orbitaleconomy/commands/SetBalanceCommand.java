package me.d0by.orbitaleconomy.commands;

import me.d0by.orbitaleconomy.Config;
import me.d0by.orbitaleconomy.OrbitalEconomy;
import me.d0by.orbitaleconomy.profile.Profile;
import me.d0by.orbitaleconomy.utils.Common;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetBalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            for (String line : Config.HELP) {
                Common.tell(sender, line);
            }
            return true;
        }

        Profile target = OrbitalEconomy.getInstance().getProfileManager().getProfile(args[0]);
        double amount = Double.parseDouble(args[1]);
        if (amount <= 0) {
            Common.tell(sender, Config.INVALID_AMOUNT);
            return true;
        }

        target.addMoney(amount);
        target.save();
        Common.tell(sender, Config.SET, args[0], amount);
        return true;
    }

}
