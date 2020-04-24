package br.drops.listener;

import br.drops.managers.DropManager;
import br.drops.objects.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionEvent implements Listener {

    private DropManager dropManager;

    public PlayerConnectionEvent(DropManager dropManager) {
        this.dropManager = dropManager;
    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e) {
        if (e.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            dropManager.load(e.getName());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        User user = dropManager.get(e.getPlayer());

        if (user == null) return;

        dropManager.update(user);
    }

}
