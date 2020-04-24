package br.drops.inventories;

import br.drops.Drops;
import br.drops.api.ItemBuilder;
import br.drops.managers.DropManager;
import br.drops.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemsMenu {

    private DropManager dropManager;

    public ItemsMenu() {
        this.dropManager = Drops.getInstance().getDropManager();
    }

    public void open(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 9 * 3, "Colete seus drops");

        int slot = 10;

        User user = dropManager.get(player);

        for (Material material : dropManager.getWhiteList()) {

            int amount = user.getAmount(material);
            inventory.setItem(slot, getDropItem(material, amount));

            slot += 2;
        }

        player.openInventory(inventory);
    }

    private ItemStack getDropItem(Material material, int amount) {
        return new ItemBuilder(material, 1)
                .setDisplayName("§f" + material.name())
                .setLore(
                        "",
                        "§7Clique para coletar §a" + amount + "x " + material.name(),
                        "§aBotão Esquerdo §7para coletar §232§7 itens",
                        "§aBotão Direito §7para coletar §264§7 itens",
                        ""
                )
                .build();
    }
}
