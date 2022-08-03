package me.d0by.orbitaleconomy;

import lombok.experimental.UtilityClass;
import me.d0by.orbitaleconomy.utils.config.CFG;
import me.d0by.orbitaleconomy.utils.config.ConfigValue;

import java.io.File;
import java.util.List;

@UtilityClass
public final class Config {

    private static final OrbitalEconomy PLUGIN = OrbitalEconomy.getInstance();

    public static void reload() {
        CFG.load(Config.class, new File(PLUGIN.getDataFolder(), "config.yml"));
    }

    // -- Database -- //

    @ConfigValue("mysql.host")
    public static String MYSQL_HOST = "localhost";
    @ConfigValue("mysql.port")
    public static String MYSQL_PORT = "3306";
    @ConfigValue("mysql.username")
    public static String MYSQL_USERNAME = "root";
    @ConfigValue("mysql.password")
    public static String MYSQL_PASSWORD = "root";
    @ConfigValue("mysql.database")
    public static String MYSQL_DATABASE = "economy";

    // -- Messages -- //

    @ConfigValue("messages.prefix")
    public static final String PREFIX = "&6Economy &8» &7";
    @ConfigValue("messages.no_perm")
    public static final String NO_PERM = "{prefix}&cYou don't have permission to do that.";
    @ConfigValue("messages.balance")
    public static final String BALANCE = "{prefix}&7Your balance is &a{0}$&7.";
    @ConfigValue("messages.balance_other")
    public static final String BALANCE_OTHER = "{prefix}&7{0}'s balance is &a{1}$&7.";
    @ConfigValue("messages.earned")
    public static final String EARNED = "{prefix}&7You earned &a{0}$&7.";
    @ConfigValue("messages.given")
    public static final String GIVEN = "{prefix}&7You gave &a{0}$&7 to {1}.";
    @ConfigValue("messages.set")
    public static final String SET = "{prefix}&7You set the balance of {0} to &a{1}$&7.";
    @ConfigValue("messages.invalid_amount")
    public static final String INVALID_AMOUNT = "{prefix}&cInvalid amount, use a positive number.";
    @ConfigValue("messages.not_enough")
    public static final String NOT_ENOUGH = "{prefix}&cYou don't have enough money.";
    @ConfigValue("messages.help")
    public static final List<String> HELP = List.of(
            "",
            "&6&lORBITAL ECONOMY HELP",
            "&fAll the available commands in this plugin.",
            "",
            " &e/balance &7- Check your balance.",
            " &e/balance <player> &7- Check another player's balance.",
            " &e/give <player> <amount> &7- Give another player money.",
            " &e/set <player> <amount> &7- Set another player's balance.",
            " &e/earn &7- Earn money.",
            ""
    );


}
