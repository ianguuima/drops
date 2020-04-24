package br.drops.managers;

import br.drops.Drops;
import br.drops.database.storages.UserStorage;
import br.drops.objects.User;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropManager {

    private Map<String, User> usersCache;

    @Getter
    private List<Material> whiteList;

    private UserStorage storage;

    public DropManager() {
        usersCache = new HashMap<>();
        whiteList = ImmutableList.of(
                Material.ROTTEN_FLESH,
                Material.BONE,
                Material.STRING,
                Material.ENDER_PEARL
        );

        storage = new UserStorage();
    }

    public void add(User user) {
        usersCache.put(user.getName(), user);
    }

    public void remove(String name) {
        usersCache.remove(name);
    }

    public boolean isInWhiteList(Material material) {
        return whiteList.contains(material);
    }

    public User get(String name) {
        return usersCache.get(name);
    }

    public User get(Player player) {
        return get(player.getName());
    }

    public void load(String name) {
        User load = storage.load(name);

        if (load == null) {
            load = new User(name);
        }

        add(load);
    }

    public void update(User user) {
        if (user == null) return;

        boolean isThereItem = user.getMaterials().values().stream().anyMatch(v -> v != 0);

        if (!isThereItem) return;

        Drops plugin = Drops.getInstance();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> storage.update(user));
    }
}
