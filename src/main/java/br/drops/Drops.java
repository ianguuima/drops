package br.drops;

import br.drops.commands.DropsCommand;
import br.drops.database.MySQLDatabase;
import br.drops.listener.KillEntityEvent;
import br.drops.listener.MenuClickEvent;
import br.drops.listener.PlayerConnectionEvent;
import br.drops.managers.DropManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static br.drops.database.MySQLDatabase.*;

@Getter
public final class Drops extends JavaPlugin {

    private static Drops instance;

    private DropManager dropManager;
    private MySQLDatabase mySQLDatabase;

    private Credentials testCredentials = Credentials
            .builder()
            .database("drops")
            .ip("localhost")
            .user("root")
            .password("vertrigo")
            .port(3306)
            .build();

    @Override
    public void onEnable() {
        instance = this;

        mySQLDatabase = new MySQLDatabase(testCredentials);

        dropManager = new DropManager();

        registerEvent(
                new KillEntityEvent(dropManager),
                new MenuClickEvent(dropManager),
                new PlayerConnectionEvent(dropManager)
        );

        getCommand("drops").setExecutor(new DropsCommand());
    }

    public void registerEvent(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Drops getInstance() {
        return instance;
    }
}
