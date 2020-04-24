package br.drops.commands;

import br.drops.inventories.ItemsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DropsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem digitar esse comando.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sender.sendMessage("§7Abrindo menu...");
            new ItemsMenu().open(player);
            return true;
        }


        return false;
    }


}
