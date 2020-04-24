package br.drops.listener;

import br.drops.managers.DropManager;
import br.drops.objects.User;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class KillEntityEvent implements Listener {

    private DropManager dropManager;

    public KillEntityEvent(DropManager dropManager) {
        this.dropManager = dropManager;
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        Player killer = entity.getKiller();

        if (killer == null) return;

        User user = dropManager.get(killer);

        for (ItemStack drop : e.getDrops()) {

            Material type = drop.getType();

            if (!dropManager.isInWhiteList(type)) {
                return;
            }

            int amount = drop.getAmount();

            user.addMaterial(type, amount);
        }


        e.getDrops().clear();
    }

}
