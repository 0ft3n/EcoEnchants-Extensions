package me.often.berserkagilityenchantment;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.extensions.Extension;
import com.willfp.ecoenchants.enchantments.EcoEnchant;

public class BerserkAgilityMain extends Extension {

    public static final EcoEnchant BerserkSpeed = new BerserkAgility();

    public BerserkAgilityMain(EcoPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
