package me.often.combatsenseenchantment;

import com.willfp.eco.util.VectorUtils;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.itemtypes.Spell;
import me.often.combatsenseenchantment.Utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class CombatSense extends Spell {

    public CombatSense(){
        super("combatsense");
    }

    @Override
    public boolean onUse(Player player, int i, PlayerInteractEvent event) {
        int radius = i*this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION+"radius-per-level");
        int time = i*this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION+"ticks-per-level");
        boolean players = this.getConfig().getBool(EcoEnchants.CONFIG_LOCATION+"apply-on-players");
        boolean animals = this.getConfig().getBool(EcoEnchants.CONFIG_LOCATION+"apply-on-animals");
        boolean monsters = this.getConfig().getBool(EcoEnchants.CONFIG_LOCATION+"apply-on-monsters");

        List<LivingEntity> nearbyEntities = (List<LivingEntity>) (List<?>) Arrays.asList(player.getNearbyEntities(radius,radius,radius).stream()
                .filter(entity -> entity instanceof LivingEntity)
                .map(entity -> (LivingEntity) entity)
                .filter(entity -> {
                    if (entity instanceof Player && players){
                        return true;
                    }
                    else if (entity instanceof Animals && animals){
                        return true;
                    }
                    else if (entity instanceof Monster && monsters){
                        return true;
                    }
                    return false;
                }).toArray());

        if (nearbyEntities.isEmpty()){
            MessageUtils.sendMessage(getConfig().getString("config.no-targets-message"), event.getPlayer());
            return false;
        }

        for (LivingEntity e: nearbyEntities){
            e.setGlowing(true);
        }

        if (this.getConfig().getBool(EcoEnchants.CONFIG_LOCATION+"enable-particle-effect")){
            Particle particle = Particle.valueOf(this.getConfig().getString("config.particle-effect"));
            Location l = event.getPlayer().getLocation();
            Location center = l.clone();
            for (Vector vector: VectorUtils.getCircle(radius)){
                event.getPlayer().getWorld().spawnParticle(particle, center.clone().add(vector), 1);
            }
        }

        this.getPlugin().getScheduler().runLater(() ->
        {
            for (LivingEntity e: nearbyEntities){
                if (!e.isValid() || e.isDead()){
                    return;
                }
                else {
                    e.setGlowing(false);
                }
            }
        },time);

        return true;
    }
}
