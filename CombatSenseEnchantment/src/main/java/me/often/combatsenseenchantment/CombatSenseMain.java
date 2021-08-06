package me.often.combatsenseenchantment;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.extensions.Extension;
import com.willfp.eco.util.VectorUtils;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import org.bukkit.Bukkit;

public class CombatSenseMain extends Extension {
    public static final EcoEnchant CombatSense = new CombatSense();

    public CombatSenseMain(EcoPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onEnable() {
        // Handled by super
    }

    @Override
    public void onDisable() {
        // Handled by super
    }
}
