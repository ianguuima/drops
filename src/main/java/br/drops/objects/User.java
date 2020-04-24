package br.drops.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

import java.util.EnumMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class User {

    private String name;
    private Map<Material, Integer> materials;

    public User(String name) {
        this.name = name;
        this.materials = new EnumMap<>(Material.class);
    }

    public void addMaterial(Material material, int amount){
        int storedAmount = materials.getOrDefault(material, 0);
        materials.put(material, storedAmount + amount);
    }

    public void removeQuantity(Material material, int amount){
        int storedAmount = materials.getOrDefault(material, 0);
        materials.put(material, storedAmount - amount);
    }

    public int getAmount(Material material){
        return materials.getOrDefault(material, 0);
    }


}
