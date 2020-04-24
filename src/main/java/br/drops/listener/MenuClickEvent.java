package br.drops.listener;

import br.drops.inventories.ItemsMenu;
import br.drops.managers.DropManager;
import br.drops.objects.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuClickEvent implements Listener {

    private DropManager dropManager;

    public MenuClickEvent(DropManager dropManager) {
        this.dropManager = dropManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Inventory inventory = e.getInventory();
        String name = inventory.getName();

        if (!name.equalsIgnoreCase("Colete seus drops")) return;

        Player player = (Player) e.getWhoClicked();
        ItemStack currentItem = e.getCurrentItem();

        if (currentItem == null) return;

        Material type = currentItem.getType();

        if (!dropManager.isInWhiteList(type)) {
            return;
        }

        e.setCancelled(true);
        giveToPlayer(player, type, e.getClick());
    }

    public void giveToPlayer(Player player, Material material, ClickType clickType) {

        if (isInventoryFull(player)) {
            player.sendMessage("§7O seu inventário está cheio!");
            return;
        }

        User user = dropManager.get(player);
        int storedAmount = user.getAmount(material);

        if (storedAmount == 0) {
            player.sendMessage("§7Você não tem nenhum item deste!");
            return;
        }

        ItemStack itemToBeGiven = new ItemStack(material, 1);
        int amountToBeRemoved = 0;

        switch (clickType) {
            case LEFT:
                amountToBeRemoved = 32;
                break;
            case RIGHT:
                amountToBeRemoved = 64;
                break;
        }


        if (amountToBeRemoved >= storedAmount) {
            amountToBeRemoved = storedAmount;
        }

        itemToBeGiven.setAmount(amountToBeRemoved);
        player.getInventory().addItem(itemToBeGiven);

        user.removeQuantity(material, amountToBeRemoved);
        player.sendMessage("§7» §7Você coletou §a" + amountToBeRemoved + "§7 itens!");
        new ItemsMenu().open(player);
    }

    public boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

}
