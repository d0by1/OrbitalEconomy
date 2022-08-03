package me.d0by.orbitaleconomy.profile;

import lombok.Getter;
import lombok.Setter;
import me.d0by.orbitaleconomy.OrbitalEconomy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@Getter
@Setter
public class Profile {

    private final @NotNull String name;
    private double money;

    @Contract(pure = true)
    public Profile(@NotNull String name) {
        this.name = name;
        this.money = 0.0d;
    }

    public void save() {
        CompletableFuture.runAsync(() -> {
            // Save to database
            OrbitalEconomy.getInstance().getDatabase().saveProfile(this);
        });
    }

    public void addMoney(double amount) {
        this.money += amount;
    }

    public void removeMoney(double amount) {
        this.money -= amount;
    }

    public boolean hasMoney(double amount) {
        return this.money >= amount;
    }

}
