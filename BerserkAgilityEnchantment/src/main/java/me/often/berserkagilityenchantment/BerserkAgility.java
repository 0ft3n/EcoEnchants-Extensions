package me.often.berserkagilityenchantment;

import com.willfp.eco.core.events.ArmorEquipEvent;
import com.willfp.eco.util.VectorUtils;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

import java.util.UUID;

public class BerserkAgility extends EcoEnchant {

    private final AttributeModifier modifier = new AttributeModifier(UUID.nameUUIDFromBytes("berserkagility".getBytes()), this.getKey().getKey(), 1, AttributeModifier.Operation.ADD_NUMBER);

    public BerserkAgility() {
        super("berserkagility", EnchantmentType.NORMAL);
    }

    @EventHandler
    public void onEquip(ArmorEquipEvent event){

        Player player = event.getPlayer();

        Projectile

        this.getPlugin().getScheduler().runLater(() -> {

            int points = EnchantChecks.getArmorPoints(player, this);

            AttributeInstance inst = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);

            assert inst != null;

            if (this.getDisabledWorlds().contains(player.getWorld())) {
                points = 0;
            }

            inst.removeModifier(modifier);

            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            double missingHealth = maxHealth-player.getHealth();

            double perModifier = this.getConfig().getDouble("config.per-health-attack-speed-per-level");

            double resSpeed = missingHealth*perModifier;

            if (points > 0) {
                inst.addModifier(
                        new AttributeModifier(
                                UUID.nameUUIDFromBytes("berserkagility".getBytes()),
                                this.getKey().getKey(),
                                resSpeed * points,
                                AttributeModifier.Operation.ADD_NUMBER
                        )
                );
            }
        }, 1);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        this.getPlugin().getScheduler().runLater(() -> {

            int points = EnchantChecks.getArmorPoints(player, this);

            AttributeInstance inst = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);

            assert inst != null;

            if (this.getDisabledWorlds().contains(player.getWorld())) {
                points = 0;
            }

            inst.removeModifier(modifier);

            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            double missingHealth = maxHealth-player.getHealth();

            double perModifier = this.getConfig().getDouble("config.per-health-attack-speed-per-level");

            double resSpeed = missingHealth*perModifier;

            if (points > 0) {
                inst.addModifier(
                        new AttributeModifier(
                                UUID.nameUUIDFromBytes("berserkagility".getBytes()),
                                this.getKey().getKey(),
                                resSpeed * points,
                                AttributeModifier.Operation.ADD_NUMBER
                        )
                );
            }
        }, 1);
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent event){

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        this.getPlugin().getScheduler().runLater(() -> {

            int points = EnchantChecks.getArmorPoints(player, this);

            AttributeInstance inst = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);

            assert inst != null;

            if (this.getDisabledWorlds().contains(player.getWorld())) {
                points = 0;
            }

            inst.removeModifier(modifier);

            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            double missingHealth = maxHealth-player.getHealth();

            double perModifier = this.getConfig().getDouble("config.per-health-attack-speed-per-level");

            double resSpeed = missingHealth*perModifier;

            if (points > 0) {
                inst.addModifier(
                        new AttributeModifier(
                                UUID.nameUUIDFromBytes("berserkagility".getBytes()),
                                this.getKey().getKey(),
                                resSpeed * points,
                                AttributeModifier.Operation.ADD_NUMBER
                        )
                );
            }
        }, 1);
    }

}
