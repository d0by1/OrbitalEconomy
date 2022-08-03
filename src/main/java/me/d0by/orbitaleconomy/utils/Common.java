package me.d0by.orbitaleconomy.utils;

import lombok.experimental.UtilityClass;
import me.d0by.orbitaleconomy.Config;
import me.d0by.orbitaleconomy.OrbitalEconomy;
import me.d0by.orbitaleconomy.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;

@UtilityClass
public final class Common {

    private static final OrbitalEconomy PLUGIN = OrbitalEconomy.getInstance();

    /**
     * Translate all color codes including rgb in the given string.
     *
     * @param string The string to translate.
     * @return The translated string.
     */
    @Contract("_ -> new")
    @NotNull
    public static String colorize(@NotNull String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Format the given string replacing all java format placeholders with the given arguments,
     * replacing the '{prefix}' placeholder and colorizing the string.
     *
     * @param message The message to format.
     * @param args    The arguments to format.
     * @return The formatted string.
     */
    @NotNull
    public static String formatString(@NotNull String message, Object... args) {
        return formatString(null, message, args);
    }

    /**
     * Format the given string replacing all java format placeholders with the given arguments,
     * replacing the '{prefix}' placeholder and colorizing the string.
     *
     * @param message The message to format.
     * @param args    The arguments to format.
     * @return The formatted string.
     */
    @NotNull
    public static String formatString(@Nullable CommandSender sender, @NotNull String message, Object... args) {
        message = message.replace("{prefix}", Config.PREFIX);
        message = MessageFormat.format(message, args);
        if (sender instanceof Player player) {
            message = replacePlaceholders(player, message);
        }
        message = colorize(message);
        return message;
    }

    /**
     * Send a message to the given {@link CommandSender}. This method formats the message
     * before sending it using the {@link #formatString(String, Object...)} method.
     *
     * @param sender  The {@link CommandSender} to send the message to.
     * @param message The message to send.
     * @param args    The arguments to format.
     */
    public static void tell(@NotNull CommandSender sender, @NotNull String message, Object... args) {
        sender.sendMessage(formatString(sender, message, args));
    }

    /**
     * Replace all built-in placeholders in the given string.
     *
     * @param player The player to replace the placeholders for.
     * @param string The string to replace placeholders in.
     * @return The string with replaced placeholders.
     */
    @NotNull
    public String replacePlaceholders(@NotNull Player player, @NotNull String string) {
        Profile profile = PLUGIN.getProfileManager().getProfile(player.getName());
        return string
                .replace("{player}", player.getName())
                .replace("{money}", profile.getMoney() + "")
                ;
    }

    /**
     * Generate a random integer between the given min and max.
     *
     * @param min The minimum value. (inclusive)
     * @param max The maximum value. (inclusive)
     * @return The random integer.
     */
    public static int irand(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

}
