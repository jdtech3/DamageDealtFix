package ca.j0e.damagedealtfix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scoreboard.Score;

import java.util.Objects;

public class EntityDamageListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        double damageDealt;

        if (damager instanceof Player) {
            if (DamageDealtFix.getInstance().getDebug()) {
                Bukkit.broadcastMessage(String.format("%s damaged by %s for %.2f", event.getEntity().getType().getName(), ((Player) damager).getDisplayName(), event.getFinalDamage()));
            }

            damageDealt = event.getFinalDamage();
        }
        else if (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof Player) {
            if (DamageDealtFix.getInstance().getDebug()) {
                Bukkit.broadcastMessage(String.format("%s damaged by %s for %.2f", event.getEntity().getType().getName(), ((Player) ((Projectile) damager).getShooter()).getDisplayName(), event.getFinalDamage()));
            }

            damager = (Player) ((Projectile) damager).getShooter();
            damageDealt = event.getFinalDamage();
        }
        else {
            return;
        }

        if (damageDealt > 0.0) {
            if (damageDealt > ((Damageable) event.getEntity()).getHealth()) {
                if (DamageDealtFix.getInstance().getDebug()) {
                    Bukkit.broadcastMessage(String.format("Overdamage: clamped %.2f to %.2f", damageDealt, ((Damageable) event.getEntity()).getHealth()));
                }

                damageDealt = ((Damageable) event.getEntity()).getHealth();
            }

            Score score = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard().getObjective("ddf-dmg").getScore(((Player) damager).getDisplayName());
            score.setScore(score.getScore() + (int) Math.round(damageDealt));
        }
    }
}
