package me.often.ambushenchantment;

import com.willfp.eco.util.NumberUtils;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class Ambush extends EcoEnchant {

    public Ambush(){
        super("ambush", EnchantmentType.SPECIAL);
    }

    @EventHandler
    public void onPickup(PlayerPickupArrowEvent e) {
        if (e.getArrow().getPersistentDataContainer().has(new NamespacedKey(this.getPlugin(),"EcoAmbush"),PersistentDataType.INTEGER)){
            e.getArrow().remove();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e){

        if (e.getEntity().getShooter() == null){
            return;
        }

        if (!(e.getEntity().getShooter() instanceof Player player)){
            return;
        }

        if (player.getInventory().getItemInMainHand().getType().equals(Material.BOW)){
            double force = e.getEntity().getVelocity().clone().length() / 3;
            force = NumberUtils.equalIfOver(force, 1);
            if (this.getConfig().getBool(EcoEnchants.CONFIG_LOCATION+"require-full-force") && force < 0.9){
                return;
            }
        }

        if (e.getEntity().getPersistentDataContainer().has(new NamespacedKey(this.getPlugin(),"EcoAmbush"),PersistentDataType.INTEGER)){
            return;
        }

        if (e.isCancelled()) {
            return;
        }

        if (!EnchantChecks.mainhand((Player) e.getEntity().getShooter(), this)) {
            return;
        }

        int lvl = EnchantChecks.getMainhandLevel(((Player) e.getEntity().getShooter()).getPlayer(),this);
        int arrows = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION+"arrows-per-level")*lvl;
        float multiplier = (float) this.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION+"arrow-speed-per-level");

        if (e.getHitBlock() == null) {
            Location hit = (e.getHitEntity().getLocation()).add(0,3,0);
            int currentHeight = hit.getBlockY()+this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION+"max-arrows-height");
            while(true){
                if (!(hit.getBlock().getType().equals(Material.AIR)) && hit.getY()>=currentHeight){
                    break;
                }
                if (hit.getBlock().getType().equals(Material.AIR) && hit.getY()>=currentHeight){
                    break;
                }
                hit.add(0,1,0);
            }
            Vector vector = e.getHitEntity().getLocation().toVector().subtract(hit.toVector());
            for (int i = 0; i<arrows; i++){
                Arrow arr = hit.getWorld().spawnArrow(hit,vector,lvl*multiplier,this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION+"arrows-spread"));
                arr.setShooter(e.getEntity().getShooter());
                PersistentDataContainer cont = arr.getPersistentDataContainer();
                cont.set(new NamespacedKey(this.getPlugin(),"EcoAmbush"), PersistentDataType.INTEGER,1);
            }

        }
        else {
            Location hit = (e.getHitBlock().getLocation()).add(0,1,0).add(e.getHitBlockFace().getDirection());
            int currentHeight = hit.getBlockY()+this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION+"max-arrows-height");
            while(true){
                if (!(hit.getBlock().getType().equals(Material.AIR)) && hit.getBlockY()>=currentHeight){
                    return;
                }
                if (hit.getBlock().getType().equals(Material.AIR) && hit.getBlockY()>=currentHeight){
                    break;
                }
                hit.add(0,1,0);
            }
            Vector vector = e.getHitBlock().getLocation().toVector().subtract(hit.toVector());
            for (int i = 0; i<arrows; i++){
                Arrow arr = hit.getWorld().spawnArrow(hit,vector,lvl*multiplier,this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION+"arrows-spread"));
                arr.setShooter(e.getEntity().getShooter());
                PersistentDataContainer cont = arr.getPersistentDataContainer();
                cont.set(new NamespacedKey(this.getPlugin(),"EcoAmbush"), PersistentDataType.INTEGER,1);
            }
        }
    }

}
