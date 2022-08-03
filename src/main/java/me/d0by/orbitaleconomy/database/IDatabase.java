package me.d0by.orbitaleconomy.database;

import me.d0by.orbitaleconomy.profile.Profile;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface IDatabase {

    void connect();

    void disconnect();

    CompletableFuture<Profile> loadProfile(@NotNull String name);

    void saveProfile(@NotNull Profile profile);

}
